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
    private AnimalType type;

    public Animal(String name, int weight, int weightToHarvest, Product harvestProduct, AnimalType type){
        super(name, "animal");
        this.weight = weight;
        this.weightToHarvest = weightToHarvest;
        this.harvestProduct = harvestProduct;
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
        boolean canEat = false;

        //Process

        return canEat;
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
