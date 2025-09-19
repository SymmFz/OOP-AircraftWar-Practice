package edu.hitsz.item;

import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * 炸弹道具类
 */
public class BombItem extends BaseItem {

    public BombItem(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    // TODO: use a GameContext Class instead of many arguments
    @Override
    public void active(HeroAircraft heroAircraft, List<EnemyAircraft> enemyAircrafts, List<BaseBullet> enemyBullets) {
        // TODO: implement active
        System.out.println("BombSupply active!");
    }
}
