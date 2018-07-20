package com.bruce007tw.order.Model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Foods {

    private String foodName, foodImage, foodDetail;
    private int foodPrice;

    public Foods() {}

    public Foods(String foodName, String foodImage, String foodDetail, int foodPrice) {
        this.foodName = foodName;
        this.foodImage = foodImage;
        this.foodPrice = foodPrice;
        this.foodDetail = foodDetail;
    }

    public String getFoodName() { return foodName; }

    public void setFoodName(String foodName) { this.foodName = foodName; }

    public String getfoodImage() { return foodImage; }

    public void setfoodImage(String foodImage) { this.foodImage = foodImage; }

    public String getFoodDetail() { return foodDetail; }

    public void setFoodDetail(String foodDetail) { this.foodDetail = foodDetail; }

    public int getFoodPrice() { return foodPrice; }

    public void setFoodPrice(int foodPrice) { this.foodPrice = foodPrice; }
}
