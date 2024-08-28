package edu.fvtc.grocerylist.models;

public class Product {

    private int id;
    private String productDescription;
    private int isOnShoppingList;
    private int isInCart;

    public Product(){
        this.productDescription = "";
        this.isOnShoppingList = 0;
        this.isInCart = 0;
    }
    public Product(int id, String productDescription, int isOnShoppingList, int isInCart) {
        this.id = id;
        this.productDescription = productDescription;
        this.isOnShoppingList = isOnShoppingList;
        this.isInCart = isInCart;
    }

    public Product(String productDescription, int isOnShoppingList, int isInCart) {
        this.productDescription = productDescription;
        this.isOnShoppingList = isOnShoppingList;
        this.isInCart = isInCart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getIsOnShoppingList() {
        return isOnShoppingList;
    }

    public void setIsOnShoppingList(int isOnShoppingList) {
        this.isOnShoppingList = isOnShoppingList;
    }

    public int getIsInCart() {
        return isInCart;
    }

    public void setIsInCart(int isInCart) {
        this.isInCart = isInCart;
    }

    @Override
    public String toString() {
        return  id+"|"+productDescription + "|" + isOnShoppingList + "|" + isInCart;
    }
}
