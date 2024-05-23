package org.awaludin.udinmaunikah.Programming;

public class GameObject {
    protected int gulden_value;
    protected String id;
    private String name;
    private String description;

    public GameObject(String name, String description){
        this.name = name;
        this.description = description;
    }

    public GameObject(String name, String description, String id){}

    public GameObject(){
        this("BLANK","<BLANK DESCRIPTION>");
    }

    public void setID(String id){
        this.id = id;
    }

    //bisa di override
    public int get_value(){
        return gulden_value;
    };

    public String GetName(){
        return name;
    }
}