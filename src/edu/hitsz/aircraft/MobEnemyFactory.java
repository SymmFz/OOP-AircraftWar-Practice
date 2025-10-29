package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shootstrategy.EnemyAircraftNoShootStrategy;

public class MobEnemyFactory extends EnemyAircraftFactory {

    public MobEnemyFactory() {
        this.baseHp = 30;
        this.baseSpeedY = 3;
        this.basePower = 5;
        this.baseShootNum = 0;
        resetProperties();
    }

    @Override
    public EnemyAircraft createEnemyAircraft() {

        return new MobEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                speedY,
                hp,
                1, shootNum, power, new EnemyAircraftNoShootStrategy(),
                0, 0
        );
    }
}
