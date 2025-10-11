package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shootstrategy.EnemyAircraftCircularShootStrategy;
import edu.hitsz.shootstrategy.ShootContext;

public class BossEnemyFactory implements EnemyAircraftFactory {

    @Override
    public EnemyAircraft createEnemyAircraft() {

        return new BossEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                3,
                0,
                300,
                0, 20, 5,
                new ShootContext(new EnemyAircraftCircularShootStrategy()),
                0.7, 3);
    }
}
