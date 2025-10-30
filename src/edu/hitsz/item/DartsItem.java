        package edu.hitsz.item;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shootstrategy.HeroAircraftFanShootStrategy;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DartsItem extends BaseItem {

    private static final int DURATION_SECONDS = 16;

    public DartsItem(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    // TODO: use a GameContext Class instead of many arguments
    @Override
    public void active(HeroAircraft heroAircraft, ScheduledExecutorService executorService) {
        System.out.println("FireSupply active!");

        heroAircraft.upgradeShootingStrategyForPeriod(
                new HeroAircraftFanShootStrategy(),
                DURATION_SECONDS,
                TimeUnit.SECONDS,
                executorService
        );
    }
}
