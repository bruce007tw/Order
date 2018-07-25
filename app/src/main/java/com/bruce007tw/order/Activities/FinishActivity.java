package com.bruce007tw.order.Activities;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.bruce007tw.order.R;

import java.util.ArrayList;
import java.util.List;

public class FinishActivity extends AppCompatActivity {

    private HorizontalStepView step_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        getSupportActionBar().hide();
        stepView();
    }

    private void stepView() {
        step_view = findViewById(R.id.step_view);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("設定",1);
        StepBean stepBean1 = new StepBean("目錄",1);
        StepBean stepBean2 = new StepBean("餐藍",1);
        StepBean stepBean3 = new StepBean("下單",1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);

        step_view
                .setTextSize(14)

                //總步驟
                .setStepViewTexts(stepsBeanList)

                //設置StepsViewIndicator完成線的顏色
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(FinishActivity.this, android.R.color.white))

                //設置StepsViewIndicator未完成線的顏色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(FinishActivity.this, R.color.uncompleted_text_color))

                //設置StepsView text完成線的顏色
                .setStepViewComplectedTextColor(ContextCompat.getColor(FinishActivity.this, android.R.color.white))

                //設置StepsView text未完成線的顏色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(FinishActivity.this, R.color.uncompleted_text_color))

                //設置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(FinishActivity.this, R.drawable.complted))

                //設置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(FinishActivity.this, R.drawable.default_icon))

                //設置StepsViewIndicator AttentionIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(FinishActivity.this, R.drawable.attention));
    }
}
