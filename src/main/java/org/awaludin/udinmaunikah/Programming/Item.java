package org.awaludin.udinmaunikah.Programming;

public class Item extends GameObject {
    private String name;
    private int price;
    private Effect effect;

    public Item(String name, int price, Effect effect) {
        this.name = name;
        this.price = price;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Effect getEffect() {
        return effect;
    }
}
