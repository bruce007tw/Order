package com.bruce007tw.order.activities;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottom_bar;

    @BindView(R2.id.btnToOrder)
    Button btnToOrder;

    @BindView(R2.id.btnToHistory)
    Button btnToHistory;

    @BindView(R2.id.btnToMap)
    Button btnToMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        order();
        history();
        map();
    }

    private void order() {
        btnToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FillActivity.class));
                finish();
            }
        });
    }

    private void history() {
        btnToHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HistorySearch.class));
                finish();
            }
        });
    }

    private void map() {
        btnToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });
    }
}
