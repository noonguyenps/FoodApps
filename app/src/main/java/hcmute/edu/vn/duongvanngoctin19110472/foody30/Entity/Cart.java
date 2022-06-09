package hcmute.edu.vn.duongvanngoctin19110472.foody30.Entity;

public class Cart {
    public String foodName, storeFood;
    public int amount;
    public double price;
    public byte[] imgFood;

    public Cart(String foodName, String storeFood, int amount, double price,byte[] imgFood) {
        this.foodName = foodName;
        this.storeFood = storeFood;
        this.amount = amount;
        this.price = price;
        this.imgFood = imgFood;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getStoreFood() {
        return storeFood;
    }

    public void setStoreFood(String storeFood) {
        this.storeFood = storeFood;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImgFood() {
        return imgFood;
    }

    public void setImgFood(byte[] imgFood) {
        this.imgFood = imgFood;
    }
}
