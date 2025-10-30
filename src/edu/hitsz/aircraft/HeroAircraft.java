package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.item.BaseItem;
import edu.hitsz.scorerecord.ScoreBoardService;
import edu.hitsz.shootstrategy.HeroAircraftDirectShootStrategy;
import edu.hitsz.shootstrategy.ShootStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 英雄飞机，游戏玩家操控
 * 
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    private ScheduledFuture<?> shootingStateTimer;

    // 静态内部类单例模式：
    // 静态内部类，为了实现单例模式的测试，需要设置类为 package-private，内部的实例为 private
    static class HeroAircraftHolder {
        private static HeroAircraft heroAircraft = new HeroAircraft(
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                0, 0, 100
                );

        // reset 方法仅用于在单元测试中重置单例。
        static void reset() {
            heroAircraft = new HeroAircraft(
                    Main.WINDOW_WIDTH / 2,
                    Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                    0, 0, 100
                    );
        }
    }

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp        初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp,
              -1, 1, 50, new HeroAircraftDirectShootStrategy());
    }

    public static HeroAircraft getInstance() {
        return HeroAircraftHolder.heroAircraft;
    }

    public static void resetInstance() {
        HeroAircraftHolder.reset();
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    public void upgradeShootingStrategyForPeriod(ShootStrategy newStrategy,
                                                 int duration, TimeUnit unit,
                                                 ScheduledExecutorService executorService) {
        if (shootingStateTimer != null && !shootingStateTimer.isDone()) {
            shootingStateTimer.cancel(false);
        }

        this.setStrategy(newStrategy);
        Runnable restoreTask = () -> {
            this.setStrategy(new HeroAircraftDirectShootStrategy());
        };

        this.shootingStateTimer = executorService.schedule(restoreTask, duration, unit);
    }
}
