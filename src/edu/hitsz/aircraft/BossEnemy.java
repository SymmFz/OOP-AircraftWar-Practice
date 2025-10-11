package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.item.*;
import edu.hitsz.shootstrategy.EnemyAircraftCircularShootStrategy;
import edu.hitsz.shootstrategy.ShootContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BossEnemy extends EnemyAircraft {

    private ShootContext shootContext = new ShootContext(new EnemyAircraftCircularShootStrategy());

    private int shootNum = 20;
    private int power = 5;
    private int direction = 0;

    // 掉落各类道具的概率
    private static final double ITEM_DROP_CHANCE = 0.7;

    private static final int HEALING_ITEM_WEIGHT = 40;
    private static final int FIRE_POWER_UP_WEIGHT = 30;
    private static final int BOMB_ITEM_WEIGHT = 30;

    private static final int TOTAL_WEIGHT = HEALING_ITEM_WEIGHT + FIRE_POWER_UP_WEIGHT + BOMB_ITEM_WEIGHT;

    private static final Random random = new Random();

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
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
    public List<BaseBullet> shoot() {
        return this.shootContext.shoot(this, this.direction, this.shootNum, this.power);
    }

    @Override
    public List<BaseItem> dropItems() {
        List<BaseItem> res = new LinkedList<>();
        ItemFactory itemFactory;

        for (int i = 0; i < 3; i++) {
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

                res.add(itemFactory.createItem(this.locationX + (i - 1) * 25, this.locationY));
            }
        }
        return res;
    }

}
