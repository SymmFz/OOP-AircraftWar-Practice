package edu.hitsz.shootstrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class HeroAircraftScatterShootStrategy implements ShootStrategy{

    public List<BaseBullet> shoot(AbstractAircraft aircraft, int direction,
                                  int shootNum, int power) {
        List<BaseBullet> res = new LinkedList<>();
        int baseSpeedY = 7;
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + direction * 2;
        int speedX = aircraft.getSpeedX();
        int speedY = aircraft.getSpeedY() + direction * baseSpeedY;
        BaseBullet bullet;
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y,
                    speedX + (i * 2 - shootNum + 1), speedY, power);
            res.add(bullet);
        }
        return res;
    }
}
