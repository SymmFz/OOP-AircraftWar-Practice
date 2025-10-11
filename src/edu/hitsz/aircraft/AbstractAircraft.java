package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.shootstrategy.ShootContext;
import edu.hitsz.shootstrategy.ShootStrategy;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {

    protected ShootContext shootContext;

    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp, ShootContext shootContext) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.shootContext = shootContext;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public void increaseHp(int increase) {
        hp += increase;
        if (hp >= 100) {
            hp = 100;
        }
    }

    public int getHp() {
        return hp;
    }

    public void changeShootStrategy(ShootStrategy shootStrategy) {
        this.shootContext.setStrategy(shootStrategy);
    }

    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public abstract List<BaseBullet> shoot();

}


