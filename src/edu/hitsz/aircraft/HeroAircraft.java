package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.shootstrategy.HeroAircraftDirectShootStrategy;
import edu.hitsz.shootstrategy.ShootContext;
import edu.hitsz.shootstrategy.ShootStrategy;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * 
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    // 静态内部类，为了实现单例模式的测试，需要设置类为 package-private，内部的实例为 private
    static class HeroAircraftHolder {
        private static HeroAircraft heroAircraft = new HeroAircraft(
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                0, 0, 100);

        // reset 方法仅用于在单元测试中重置单例。
        static void reset() {
            heroAircraft = new HeroAircraft(
                    Main.WINDOW_WIDTH / 2,
                    Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                    0, 0, 100);
        }
    }

    private ShootContext shootContext = new ShootContext(new HeroAircraftDirectShootStrategy());

    /** 攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：-1，向下发射：1)
     */
    private int direction = -1;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp        初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    public static HeroAircraft getInstance() {
        return HeroAircraftHolder.heroAircraft;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /**
     * 通过射击产生子弹
     * 
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        return this.shootContext.shoot(this, this.direction, this.shootNum, this.power);
    }

    public void firePowerUp() {
        if (this.shootNum <= 6) {
            this.shootNum += 1;
            if (this.shootNum <= 2) {
                this.power -= 30 / this.shootNum;
                this.power += 5;
            }
        } else {
            this.power += 5;
        }
    }

}
