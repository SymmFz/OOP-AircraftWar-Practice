package edu.hitsz.shootstrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class ShootContext {

    private ShootStrategy strategy;

    public ShootContext(ShootStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(ShootStrategy strategy) {
        this.strategy = strategy;
    }

    public List<BaseBullet> shoot(AbstractAircraft aircraft, int direction, int shootNum, int power) {
       return this.strategy.shoot(aircraft, direction, shootNum, power);
    }
}
