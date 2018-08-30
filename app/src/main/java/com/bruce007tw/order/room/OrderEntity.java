package com.bruce007tw.order.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class OrderEntity {

    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name="id")
    private int id;

    @ColumnInfo(name="foodName")
    private String foodName;

    @ColumnInfo(name="foodPic")
    private String foodPic;

    @ColumnInfo(name="foodDetail")
    private String foodDetail;

    @ColumnInfo(name = "foodPrice")
    private int foodPrice;

    @ColumnInfo(name="foodQuantity")
    private int foodQuantity;

    public OrderEntity(int id, String foodName, String foodPic, String foodDetail, int foodPrice, int foodQuantity) {
        this.id = id;
        this.foodName = foodName;
        this.foodPic = foodPic;
        this.foodDetail = foodDetail;
        this.foodPrice = foodPrice;
        this.foodQuantity = foodQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }
}
