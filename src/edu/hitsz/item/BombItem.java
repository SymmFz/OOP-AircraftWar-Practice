package edu.hitsz.item;

import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 炸弹道具类
 */
public class BombItem extends BaseItem {

    private final List<BombObserver> bombExplosionObserver = new LinkedList<>();

    public BombItem(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void registerObserver(BombObserver observer)  {
        bombExplosionObserver.add(observer);
    }

    public void registerObserver(List<? extends BombObserver> observers) {
        bombExplosionObserver.addAll(observers);
    }

    public void removeObserver(BombObserver observer) {
        bombExplosionObserver.remove(observer);
    }

    public void removeObserver(List<? extends BombObserver> observers) {
        bombExplosionObserver.removeAll(observers);
    }

    private void notifyObserver() {
        for (BombObserver observer : bombExplosionObserver) {
            observer.updateOnBombExplosion();
        }
    }

    // TODO: use a GameContext Class instead of many arguments
    @Override
    public void active(HeroAircraft heroAircraft, ScheduledExecutorService executorService) {
        // TODO: implement active
        System.out.println("BombSupply active!");
        notifyObserver();
    }
}
