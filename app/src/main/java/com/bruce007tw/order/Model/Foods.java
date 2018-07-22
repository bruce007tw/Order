package com.bruce007tw.order.Model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Foods {

    private String foodName, foodPic, foodDetail, foodPrice;

    public Foods() {}

    public Foods(String foodName, String foodPrice, String foodPic, String foodDetail) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodPic = foodPic;
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

    public String getFoodDetail() {
        return foodDetail;
    }

    public void setFoodDetail(String foodDetail) {
        this.foodDetail = foodDetail;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }
}
