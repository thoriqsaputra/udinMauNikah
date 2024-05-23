package org.AwalUdin.Programming;

enum ProductType {
    UNDEFINED, //ini bisa juga kalo suatu object "typeless"
    PLANT,
    MEAT
}

//note : ada keuntungan nya kalo misalkan product type dijadiin interface, kalo misalnya mau nambah extra behaviour
// tapi itu juga bisa ditangkas dengan API or something
//this applies to Animal aswell
public class Product extends GameObject {
    private String name;
    private int price;
    private int weight;
    private ProductType type;

    public Product(String name){
        super(name, "product");
    }

    public Product(String name, int price, int weight, ProductType type) {
        super(name, "product");
        this.price = price;
        this.weight = weight;
        this.type = type;
    }

    public Product(Product otherProduct) {
        super(otherProduct.name, "product");
        this.price = otherProduct.price;
        this.weight = otherProduct.weight;
        this.type = otherProduct.type;
    }

    public String getName() {
        return name;
    }

    public ProductType getProductType(){
        return type;
    }

    public int getPrice() {
        return price;
    }

    public int getWeight() {
        return weight;
    }
    

}
