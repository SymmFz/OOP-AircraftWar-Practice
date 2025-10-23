package edu.hitsz.item;

import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 道具父类
 */
public abstract class BaseItem extends AbstractFlyingObject {

    public BaseItem(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {
        super.forward();

        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            vanish();
        }

        if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }else if (locationY <= 0){
            vanish();
        }
    }

    // TODO: use a GameContext Class instead of many arguments
    public abstract void active(HeroAircraft heroAircraft, List<EnemyAircraft> enemyAircrafts, List<BaseBullet> enemyBullets,
                                ScheduledExecutorService executorService);
}
