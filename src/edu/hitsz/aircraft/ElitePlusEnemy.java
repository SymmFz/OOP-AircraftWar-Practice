package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.item.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ElitePlusEnemy extends EnemyAircraft {

    private int shootNum = 3;
    private int power = 5;
    private int direction = 1;

    // 子弹间隔角度，单位：度
    private static final int BULLET_SEP_ANGEL = 12;

    // 掉落各类道具的概率
    private static final double ITEM_DROP_CHANCE = 0.6;

    private static final int HEALING_ITEM_WEIGHT = 40;
    private static final int FIRE_POWER_UP_WEIGHT = 30;
    private static final int BOMB_ITEM_WEIGHT = 30;

    private static final int TOTAL_WEIGHT = HEALING_ITEM_WEIGHT + FIRE_POWER_UP_WEIGHT + BOMB_ITEM_WEIGHT;

    private static final Random random = new Random();

    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedX;
        int speedY = this.getSpeedY() + direction * 5;
        BaseBullet bullet;
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            speedX = (int) (speedY * (i - (shootNum / 2)) *
                    Math.tan(Math.toRadians((double) BULLET_SEP_ANGEL)));
            bullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
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
