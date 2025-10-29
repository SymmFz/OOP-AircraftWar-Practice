package edu.hitsz.shootstrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 回旋扇射策略
 * 发射一个扇形弹幕，并且扇形会周期性地左右旋转
 */
public class HeroAircraftFanShootStrategy implements ShootStrategy {

    private static int shootCounter = 0;

    @Override
    public int getDefaultShootNum() {
        return 5;
    }

    @Override
    public List<BaseBullet> shoot(int aircraftLocationX, int aircraftLocationY, int aircraftSpeedX, int aircraftSpeedY,
                                  int direction, int shootNum, int power) {
        List<BaseBullet> res = new LinkedList<>();
        int baseSpeed = 16;
        int y = aircraftLocationY + direction * 2;

        int fanTotalAngle = 75;
        int rotationPeriod = 20;
        int maxRotationAngle = 35;

        double rotationAngle = maxRotationAngle * Math.sin(2 * Math.PI * shootCounter / rotationPeriod);
        shootCounter++;

        double angleStep = (shootNum > 1) ? (double) fanTotalAngle / (shootNum - 1) : 0;

        double startAngle = -fanTotalAngle / 2.0;

        for (int i = 0; i < shootNum; i++) {
            double fanAngle = startAngle + i * angleStep;
            double finalAngle = fanAngle + rotationAngle;

            int speedX = (int) (baseSpeed * Math.sin(Math.toRadians(finalAngle)));
            int speedY = (int) (baseSpeed * Math.cos(Math.toRadians(finalAngle))) * direction;

            BaseBullet bullet = new HeroBullet(aircraftLocationX, y, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }
}
