package edu.hitsz.item;

public class HealingItemFactory implements ItemFactory {

    @Override
    public BaseItem createItem(int locationX, int locationY) {

        int speedX = 0;
        int speedY = 3;

        return new HealingItem(locationX, locationY, speedX, speedY);
    }
}
