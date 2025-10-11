package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.item.*;
import edu.hitsz.shootstrategy.ShootContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EliteEnemy extends EnemyAircraft {

    // 掉落各类道具的概率
    private static final double ITEM_DROP_CHANCE = 0.5;

    private static final int HEALING_ITEM_WEIGHT = 40;
    private static final int FIRE_POWER_UP_WEIGHT = 30;
    private static final int BOMB_ITEM_WEIGHT = 30;

    private static final int TOTAL_WEIGHT = HEALING_ITEM_WEIGHT + FIRE_POWER_UP_WEIGHT + BOMB_ITEM_WEIGHT;

    private static final Random random = new Random();

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp,
                      int direction, int shootNum, int power, ShootContext shootContext) {
        super(locationX, locationY, speedX, speedY, hp,
              direction, shootNum, power, shootContext);
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
    public List<BaseItem> dropItems() {
        List<BaseItem> res = new LinkedList<>();
        ItemFactory itemFactory;

        boolean dropItemFlag = Math.random() < ITEM_DROP_CHANCE;
        if (dropItemFlag) {
            int roll = random.nextInt(TOTAL_WEIGHT);

            if (roll < HEALING_ITEM_WEIGHT) {
                itemFactory = new HealingItemFactory();
            } else if (roll < HEALING_ITEM_WEIGHT + FIRE_POWER_UP_WEIGHT) {
                itemFactory = new FirePowerUpItemFactory();
            } else {
                itemFactory = new BombItemFactory();
            }

            res.add(itemFactory.createItem(this.locationX, this.locationY));
        }

        return res;
    }

}
