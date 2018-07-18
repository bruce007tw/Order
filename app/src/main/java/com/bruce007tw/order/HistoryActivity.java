package com.bruce007tw.order;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.bruce007tw.order.Activities.MainActivity;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private static final String TAG = "HistoryActivity";

    private BottomNavigationView bottom_bar;
    private ArrayList<String> mHistoryName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().hide();
        bottomBar();
        recyclerTest();
    }

    private void bottomBar() {
        bottom_bar = findViewById(R.id.bottom_bar);
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_main :
                        startActivity(new Intent(HistoryActivity.this, MainActivity.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }

    private void recyclerTest() {
        mHistoryName.add("訂餐紀錄A");
        mHistoryName.add("訂餐紀錄B");
        mHistoryName.add("訂餐紀錄C");
        mHistoryName.add("訂餐紀錄D");
        mHistoryName.add("訂餐紀錄E");
        mHistoryName.add("訂餐紀錄F");
        mHistoryName.add("訂餐紀錄G");
        mHistoryName.add("訂餐紀錄H");
        mHistoryName.add("訂餐紀錄I");
        mHistoryName.add("訂餐紀錄J");

        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: RecyclerView啟動");
        RecyclerView recyclerView = findViewById(R.id.historyRecyclerView);
        HistoryRecyclerViewAdapter adapter = new HistoryRecyclerViewAdapter(this, mHistoryName);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
