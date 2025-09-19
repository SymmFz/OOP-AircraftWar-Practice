package edu.hitsz.item;

import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * 加血道具类
 */
public class HealingItem extends BaseItem {

    private final int healingAmount = 30;

    public HealingItem(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    // TODO: use a GameContext Class instead of many arguments
    @Override
    public void active(HeroAircraft heroAircraft, List<EnemyAircraft> enemyAircrafts, List<BaseBullet> enemyBullets) {
        heroAircraft.increaseHp(healingAmount);
        System.out.println("Healing Item active: healing " + healingAmount);
    }
}
