package edu.hitsz.item;

public class FirePowerUpItemFactory implements ItemFactory {

    @Override
    public BaseItem createItem(int locationX, int locationY) {

        int speedX = 0;
        int speedY = 3;

        return new FirePowerUpItem(locationX, locationY, speedX, speedY);
    }
}
