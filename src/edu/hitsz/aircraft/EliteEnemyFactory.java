package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shootstrategy.EnemyAircraftDirectShootStrategy;

public class EliteEnemyFactory extends EnemyAircraftFactory {

    public EliteEnemyFactory() {
        this.baseHp = 50;
        this.baseSpeedY = 3;
        this.basePower = 5;
        this.baseShootNum = 1;
        resetProperties();
    }

    @Override
    public EnemyAircraft createEnemyAircraft() {

        return new EliteEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                speedY,
                hp,
                1, shootNum, power, new EnemyAircraftDirectShootStrategy(),
                0.6, 1);
    }
}
