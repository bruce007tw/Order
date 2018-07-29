package com.bruce007tw.order.models;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Keywords {

    private String name, phone, address, orderDate, demandTime;

    public Keywords() {}

    public Keywords(String name, String phone, String address, String orderDate, String demandTime) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.orderDate = orderDate;
        this.demandTime = demandTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDemandTime() {
        return demandTime;
    }

    public void setDemandTime(String demandTime) {
        this.demandTime = demandTime;
    }
}
