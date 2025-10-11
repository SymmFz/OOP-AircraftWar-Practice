package edu.hitsz.item;

public interface ItemFactory {

    BaseItem createItem(int locationX, int locationY);
}
