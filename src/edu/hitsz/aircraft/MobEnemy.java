package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.shootstrategy.ShootContext;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends EnemyAircraft {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp,
                    int direction, int shootNum, int power, ShootContext shootContext,
                    double itemDropChance, int maxItemNum) {
        super(locationX, locationY, speedX, speedY, hp,
                direction, shootNum, power, shootContext,
                itemDropChance, maxItemNum);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }
}
