package edu.hitsz.item;

public class BombItemFactory implements ItemFactory {

    @Override
    public BaseItem createItem(int locationX, int locationY) {

        int speedX = 0;
        int speedY = 3;

        return new BombItem(locationX, locationY, speedX, speedY);
    }
}
