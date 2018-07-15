package com.bruce007tw.order;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "CartActivity";

    private HorizontalStepView step_view;
    private BottomNavigationView bottom_bar;
    private ArrayList<String> mfoodName = new ArrayList<>();
    private ArrayList<String> mfoodPicUrl = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().hide();
        stepView();
        bottomBar();
        recyclerTest();
    }

    private void stepView() {
        step_view = findViewById(R.id.step_view);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("選擇",1);
        StepBean stepBean1 = new StepBean("購物籃",0);
        StepBean stepBean2 = new StepBean("填寫",-1);
        StepBean stepBean3 = new StepBean("送出",-1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);

        step_view
                .setStepViewTexts(stepsBeanList) //總步驟
                .setTextSize(14) //set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(CartActivity.this, android.R.color.white)) //設置StepsViewIndicator完成線的顏色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(CartActivity.this, R.color.uncompleted_text_color)) //設置StepsViewIndicator未完成線的顏色
                .setStepViewComplectedTextColor(ContextCompat.getColor(CartActivity.this, android.R.color.white)) //設置StepsView text完成線的顏色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(CartActivity.this, R.color.uncompleted_text_color)) //設置StepsView text未完成線的顏色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(CartActivity.this, R.drawable.complted)) //設置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(CartActivity.this, R.drawable.default_icon)) //設置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(CartActivity.this, R.drawable.attention)); //設置StepsViewIndicator AttentionIcon
    }

    private void bottomBar() {
        bottom_bar = findViewById(R.id.bottom_bar);
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_next :
                        startActivity(new Intent(CartActivity.this, FillActivity.class));
                        break;
                    case R.id.action_back :
                        startActivity(new Intent(CartActivity.this, MenuActivity.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }

    private void recyclerTest() {
        mfoodPicUrl.add("https://www.mcdonalds.com/content/dam/usa/documents/mcdelivery/mcdelivery_new11.jpg");
        mfoodName.add("套餐A");

        mfoodPicUrl.add("https://www.mcdonalds.com/content/dam/usa/documents/mcdelivery/mcdelivery_new11.jpg");
        mfoodName.add("套餐B");

        mfoodPicUrl.add("https://www.mcdonalds.com/content/dam/usa/documents/mcdelivery/mcdelivery_new11.jpg");
        mfoodName.add("套餐C");

        mfoodPicUrl.add("https://www.mcdonalds.com/content/dam/usa/documents/mcdelivery/mcdelivery_new11.jpg");
        mfoodName.add("套餐D");

        mfoodPicUrl.add("https://www.mcdonalds.com/content/dam/usa/documents/mcdelivery/mcdelivery_new11.jpg");
        mfoodName.add("套餐E");

        mfoodPicUrl.add("https://www.mcdonalds.com/content/dam/usa/documents/mcdelivery/mcdelivery_new11.jpg");
        mfoodName.add("套餐F");

        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: RecyclerView啟動");
        RecyclerView recyclerView = findViewById(R.id.cartRecyclerView);
        CartRecyclerViewAdapter adapter = new CartRecyclerViewAdapter(this, mfoodName, mfoodPicUrl);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
