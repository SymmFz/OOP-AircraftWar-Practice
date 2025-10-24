package edu.hitsz.item;

import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.shootstrategy.HeroAircraftScatterShootStrategy;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 火力道具类
 */
public class FirePowerUpItem extends BaseItem {

    private static final int DURATION_SECONDS = 10;

    public FirePowerUpItem(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    // TODO: use a GameContext Class instead of many arguments
    @Override
    public void active(HeroAircraft heroAircraft, List<EnemyAircraft> enemyAircrafts, List<BaseBullet> enemyBullets,
                       ScheduledExecutorService executorService) {
        System.out.println("FireSupply active!");

        heroAircraft.upgradeShootingStrategyForPeriod(
                new HeroAircraftScatterShootStrategy(),
                DURATION_SECONDS,
                TimeUnit.SECONDS,
                executorService
        );
    }
}
