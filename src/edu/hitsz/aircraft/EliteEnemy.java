package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.shootstrategy.ShootStrategy;

public class EliteEnemy extends EnemyAircraft {

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp,
        int direction, int shootNum, int power, ShootStrategy shootStrategy,
        double itemDropChance, int maxItemNum) {
            super(locationX, locationY, speedX, speedY, hp,
                    direction, shootNum, power, shootStrategy,
                    itemDropChance, maxItemNum);
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
        return 300;
    }

    @Override
    public void updateOnBombExplosion() {
        this.vanish();
    }
}
