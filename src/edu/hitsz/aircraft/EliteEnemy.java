package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.item.BaseItem;
import edu.hitsz.item.BombItem;
import edu.hitsz.item.FirePowerUpItem;
import edu.hitsz.item.HealingItem;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EliteEnemy extends EnemyAircraft {

    private int shootNum = 1;
    private int power = 10;
    private int direction = 1;

    // 掉落各类道具的概率
    private static final double ITEM_DROP_CHANCE = 0.3;

    private static final int HEALING_ITEM_WEIGHT = 40;
    private static final int FIRE_POWER_UP_WEIGHT = 30;
    private static final int BOMB_ITEM_WEIGHT = 30;

    private static final int TOTAL_WEIGHT = HEALING_ITEM_WEIGHT + FIRE_POWER_UP_WEIGHT + BOMB_ITEM_WEIGHT;

    private static final Random random = new Random();

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction*5;
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }

    @Override
    public List<BaseItem> dropItems() {
        List<BaseItem> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY();
        int speedX = 0;
        int speedY = 3;
        BaseItem item;

        boolean dropItemFlag = Math.random() < ITEM_DROP_CHANCE;
        if (dropItemFlag) {
            int roll = random.nextInt(TOTAL_WEIGHT);

            if (roll < HEALING_ITEM_WEIGHT) {
                item =  new HealingItem(x, y, speedX, speedY);
                res.add(item);
            } else if (roll < HEALING_ITEM_WEIGHT + FIRE_POWER_UP_WEIGHT) {
                item = new FirePowerUpItem(x, y, speedX, speedY);
                res.add(item);
            } else if (roll < HEALING_ITEM_WEIGHT + FIRE_POWER_UP_WEIGHT + BOMB_ITEM_WEIGHT) {
                item = new BombItem(x, y, speedX, speedY);
                res.add(item);
            }
        }

        return res;
    }


}
