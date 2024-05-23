package org.AwalUdin.udinmaunikah.Programming;

public class Plant extends GameObject implements IHarvestable, IGrowable{
    private int age;
    private int ageToHarvest;
    private Product harvestProduct;

    public Plant(String name, int ageToHarvest, Product harvestProduct){
        super(name, "plant");
        this.ageToHarvest = ageToHarvest;
        this.harvestProduct = harvestProduct;
    }

    public int GetAge(){
        return age;
    }

    public int GetAgeToHarvest(){
        return ageToHarvest;
    }

    public Product GetHarvestProduct(){
        return harvestProduct;
    }

    public boolean isReadyToHarvest(){
        return age >= ageToHarvest;
    }

    public void Tick(int ticks){
        age += ticks;
    };

    public GameObject Harvest(){
        //check

        return harvestProduct;
    };

}
