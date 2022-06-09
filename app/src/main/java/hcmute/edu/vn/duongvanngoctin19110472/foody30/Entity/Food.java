package hcmute.edu.vn.duongvanngoctin19110472.foody30.Entity;

import java.io.Serializable;

public class Food implements Serializable {
    public String nameFood;
    public String descriptionFood;
    public Double priceFood;
    public String storeName;
    public byte[] picFood;

    public Food(String nameFood, String descriptionFood, Double priceFood, String storeName, byte[] picFood) {
        this.nameFood = nameFood;
        this.descriptionFood = descriptionFood;
        this.priceFood = priceFood;
        this.storeName = storeName;
        this.picFood = picFood;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getDescriptionFood() {
        return descriptionFood;
    }

    public void setDescriptionFood(String descriptionFood) {
        this.descriptionFood = descriptionFood;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Double getPriceFood() {
        return priceFood;
    }

    public void setPriceFood(Double priceFood) {
        this.priceFood = priceFood;
    }

    public byte[] getPicFood() {
        return picFood;
    }

    public void setPicFood(byte[] picFood) {
        this.picFood = picFood;
    }
}
