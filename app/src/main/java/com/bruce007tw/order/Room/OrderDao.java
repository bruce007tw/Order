package com.bruce007tw.order.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM OrderEntity")
    List<OrderEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addOrder(OrderEntity orderEntity);

    @Query("UPDATE OrderEntity SET foodQuantity=:foodQuantity WHERE id=:id")
    void updateOrder(int foodQuantity, int id);

    @Query("DELETE FROM OrderEntity WHERE id=:id")
    void deleteOrder(int id);

    @Query("DELETE FROM OrderEntity")
    void nukeOrder();
}
