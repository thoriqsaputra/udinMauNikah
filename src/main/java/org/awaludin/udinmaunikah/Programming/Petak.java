package org.awaludin.udinmaunikah.Programming;

import javafx.scene.shape.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.awaludin.udinmaunikah.CardBrain;
import org.awaludin.udinmaunikah.Programming.Effect.Layout;

public class Petak implements IHarvestable{
    private GameObject gameObject;
    private Map<Item, Integer> item;

    private Rectangle rectangle;
    private boolean enabled;
    private CardBrain.cardObj cardObj;

    public Petak(Rectangle rectangle) {
        this.gameObject = null;
        this.enabled = true;
        this.item = new HashMap<Item,Integer>();
        this.rectangle = rectangle;
        this.cardObj = null;
    }

    public Petak(Rectangle rectangle, boolean enabled) {
        this.gameObject = null;
        this.enabled = enabled;
        this.item = new HashMap<>();
        this.rectangle = rectangle;
        this.rectangle.setOpacity(0.3);
        this.rectangle.setMouseTransparent(true);
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setCardObj(CardBrain.cardObj cardObj) {
        this.cardObj = cardObj;
        this.gameObject = cardObj.getGameObject();
    }

    public CardBrain.cardObj getCardObj() {
        return cardObj;
    }

    public void setNull(){
        this.gameObject = null;
        this.cardObj = null;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public Map<Item, Integer> getItem() {
        return item;
    }

    public void setItem(Item item) {
        for (Entry<Item, Integer> hitem : this.item.entrySet()) {
            if (hitem.getKey() == item) {
                this.item.replace(hitem.getKey(), hitem.getValue()+1);
                item.getEffect().applyEffect(this);
                return;
            }
        }
        this.item.put(item, 1);
        item.getEffect().applyEffect(this);
    }

    // public void setItemBonus(Item item, Ladang ladang, boolean attacking) {
    //     this.item.add(item);
    //     if (item.getEffect() instanceof Layout) {
    //         item.getEffect().applyEffectBonus(attacking, ladang);
    //     }
    // }

    // public void itemBonusHabis(Ladang ladang) {
    //     for (Petak petak : ladang.getGrid()) {
    //         for (Item item : petak.getItem()) {
    //             if (item.getEffect() instanceof Layout) {
    //                 petak.getItem().remove(item);
    //                 continue;
    //             }
    //         }
    //     }
    // }

    @Override
    public GameObject Harvest() {
        return gameObject;
    }

    @Override
    public boolean isReadyToHarvest() {
        return true;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public boolean isEmpty(){
        return cardObj == null;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void disable() {
        this.enabled = false;
        this.rectangle.setOpacity(0.3);
        this.rectangle.setMouseTransparent(true);
    }

    public void enable() {
        this.enabled = true;
        this.rectangle.setOpacity(1.0);
        this.rectangle.setMouseTransparent(false);
    }
}