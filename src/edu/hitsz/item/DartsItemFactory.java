package edu.hitsz.item;

public class DartsItemFactory implements ItemFactory {

    @Override
    public BaseItem createItem(int locationX, int locationY) {

        int speedX = 0;
        int speedY = 3;

        return new DartsItem(locationX, locationY, speedX, speedY);
    }
}
