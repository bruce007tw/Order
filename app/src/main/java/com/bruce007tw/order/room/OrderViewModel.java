package com.bruce007tw.order.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private final LiveData<List<OrderEntity>> orderList;
    private OrderDatabase orderDatabase;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        orderDatabase = OrderDatabase.getDatabase(this.getApplication());
        orderList = orderDatabase.orderDao().getAll();
    }

    public LiveData<List<OrderEntity>> getOrderList() {
        return orderList;
    }
}
