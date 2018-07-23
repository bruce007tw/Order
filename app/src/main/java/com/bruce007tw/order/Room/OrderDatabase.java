package com.bruce007tw.order.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {OrderEntity.class}, version = 1, exportSchema = false)
public abstract class OrderDatabase extends RoomDatabase{

    public static final String DBNAME = "Orders";

    private static OrderDatabase Instance;

    public static OrderDatabase getDatabase(Context context) {
        if (Instance == null) {
            Instance = Room
                    .databaseBuilder
                    (context.getApplicationContext(),
                            OrderDatabase.class,
                            OrderDatabase.DBNAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return Instance;
    }

    public static void destoryInstance() {
        Instance = null;
    }

    public abstract OrderDao orderDao();
}
