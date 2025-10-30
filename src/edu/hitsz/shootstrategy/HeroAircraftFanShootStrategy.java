package edu.hitsz.shootstrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.bullet.HeroDartsBullet;

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
        return 10;
    }

    @Override
    public List<BaseBullet> shoot(int aircraftLocationX, int aircraftLocationY, int aircraftSpeedX, int aircraftSpeedY,
                                  int direction, int shootNum, int power) {
        List<BaseBullet> res = new LinkedList<>();
        int baseSpeed = 20;
        int radius1 = 30;
        int radius2 = 60;

        int fanTotalAngle = 60;
        int rotationPeriod = 10;
        int maxRotationAngle = 35;

        double rotationAngle = maxRotationAngle * Math.sin(2 * Math.PI * shootCounter / rotationPeriod);
        shootCounter++;

        int numPerLayer = (int) Math.ceil((double) shootNum / 2);
        double angleStep = (numPerLayer > 1) ? (double) fanTotalAngle / (numPerLayer - 1) : 0;
        double startAngle = -fanTotalAngle / 2.0;

        for (int i = 0; i < shootNum; i++) {
            int layerIndex = i / 2;
            double fanAngle = startAngle + layerIndex * angleStep;

            double finalAngle = fanAngle + rotationAngle;

            int speedX = (int) (baseSpeed * Math.sin(Math.toRadians(finalAngle)));
            int speedY = (int) (baseSpeed * Math.cos(Math.toRadians(finalAngle))) * direction;

            int bulletX;
            int bulletY;
            int currentRadius;

            if (i % 2 == 0) {
                currentRadius = radius1;
            } else {
                currentRadius = radius2;
            }

            bulletX = aircraftLocationX + (int) (currentRadius * Math.sin(Math.toRadians(fanAngle)));
            bulletY = aircraftLocationY + (int) (currentRadius * Math.cos(Math.toRadians(fanAngle))) * direction;

            BaseBullet bullet = new HeroDartsBullet(bulletX, bulletY, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }
}