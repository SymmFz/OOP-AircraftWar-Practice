package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.shootstrategy.EnemyAircraftScatterShootStrategy;

public class ElitePlusEnemy extends EnemyAircraft {

    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp,
                1, 3, 5, new EnemyAircraftScatterShootStrategy(),
                0.8, 1);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public int getScoreNum() {
        return 500;
    }
}
