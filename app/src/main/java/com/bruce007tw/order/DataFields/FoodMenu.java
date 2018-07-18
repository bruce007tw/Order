package com.bruce007tw.order.DataFields;

public class FoodMenu {

    String foodName, foodPic, foodPrice;

    public FoodMenu() {

    }

    public FoodMenu(String foodName, String foodPic, String foodPrice) {
        this.foodName = foodName;
        this.foodPic = foodPic;
        this.foodPrice = foodPrice;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPic() {
        return foodPic;
    }

    public void setFoodPic(String foodPic) {
        this.foodPic = foodPic;
    }

    public String getFoodPrice() { return foodPrice; }

    public void setFoodPrice(String foodPrice) { this.foodPrice = foodPrice; }
}
