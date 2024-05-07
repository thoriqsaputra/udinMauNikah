package org.AwalUdin.Programming;

public class Plant extends GameObject implements IHarvestable, IGrowable{
    private int age;
    private int ageToHarvest;
    private Product harvestProduct;

    public void Tick(int ticks){
        age += ticks;
    };

    public GameObject Harvest(){
        //check

        return harvestProduct;
    };

}
