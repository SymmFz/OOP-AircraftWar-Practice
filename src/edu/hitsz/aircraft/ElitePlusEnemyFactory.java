package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shootstrategy.EnemyAircraftScatterShootStrategy;
import edu.hitsz.shootstrategy.ShootContext;

public class ElitePlusEnemyFactory implements EnemyAircraftFactory {

    @Override
    public EnemyAircraft createEnemyAircraft() {

        return new ElitePlusEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_PLUS_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                3,
                3,
                70,
                new ShootContext(new EnemyAircraftScatterShootStrategy()));
    }
}
