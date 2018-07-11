package com.bruce007tw.order;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class HistoryActivity extends AppCompatActivity {

    private BottomNavigationView bottom_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().hide();

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
}
