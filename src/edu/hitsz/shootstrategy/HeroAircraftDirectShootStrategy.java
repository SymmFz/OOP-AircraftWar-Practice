package edu.hitsz.shootstrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class HeroAircraftDirectShootStrategy implements ShootStrategy{

    @Override
    public int getDefaultShootNum() {
        return 1;
    }

    @Override
    public List<BaseBullet> shoot(int aircraftLocationX, int aircraftLocationY, int aircraftSpeedX, int aircraftSpeedY,
                                  int direction, int shootNum, int power) {
        List<BaseBullet> res = new LinkedList<>();
        int baseSpeedY = 7;
        int x = aircraftLocationX;
        int y = aircraftLocationY + direction * 2;
        int speedX = 0;
        int speedY = aircraftSpeedY + direction * baseSpeedY;
        BaseBullet bullet;
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }
}
