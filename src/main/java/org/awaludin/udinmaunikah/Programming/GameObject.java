package org.AwalUdin.udinmaunikah.Programming;

public abstract class GameObject {
    protected int gulden_value;
    private String name;
    private String description;

    public GameObject(String name, String description){
        this.name = name;
        this.description = description;
    }

    public GameObject(){
        this("BLANK","<BLANK DESCRIPTION>");
    }

    //bisa di override
    public int get_value(){
        return gulden_value;
    };

    public String GetName(){
        return name;
    }
}