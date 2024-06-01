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

    public GameObject(GameObject g){
        this.gulden_value = g.gulden_value;
        this.id = g.id;
        this.name = g.name;
        this.description = g.description;
    }

    public void setID(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    //bisa di override
    public int get_value(){
        return gulden_value;
    };

    public String GetName(){
        return name;
    }
}