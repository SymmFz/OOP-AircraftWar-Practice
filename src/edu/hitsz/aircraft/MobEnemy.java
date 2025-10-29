package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.shootstrategy.EnemyAircraftNoShootStrategy;
import edu.hitsz.shootstrategy.ShootStrategy;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends EnemyAircraft {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp,
        int direction, int shootNum, int power, ShootStrategy shootStrategy,
        double itemDropChance, int maxItemNum) {
            super(locationX, locationY, speedX, speedY, hp,
                    direction, shootNum, power, shootStrategy,
                    itemDropChance, maxItemNum);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public int getScoreNum() {
        return 100;
    }

    @Override
    public void updateOnBombExplosion() {
        this.vanish();
    }
}
