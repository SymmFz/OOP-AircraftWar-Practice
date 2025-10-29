package edu.hitsz.aircraft;

public abstract class EnemyAircraftFactory {

    protected int baseHp = 70;
    protected int baseSpeedY = 3;
    protected int basePower = 5;
    protected int baseShootNum = 3;

    protected int hp;
    protected int speedY;
    protected int power;
    protected int shootNum;

    public EnemyAircraftFactory() {
        resetProperties();
    }

    public abstract EnemyAircraft createEnemyAircraft();

    public void resetProperties() {
        this.hp = this.baseHp;
        this.speedY = this.baseSpeedY;
        this.power = this.basePower;
        this.shootNum = this.baseShootNum;
    }

    public void setPropertyHp(double rate) {
        this.hp = (int) (baseHp * rate);
    }

    public void setPropertySpeedY(double rate) {
        this.speedY = (int) (baseSpeedY * rate);
    }

    public void setPropertyPower(double rate) {
        this.power = (int) (basePower * rate);
    }

    public void setPropertyShootNum(double rate) {
        this.shootNum = (int) (baseShootNum * rate);
    }

    public void increasePropertyHp(int amount) {
        baseHp += amount;
        this.hp += amount;
    }

    public void increasePropertySpeedY(int amount) {
        baseSpeedY += amount;
        this.speedY += amount;
    }

    public void increasePropertyPower(int amount) {
        basePower += amount;
        this.power += amount;
    }

    public void increasePropertyShootNum(int amount) {
        baseShootNum += amount;
        this.shootNum += amount;
    }
}
