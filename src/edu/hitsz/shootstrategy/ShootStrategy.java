package edu.hitsz.shootstrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface ShootStrategy {

   int getDefaultShootNum();

    List<BaseBullet> shoot(AbstractAircraft aircraft, int direction, int shootNum, int power);
}
