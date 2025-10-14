package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shootstrategy.EnemyAircraftDirectShootStrategy;

public class EliteEnemyFactory implements EnemyAircraftFactory {

    @Override
    public EnemyAircraft createEnemyAircraft() {

        return new EliteEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                3,
                50,
                1, 1, 5, new EnemyAircraftDirectShootStrategy(),
                0.3, 1);
    }
}
