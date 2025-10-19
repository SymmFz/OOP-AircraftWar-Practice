package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.shootstrategy.EnemyAircraftCircularShootStrategy;

public class BossEnemy extends EnemyAircraft {

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp,
                0, 20, 5, new EnemyAircraftCircularShootStrategy(),
                0.7, 3);
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
        return 1500;
    }
}
