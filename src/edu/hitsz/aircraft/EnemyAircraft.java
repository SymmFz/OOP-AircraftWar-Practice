package edu.hitsz.aircraft;

import edu.hitsz.item.*;
import edu.hitsz.shootstrategy.ShootStrategy;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 所有敌机的父类
 */
public abstract class EnemyAircraft extends AbstractAircraft {

    protected double itemDropChance;
    protected int maxItemNum;

    public EnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp,
                         int direction, int shootNum, int power, ShootStrategy shootStrategy,
                         double itemDropChance, int maxItemNum) {
        super(locationX, locationY, speedX, speedY, hp, direction, shootNum, power, shootStrategy);
        this.itemDropChance = itemDropChance;
        this.maxItemNum = maxItemNum;
    }

    // 掉落各类道具的概率
    private static final int HEALING_ITEM_WEIGHT = 300;
    private static final int FIRE_POWER_UP_WEIGHT = 150;
    private static final int BOMB_ITEM_WEIGHT = 50;
    private static final int FIRE_POWER_UP_PLUS_WEIGHT = 100;

    private static final int TOTAL_WEIGHT = HEALING_ITEM_WEIGHT + FIRE_POWER_UP_WEIGHT + BOMB_ITEM_WEIGHT + FIRE_POWER_UP_PLUS_WEIGHT;

    private static final Random RANDOM_INSTANCE = new Random();

    public List<BaseItem> dropItems() {
        List<BaseItem> res = new LinkedList<>();
        ItemFactory itemFactory;

        for (int i = 0; i < this.maxItemNum; i++) {
            boolean dropItemFlag = Math.random() < this.itemDropChance;
            if (dropItemFlag) {
                int roll = RANDOM_INSTANCE.nextInt(TOTAL_WEIGHT);

                if (roll < HEALING_ITEM_WEIGHT) {
                    itemFactory = new HealingItemFactory();
                } else if (roll < HEALING_ITEM_WEIGHT + FIRE_POWER_UP_WEIGHT) {
                    itemFactory = new FirePowerUpItemFactory();
                } else if (roll < HEALING_ITEM_WEIGHT + FIRE_POWER_UP_WEIGHT + FIRE_POWER_UP_PLUS_WEIGHT){
                    itemFactory = new FirePowerUpPlusItemFactory();
                } else {
                    itemFactory = new BombItemFactory();
                }

                res.add(itemFactory.createItem(this.locationX + (i * 2 - shootNum + 1) * 10, this.locationY));
            }
        }
        return res;
    }
}
