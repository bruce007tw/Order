package com.bruce007tw.order.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.bruce007tw.order.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottom_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        bottomBar();
    }

    private void bottomBar() {
        bottom_bar = findViewById(R.id.bottom_bar);
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_order :
                        startActivity(new Intent(MainActivity.this, FillActivity.class));
                        break;
                    case R.id.action_history :
                        startActivity(new Intent(MainActivity.this, HistorySearch.class));
                        break;
                    case  R.id.action_exit :
                        AlertDialog dialog = null;
                        AlertDialog.Builder builder = null;
                        builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("警告")
                                .setMessage("確定離開?")
                                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        System.exit(0);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                    }
                                }).show();
                }
                return true;
            }
        });
    }
}
