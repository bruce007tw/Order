package com.bruce007tw.order;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.bruce007tw.order.Activities.CartActivity;
import com.bruce007tw.order.Activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class FillActivity extends AppCompatActivity {

    private HorizontalStepView step_view;
    private BottomNavigationView bottom_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);
        getSupportActionBar().hide();

        step_view = findViewById(R.id.step_view);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("選擇",1);
        StepBean stepBean1 = new StepBean("購物籃",1);
        StepBean stepBean2 = new StepBean("填寫",0);
        StepBean stepBean3 = new StepBean("送出",-1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);

        step_view
                .setStepViewTexts(stepsBeanList) //總步驟
                .setTextSize(14) //set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(FillActivity.this, android.R.color.white)) //設置StepsViewIndicator完成線的顏色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(FillActivity.this, R.color.uncompleted_text_color)) //設置StepsViewIndicator未完成線的顏色
                .setStepViewComplectedTextColor(ContextCompat.getColor(FillActivity.this, android.R.color.white)) //設置StepsView text完成線的顏色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(FillActivity.this, R.color.uncompleted_text_color)) //設置StepsView text未完成線的顏色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(FillActivity.this, R.drawable.complted)) //設置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(FillActivity.this, R.drawable.default_icon)) //設置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(FillActivity.this, R.drawable.attention)); //設置StepsViewIndicator AttentionIcon

        bottom_bar = findViewById(R.id.bottom_bar);
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_next :
                        startActivity(new Intent(FillActivity.this, MainActivity.class));
                        break;
                    case R.id.action_back :
                        startActivity(new Intent(FillActivity.this, CartActivity.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }
}
