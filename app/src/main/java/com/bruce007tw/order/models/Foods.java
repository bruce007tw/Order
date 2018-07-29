package com.bruce007tw.order.models;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Foods {

    private String foodName, foodPic, foodPrice, foodDetail;

    public Foods() {}

    public Foods(String foodName, String foodPic, String foodPrice, String foodDetail) {
        this.foodName = foodName;
        this.foodPic = foodPic;
        this.foodPrice = foodPrice;
        this.foodDetail = foodDetail;
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

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodDetail() {
        return foodDetail;
    }

    public void setFoodDetail(String foodDetail) {
        this.foodDetail = foodDetail;
    }
}
