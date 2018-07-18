package com.bruce007tw.order.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.bruce007tw.order.HistoryActivity;
import com.bruce007tw.order.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottom_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        bottom_bar = findViewById(R.id.bottom_bar);
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_order :
                        startActivity(new Intent(MainActivity.this, MenuActivity.class));
                        break;
                    case R.id.action_history :
                        startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }
}
