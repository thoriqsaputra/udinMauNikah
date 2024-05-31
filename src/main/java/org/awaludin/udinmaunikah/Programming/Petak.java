package org.awaludin.udinmaunikah.Programming;

import javafx.scene.shape.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.awaludin.udinmaunikah.CardBrain;
import org.awaludin.udinmaunikah.Programming.Effect.Layout;
import org.awaludin.udinmaunikah.Programming.Item;

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
        this.item.clear();
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public Map<Item, Integer> getItem() {
        return item;
    }

    public String getItemsToList() {
        StringBuilder temp = new StringBuilder();
        for (Map.Entry<Item, Integer> entry : item.entrySet()) {
            // Append item and its quantity to the string builder
            temp.append(entry.getKey().GetName())
                    .append(" (")
                    .append(entry.getValue())
                    .append("), ");
        }

        // Remove the trailing comma and space
        if (temp.length() > 0) {
            temp.setLength(temp.length() - 2);
        }

        System.out.println(temp.toString());

        return temp.toString();
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

    public void kurangItems(GameObject item) {
        if (item instanceof Item) {
            int quantity = 0;
            Item baru = (Item) item;
            for (Entry<Item, Integer> x : this.item.entrySet()) {
                if (x.getKey().equals(baru)) {
                    quantity = x.getValue();
                }
            }
            if (quantity > 1) {
                this.item.replace(baru, quantity - 1);
            } else if (quantity == 1){
                this.item.remove(baru);
            } else {
                return;
            }
        }
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

    public int getCount(){
        int count=0;
        for(var x : item.entrySet()){
            count += x.getValue();
        }
        return count;
    }

}