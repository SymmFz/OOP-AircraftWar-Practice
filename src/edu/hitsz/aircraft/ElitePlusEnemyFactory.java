package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shootstrategy.EnemyAircraftScatterShootStrategy;

public class ElitePlusEnemyFactory extends EnemyAircraftFactory {

    public ElitePlusEnemyFactory() {
        this.baseHp = 70;
        this.baseSpeedY = 3;
        this.basePower = 5;
        this.baseShootNum = 3;
        resetProperties();
    }

    @Override
    public EnemyAircraft createEnemyAircraft() {

        return new ElitePlusEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_PLUS_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                3,
                speedY,
                hp,
        1, shootNum, power, new EnemyAircraftScatterShootStrategy(),
                0.8, 1);
    }
}
