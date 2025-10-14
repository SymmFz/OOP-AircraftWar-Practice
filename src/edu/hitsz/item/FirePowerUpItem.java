package edu.hitsz.item;

import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.shootstrategy.HeroAircraftScatterShootStrategy;

import java.util.List;

/**
 * 火力道具类
 */
public class FirePowerUpItem extends BaseItem {

    public FirePowerUpItem(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    // TODO: use a GameContext Class instead of many arguments
    @Override
    public void active(HeroAircraft heroAircraft, List<EnemyAircraft> enemyAircrafts, List<BaseBullet> enemyBullets) {
        System.out.println("FireSupply active!");
        heroAircraft.setStrategy(new HeroAircraftScatterShootStrategy());
    }
}
