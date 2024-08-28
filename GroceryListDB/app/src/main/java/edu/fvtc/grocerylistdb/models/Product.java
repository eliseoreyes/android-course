package edu.fvtc.grocerylistdb.models;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.EditText;

import java.io.Serializable;

import edu.fvtc.grocerylistdb.R;

public class Product implements Serializable {

    private String productDescription;
    private boolean isOnShoppingList;
    private boolean isInCart;
    private int id;
    private String owner;
    private Double longitue;
    private Double latitude;
    private Bitmap photo;

    public Product(){

    }
    public Product(String productDescription, boolean isOnShoppingList, boolean isInCart) {
        this.productDescription = productDescription;
        this.isOnShoppingList = isOnShoppingList;
        this.isInCart = isInCart;
    }

    public Product(int id, String productDescription, boolean isOnShoppingList, boolean isInCart) {
        this.id = id;
        this.productDescription = productDescription;
        this.isOnShoppingList = isOnShoppingList;
        this.isInCart = isInCart;
    }
    public Product(int id, String productDescription, boolean isOnShoppingList, boolean isInCart, String owner, Double longitude, Double latitude ) {
        this.id = id;
        this.productDescription = productDescription;
        this.isOnShoppingList = isOnShoppingList;
        this.isInCart = isInCart;
        this.owner = owner;
        this.longitue = longitude;
        this.latitude = latitude;
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

    public boolean getIsOnShoppingList() {
        return isOnShoppingList;
    }

    public void setIsOnShoppingList(boolean isOnShoppingList) {
        this.isOnShoppingList = isOnShoppingList;
    }

    public boolean getIsInCart() {
        return isInCart;
    }

    public void setIsInCart(boolean isInCart) {
        this.isInCart = isInCart;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Double getLongitue() {
        return longitue;
    }

    public void setLongitue(Double longitue) {
        this.longitue = longitue;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public void setControlText(int controlId, String value) {
        Log.d("Product", "setControlText: " + value );

        if(controlId == R.id.etProduct){
            this.setProductDescription(value);
        }
    }
    @Override
    public String toString() {
        return  id + "|" + productDescription + "|" + isOnShoppingList + "|" + isInCart +"|"+owner+"|"+latitude+"|"+longitue;
    }
}
