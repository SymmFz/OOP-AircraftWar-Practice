package edu.hitsz.item;

import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 加血道具类
 */
public class HealingItem extends BaseItem {

    private static final int HEALING_AMOUNT = 30;

    public HealingItem(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    // TODO: use a GameContext Class instead of many arguments
    @Override
    public void active(HeroAircraft heroAircraft, ScheduledExecutorService executorService) {
        heroAircraft.increaseHp(HEALING_AMOUNT);
        System.out.println("Healing Item active: healing " + HEALING_AMOUNT);
    }
}
