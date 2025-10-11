package edu.hitsz.shootstrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class EnemyAircraftCircularShootStrategy implements ShootStrategy {

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft, int direction,
                                  int shootNum, int power) {
        List<BaseBullet> res = new LinkedList<>();
        int locationVectorLen = 10;
        int baseSpeed = 10;
        int aircraftLocationX = aircraft.getLocationX();
        int aircraftLocationY = aircraft.getLocationY();
        int aircraftSpeedX = aircraft.getSpeedX();
        int aircraftSpeedY = aircraft.getSpeedY();
        int bulletSepAngel = 360 / shootNum;
        int bulletCurrentAngel;
        int x, y;
        int speedX, speedY;
        BaseBullet bullet;
        for (int i = 0; i < shootNum; i++) {
            bulletCurrentAngel = i * bulletSepAngel;
            x = aircraftLocationX + (int) (Math.sin(Math.toRadians(bulletCurrentAngel)) * locationVectorLen);
            y = aircraftLocationY + (int) (Math.cos(Math.toRadians(bulletCurrentAngel)) * locationVectorLen);
            speedX = aircraftSpeedX + (int) (Math.sin(Math.toRadians(bulletCurrentAngel)) * baseSpeed);
            speedY = aircraftSpeedY + (int) (Math.cos(Math.toRadians(bulletCurrentAngel)) * baseSpeed);
            bullet = new EnemyBullet(x, y, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }

}
