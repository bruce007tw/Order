package com.bruce007tw.order.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM OrderEntity")
    LiveData<List<OrderEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addOrder(OrderEntity orderEntity);

    @Update
    void update(OrderEntity orderEntity);

    @Delete
    int deleteOrder(OrderEntity orderEntity);

    @Query("DELETE FROM OrderEntity")
    void nukeOrder();
}
