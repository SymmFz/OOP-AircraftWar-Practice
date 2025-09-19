package edu.hitsz.aircraft;

import edu.hitsz.item.BaseItem;

import java.util.List;

/**
 * 所有敌机的父类
 */
public abstract class EnemyAircraft extends AbstractAircraft {
    public EnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    public abstract List<BaseItem> dropItems();
}
