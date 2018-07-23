package com.bruce007tw.order.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("select * from " + OrderEntity.TABLE_ORDER)
    List<OrderEntity> getAll();

    @Query("select * from " + OrderEntity.TABLE_ORDER + " where foodQuantity = :foodQuantity")
    OrderEntity getOrderByQuantity(int foodQuantity);

    @Query("DELETE FROM " + OrderEntity.TABLE_ORDER)
    void nukeTable();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addOrder(OrderEntity orderEntity);

    @Delete()
    void deleteOrder(OrderEntity orderEntity);
}
