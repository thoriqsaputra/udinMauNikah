package org.awaludin.udinmaunikah.Programming;

import java.util.ArrayList;

public class Animal extends GameObject implements IFeedable, IHarvestable{
    private int weight;
    private final int weightToHarvest;
    private Product harvestProduct;
    private final AnimalType type;

    public Animal(String name, int weight, int weightToHarvest, Product harvestProduct, AnimalType type){
        super(name, "animal");
        this.weight = weight;
        this.weightToHarvest = weightToHarvest;
        this.harvestProduct = harvestProduct;
        this.type = type;
    }

    public Animal(String name, int weight, int weightToHarvest,  AnimalType type){
        super(name, "animal");
        this.weight = weight;
        this.weightToHarvest = weightToHarvest;
        this.type = type;
    }

    public int GetWeight(){
        return weight;
    }

    public int GetWeightToHarvest(){
        return weightToHarvest;
    }

    public Product GetHarvestProduct(){
        return harvestProduct;
    }

    public AnimalType GetType(){
        return type;
    }

    public boolean isReadyToHarvest(){
        return weight >= weightToHarvest;
    }

    public boolean isEatAble(Product food){
        if (food.getProductType() == ProductType.PLANT) {
            if (type == AnimalType.CARNIVORE) {
                return false;
            } else {
                Feed(food.getWeight());
                return true;
            }
        } else if (food.getProductType() == ProductType.MEAT) {
            if (type == AnimalType.HERBIVORE) {
                return false;
            } else {
                Feed(food.getWeight());
                return true;
            }
        } else if (food.getProductType() == ProductType.BOTH){
            Feed(food.getWeight());
            return true;
        }
        return false;
    }

    

    public void Feed(int nutrient){
        weight += nutrient;
    }

    public GameObject Harvest(){
        //CHECK HERE
        return harvestProduct;
    }

    public boolean GiveToAnimal(Product food){
        boolean success = false;

        //Process

        return success;
    };
}
