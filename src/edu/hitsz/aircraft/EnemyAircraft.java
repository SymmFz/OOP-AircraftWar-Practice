package edu.hitsz.aircraft;

import edu.hitsz.item.BaseItem;
import edu.hitsz.shootstrategy.ShootContext;

import java.util.List;

/**
 * 所有敌机的父类
 */
public abstract class EnemyAircraft extends AbstractAircraft {
    public EnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp,
                         int direction, int shootNum, int power, ShootContext shootContext) {
        super(locationX, locationY, speedX, speedY, hp,
              direction, shootNum, power, shootContext);
    }

    public abstract List<BaseItem> dropItems();
}
