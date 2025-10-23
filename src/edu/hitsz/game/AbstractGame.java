package edu.hitsz.game;

import edu.hitsz.aircraft.*;
import edu.hitsz.application.HeroController;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicManager;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.item.BaseItem;
import edu.hitsz.scorerecord.ScoreBoardService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

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
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<EnemyAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<BaseItem> items;

    protected GameDifficulty gameDifficulty;
    protected ScoreBoardService scoreBoardService;

    protected MusicManager musicManager;
    protected BufferedImage backgroundImage = ImageManager.BACKGROUND_IMAGE;

    /**
     * 屏幕中出现的敌机最大数量
     */
    private int enemyMaxNumber = 5;

    /**
     * 下一个刷新的非 Boss 敌人为精英敌人的概率
     */
    private final double ELITE_SPAWN_CHANCE = 0.2;

    /**
     * 下一个 Boss 敌人出现所需的分数阈值
     */
    private final int BOSS_SCORE_INTERVAL = 5000;
    private int bossScoreThreshold = BOSS_SCORE_INTERVAL;
    private boolean bossExists = false;

    private final EnemyAircraftFactory mobEnemyFactory = new MobEnemyFactory();
    private final EnemyAircraftFactory eliteEnemyFactory = new EliteEnemyFactory();
    private final EnemyAircraftFactory elitePlusEnemyFactory = new ElitePlusEnemyFactory();
    private final EnemyAircraftFactory bossEnemyFactory = new BossEnemyFactory();

    /**
     * 当前得分
     */
    private int score = 0;
    /**
     * 当前时刻
     */
    private int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
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

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        // 启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    public abstract void configureGame();
    public GameDifficulty getGameDifficulty() { return this.gameDifficulty; }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        musicManager.playDefaultBgm();

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
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
                executorService.shutdown();
                gameOverFlag = true;

                String playerName = JOptionPane.showInputDialog(String.format("游戏结束，你的得分为 %d \n请输入玩家名记录得分：", score));
                scoreBoardService.addRecord(playerName, score, gameDifficulty);
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

    private void generateEnemyAircraftAction() {
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if (score >= bossScoreThreshold && !bossExists) {
                enemyAircrafts.add(bossEnemyFactory.createEnemyAircraft());
                bossScoreThreshold += BOSS_SCORE_INTERVAL;
                bossExists = true;

                musicManager.playBossBgm();
            } else {
                boolean nextEnemyIsElite = Math.random() < ELITE_SPAWN_CHANCE;
                if (nextEnemyIsElite) {
                    if (Math.random() < 0.75) {
                        enemyAircrafts.add(eliteEnemyFactory.createEnemyAircraft());
                    } else {
                        enemyAircrafts.add(elitePlusEnemyFactory.createEnemyAircraft());
                    }
                } else {
                    enemyAircrafts.add(mobEnemyFactory.createEnemyAircraft());
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
                            musicManager.playDefaultBgm();
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
                item.active(heroAircraft, enemyAircrafts, enemyBullets);
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
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

}
