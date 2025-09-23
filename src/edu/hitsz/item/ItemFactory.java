package edu.hitsz.item;

public interface ItemFactory {

    public abstract BaseItem createItem(int locationX, int locationY);
}
