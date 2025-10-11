package edu.hitsz.shootstrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

public class EnemyAircraftNoShootStrategy implements ShootStrategy {

    @Override
    public int getDefaultShootNum() {
        return 0;
    }

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft, int direction, int shootNum, int power) {
        return new LinkedList<>();
    }
}
