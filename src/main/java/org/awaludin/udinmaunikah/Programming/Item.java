package org.awaludin.udinmaunikah.Programming;

public class Item extends GameObject {
    private int price;
    private Effect effect;

    public Item(String name, int price, Effect effect) {
        super(name, "Item");
        this.price = price;
        this.effect = effect;
    }


    public int getPrice() {
        return price;
    }

    public Effect getEffect() {
        return effect;
    }
}
