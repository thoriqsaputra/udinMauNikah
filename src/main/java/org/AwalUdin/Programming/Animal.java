package org.AwalUdin.Programming;

enum AnimalType {
    UNDEFINED,
    HERBIVORE,
    CARNIVORE,
    OMNIVORE
}

public class Animal extends GameObject implements IFeedable, IHarvestable{
    private int weight;
    private int weightToHarvest;
    private Product harvestProduct;

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
