package edu.hitsz.game;

import edu.hitsz.aircraft.*;
import edu.hitsz.application.HeroController;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicManager;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.item.BaseItem;
import edu.hitsz.item.BombItem;
import edu.hitsz.scorerecord.ScoreBoardService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class AbstractGame extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<EnemyAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<BaseItem> items;

    protected GameDifficulty gameDifficulty;
    protected ScoreBoardService scoreBoardService;
    protected MusicManager musicManager;
    protected BufferedImage backgroundImage = ImageManager.BACKGROUND_IMAGE;

    // 游戏对象基础属性
    protected int baseEnemyMaxNumber;
    protected int baseMobEnemyWeight;
    protected int baseEliteEnemyWeight;
    protected int baseElitePlusEnemyWeight;
    protected int baseBossScoreInterval;

    private double currentEnemyHpRate = 1.0;
    private double currentEnemyPowerRate = 1.0;
    private double currentBossHpRate = 1.0;
    private double currentBossPowerRate = 1.0;
    private int currentEnemyMaxNumber;
    protected int bossScoreThreshold;
    protected int currentBossScoreInterval;

    private int currentMobEnemyWeight;
    private int currentEliteEnemyWeight;
    private int currentElitePlusEnemyWeight;

    protected int bossKilledNum = 0;
    private boolean bossExists = false;

    private int heroLastHp = 100;

    private volatile boolean storyMode = false;
    private final List<GameEventListener> gameEventListeners = new CopyOnWriteArrayList<>();

    protected final EnemyAircraftFactory mobEnemyFactory = new MobEnemyFactory();
    protected final EnemyAircraftFactory eliteEnemyFactory = new EliteEnemyFactory();
    protected final EnemyAircraftFactory elitePlusEnemyFactory = new ElitePlusEnemyFactory();
    protected final EnemyAircraftFactory bossEnemyFactory = new BossEnemyFactory();

    protected int score = 0;
    protected int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private final int cycleDuration = 850;
    private int cycleTime = 0;

    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;

    public AbstractGame(ScoreBoardService scoreBoardService) {
        HeroAircraft.resetInstance();
        heroAircraft = HeroAircraft.getInstance();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        items = new LinkedList<>();

        this.scoreBoardService = scoreBoardService;
        this.musicManager = MusicManager.getInstance();

        configureGame();

        this.bossScoreThreshold = this.baseBossScoreInterval;
        this.currentBossScoreInterval = this.baseBossScoreInterval;
        this.currentEnemyMaxNumber = this.baseEnemyMaxNumber;
        this.currentMobEnemyWeight = this.baseMobEnemyWeight;
        this.currentEliteEnemyWeight = this.baseEliteEnemyWeight;
        this.currentElitePlusEnemyWeight = this.baseElitePlusEnemyWeight;

        configureFactories();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(2,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        // 启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    public abstract void configureGame();

    protected abstract double calculateEnemyHpRate(int time);

    protected abstract double calculateEnemyPowerRate(int time);

    protected abstract double calculateBossHpRate(int bossKilledNum);

    protected abstract double calculateBossPowerRate(int bossKilledNum);

    protected abstract int calculateEnemyMaxNumber(int baseMaxNumber, int score);

    protected abstract int calculateEliteEnemyWeight(int baseWeight, int time);

    protected abstract int calculateElitePlusEnemyWeight(int baseWeight, int time);

    protected int calculateBossScoreInterval(int baseInterval, int bossKilledNum) {
        // 默认情况下，Boss出现间隔不变
        return baseInterval;
    }

    private void configureFactories() {
        mobEnemyFactory.setPropertyHp(this.currentEnemyHpRate);
        mobEnemyFactory.setPropertyPower(this.currentEnemyPowerRate);

        eliteEnemyFactory.setPropertyHp(this.currentEnemyHpRate);
        eliteEnemyFactory.setPropertyPower(this.currentEnemyPowerRate);

        elitePlusEnemyFactory.setPropertyHp(this.currentEnemyHpRate);
        elitePlusEnemyFactory.setPropertyPower(this.currentEnemyPowerRate);

        bossEnemyFactory.setPropertyHp(this.currentBossHpRate);
        bossEnemyFactory.setPropertyPower(this.currentBossPowerRate);
    }

    public void updateGameProperty() {
        double newEnemyHpRate = calculateEnemyHpRate(time);
        double newEnemyPowerRate = calculateEnemyPowerRate(time);
        double newBossHpRate = calculateBossHpRate(bossKilledNum);
        double newBossPowerRate = calculateBossPowerRate(bossKilledNum);
        int newEnemyMaxNumber = calculateEnemyMaxNumber(this.baseEnemyMaxNumber, score);
        int newEliteWeight = calculateEliteEnemyWeight(this.baseEliteEnemyWeight, time);
        int newElitePlusWeight = calculateElitePlusEnemyWeight(this.baseElitePlusEnemyWeight, time);
        int newBossScoreInterval = calculateBossScoreInterval(this.baseBossScoreInterval, bossKilledNum);

        if (newEnemyHpRate > this.currentEnemyHpRate ||
                newEnemyPowerRate > this.currentEnemyPowerRate ||
                newEnemyMaxNumber > this.currentEnemyMaxNumber ||
                newEliteWeight > this.currentEliteEnemyWeight ||
                newElitePlusWeight > this.currentElitePlusEnemyWeight ||
                newBossHpRate > this.currentBossHpRate ||
                newBossPowerRate > this.currentBossPowerRate ||
                newBossScoreInterval != this.currentBossScoreInterval) {

            this.currentEnemyHpRate = newEnemyHpRate;
            this.currentEnemyPowerRate = newEnemyPowerRate;
            this.currentBossHpRate = newBossHpRate;
            this.currentBossPowerRate = newBossPowerRate;
            this.currentEnemyMaxNumber = newEnemyMaxNumber;
            this.currentEliteEnemyWeight = newEliteWeight;
            this.currentElitePlusEnemyWeight = newElitePlusWeight;
            this.currentBossScoreInterval = newBossScoreInterval;

            System.out.println("-------------------------------------------------------");
            System.out.println("Time: " + time + " | Score: " + score);
            System.out.println("提高难度！");
            System.out.printf("1. 敌机生命值倍率：%.2f, 敌机伤害倍率：%.2f%n",
                    this.currentEnemyHpRate, this.currentEnemyPowerRate);

            int totalWeight = this.currentMobEnemyWeight + this.currentEliteEnemyWeight
                    + this.currentElitePlusEnemyWeight;
            System.out.printf("2. 精英敌机出现概率：%.2f，超级精英敌机出现概率：%.2f%n",
                    (double) this.currentEliteEnemyWeight / totalWeight,
                    (double) this.currentElitePlusEnemyWeight / totalWeight);

            System.out.printf("3. 敌机最大数量：%d%n", this.currentEnemyMaxNumber);
            System.out.printf("4. Boss 敌机下次产生阈值：%d, 后续产生间隔: %d%n", this.bossScoreThreshold, this.currentBossScoreInterval);
            System.out.printf("5. Boss 敌机生命值倍率：%.2f, Boss 敌机伤害倍率：%.2f%n",
                    this.currentBossHpRate, this.currentBossPowerRate);
            System.out.println("-------------------------------------------------------");

            configureFactories();
        }
    }

    public GameDifficulty getGameDifficulty() {
        return this.gameDifficulty;
    }

    public void setStoryMode(boolean storyMode) {
        this.storyMode = storyMode;
    }

    public boolean isStoryMode() {
        return storyMode;
    }

    public void addGameEventListener(GameEventListener listener) {
        if (listener != null) {
            gameEventListeners.add(listener);
        }
    }

    public void removeGameEventListener(GameEventListener listener) {
        if (listener != null) {
            gameEventListeners.remove(listener);
        }
    }

    protected void notifyGameOverEvent(boolean heroDied) {
        for (GameEventListener listener : gameEventListeners) {
            try {
                listener.onGameOver(this, heroDied, this.score);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void notifyBossKilledEvent() {
        for (GameEventListener listener : gameEventListeners) {
            try {
                listener.onBossKilled(this, this.bossKilledNum, this.score);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stopGame() {
        gameOverFlag = true;
        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        musicManager.switchToDefaultBgm();

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                updateGameProperty();

                maybePlayFailureSuccessVoice();

                // 新敌机产生
                generateEnemyAircraftAction();
                // 飞机射出子弹
                shootAction();
            }

            // 道具移动
            itemsMoveAction();

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            // 每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                musicManager.stopBgm();
                musicManager.playGameOverSoundEffect();
                gameOverFlag = true;

                notifyGameOverEvent(true);

                if (storyMode) {
                    executorService.shutdown();
                    SwingUtilities.invokeLater(() -> Main.cardLayout.show(Main.cardPanel, Main.SCORE_BOARD_VIEW));
                    return;
                }

                String playerName = JOptionPane.showInputDialog(String.format("游戏结束，你的得分为 %d \n请输入玩家名记录得分：", score));

                if ("woshiqingshihuang".equals(playerName) ||
                    "wsqsh".equals(playerName) ||
                    "我是秦始皇".equals(playerName)) {
                    System.out.println("作弊码激活！我是秦始皇！再来一次！");
                    JOptionPane.showMessageDialog(this, "作弊码激活！我是秦始皇！再来一次！");
                    heroAircraft.increaseHp(Integer.MAX_VALUE);
                    enemyAircrafts.clear();
                    enemyBullets.clear();
                    gameOverFlag = false;
                    musicManager.switchToDefaultBgm();
                    return;
                }

                executorService.shutdown();

                if (playerName != null) {
                    playerName = playerName.trim();
                    if (playerName.isEmpty()) {
                        playerName = "Anonymous";
                    }
                    System.out.println("玩家名：" + playerName);
                    System.out.println("难度：" + gameDifficulty);
                    System.out.println("得分：" + score);
                    scoreBoardService.addRecord(playerName, score, gameDifficulty);
                } else {
                    System.out.println("玩家取消，本次游戏不记录分数。");
                }
                scoreBoardService.printScoreBoardInConsole(gameDifficulty);
                Main.cardLayout.show(Main.cardPanel, Main.SCORE_BOARD_VIEW);
            }
        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    // ***********************
    // Action 各部分
    // ***********************

    private void maybePlayFailureSuccessVoice() {
        if (heroLastHp != heroAircraft.getHp() && heroLastHp > 30 && heroAircraft.getHp() < 30) {
            musicManager.playRandomFailureVoice();
        } else {
            if (heroAircraft.getHp() <= 30) {
                if (Math.random() < 0.04) {
                    musicManager.playRandomFailureVoice();
                }
            } else {
                if (Math.random() < 0.02) {
                    musicManager.playRandomSuccessVoice();
                }
            }
        }
        heroLastHp = heroAircraft.getHp();
    }

    private void generateEnemyAircraftAction() {
        if (enemyAircrafts.size() < currentEnemyMaxNumber) {
            if (score >= bossScoreThreshold && !bossExists) {
                enemyAircrafts.add(bossEnemyFactory.createEnemyAircraft());
                bossScoreThreshold += currentBossScoreInterval;
                bossExists = true;

                musicManager.switchToBossBgm();
            } else {
                int totalWeight = currentMobEnemyWeight + currentEliteEnemyWeight + currentElitePlusEnemyWeight;
                int randomNum = (int) (Math.random() * totalWeight);
                if (randomNum < currentMobEnemyWeight) {
                    enemyAircrafts.add(mobEnemyFactory.createEnemyAircraft());
                } else if (randomNum < currentMobEnemyWeight + currentEliteEnemyWeight) {
                    enemyAircrafts.add(eliteEnemyFactory.createEnemyAircraft());
                } else {
                    enemyAircrafts.add(elitePlusEnemyFactory.createEnemyAircraft());
                }
            }
        }
    }

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());

        // 敌人射击
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyBullets.addAll(enemyAircraft.shoot());
        }

    }

    private void itemsMoveAction() {
        for (BaseItem item : items) {
            item.forward();
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();

                if (heroAircraft.getHp() <= 30) {
                    musicManager.playGameOverSoundEffect();
                }
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (EnemyAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // 获得分数，产生道具补给
                        items.addAll(enemyAircraft.dropItems());
                        score += enemyAircraft.getScoreNum();
                        if (enemyAircraft instanceof BossEnemy) {
                            bossExists = false;
                            bossKilledNum++;
                            musicManager.playRandomSuccessVoice();
                            musicManager.switchToDefaultBgm();
                            notifyBossKilledEvent();
                        }
                    }

                    musicManager.playBulletHitSoundEffect();
                }
            }
        }

        // 英雄机 与 敌机 相撞，均损毁
        for (EnemyAircraft enemyAircraft : enemyAircrafts) {
            if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                enemyAircraft.vanish();
                heroAircraft.decreaseHp(Integer.MAX_VALUE);
            }
        }

        // 我方获得道具，道具生效
        for (BaseItem item : items) {
            if (heroAircraft.crash(item)) {
                if (item instanceof BombItem) {
                    musicManager.playBombExplosionSoundEffect();

                    BombItem bomb = (BombItem) item;
                    bomb.registerObserver(enemyAircrafts);
                    bomb.registerObserver(enemyBullets);
                }
                item.active(heroAircraft, this.executorService);
                item.vanish();

                musicManager.playGetSupplySoundEffect();
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     *
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {

        items.removeIf(AbstractFlyingObject::notValid);
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
    }

    // ***********************
    // Paint 各部分
    // ***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(backgroundImage, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(backgroundImage, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, items);
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        // 绘制得分和生命值
        paintScoreAndLife(g);

        // 绘制游戏 logo
        if (heroAircraft.getHp() > 30) {
            g.drawImage(ImageManager.SMALL_LOGO_IMAGE, 0, 0, 60, 60, null);
        } else {
            g.drawImage(ImageManager.SMALL_LOGO_RED_IMAGE, 0, 0, 60, 60, null);
        }
    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 70;
        int y = 30;
        if (heroAircraft.getHp() > 30) {
            g.setColor(Color.CYAN);
        } else {
            g.setColor(new Color(16711680));
        }
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

}
