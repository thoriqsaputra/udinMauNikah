package org.awaludin.udinmaunikah.Programming;

import javafx.scene.shape.Rectangle;

public class Petak implements IHarvestable{
   private GameObject gameObject;
   private Item item;
   private Rectangle rectangle;
   private boolean enabled;

   public Petak(Rectangle rectangle) {
       this.gameObject = null;
       this.enabled = true;
       this.item = null;
       this.rectangle = rectangle;
   }

   public Petak(Rectangle rectangle, boolean enabled) {
       this.gameObject = null;
       this.enabled = enabled;
       this.item = null;
       this.rectangle = rectangle;
       this.rectangle.setOpacity(0.3);
       this.rectangle.setMouseTransparent(true);
   }

   public GameObject getGameObject() {
       return gameObject;
   }

   public void setGameObject(GameObject gameObject) {
       this.gameObject = gameObject;
   }

   public Item getItem() {
       return item;
   }

   public void setItem(Item item) {
       this.item = item;
   }

   public void pakaiItem() {
       item.use(this.gameObject);
   }

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
       return gameObject == null;
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