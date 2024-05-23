package org.AwalUdin.udinmaunikah.Programming;

public class Petak implements IHarvestable{
   private GameObject gameObject;
   private Item item;

   public Petak(GameObject gameObject) {
       this.gameObject = gameObject;
       this.item = null;
   }

   public Petak(GameObject gameObject, Item item) {
       this.gameObject = gameObject;
       this.item = item;
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
}