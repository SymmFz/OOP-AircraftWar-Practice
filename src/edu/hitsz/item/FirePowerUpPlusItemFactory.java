package edu.hitsz.item;

public class FirePowerUpPlusItemFactory implements ItemFactory {

    @Override
    public BaseItem createItem(int locationX, int locationY) {

        int speedX = 0;
        int speedY = 3;

        return new FirePowerUpPlusItem(locationX, locationY, speedX, speedY);
    }
}
