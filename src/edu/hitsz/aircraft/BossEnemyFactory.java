package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shootstrategy.EnemyAircraftCircularShootStrategy;

public class BossEnemyFactory extends EnemyAircraftFactory {

    public BossEnemyFactory() {
        this.baseHp = 1000;
        this.baseSpeedY = 0;
        this.basePower = 5;
        this.baseShootNum = 20;
        resetProperties();
    }

    @Override
    public EnemyAircraft createEnemyAircraft() {

        return new BossEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                3,
                speedY,
                hp,
                0, shootNum, power, new EnemyAircraftCircularShootStrategy(),
                0.6, 3);
    }
}
