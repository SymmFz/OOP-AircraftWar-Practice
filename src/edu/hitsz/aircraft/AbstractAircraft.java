package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.shootstrategy.ShootStrategy;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {

    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    protected int direction;
    protected int shootNum;
    protected int power;

    protected ShootStrategy shootStrategy;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp,
                            int direction, int shootNum, int power, ShootStrategy shootStrategy) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.direction = direction;
        this.shootNum = shootNum;
        this.power = power;
        this.shootStrategy = shootStrategy;
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

    public void setStrategy(ShootStrategy strategy) {
        this.shootStrategy = strategy;
        this.shootNum = strategy.getDefaultShootNum();
    }

    /**
     * 飞机射击方法
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public List<BaseBullet> shoot() {
        return this.shootStrategy.shoot(this, this.direction, this.shootNum, this.power);
    }

}


