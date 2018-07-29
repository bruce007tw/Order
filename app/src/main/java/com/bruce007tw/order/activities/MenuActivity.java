package com.bruce007tw.order.activities;

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

import com.bruce007tw.order.adapters.FoodRecyclerAdapter;
import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity implements FoodRecyclerAdapter.onFoodSelectedListener {

    private static final String TAG = "MenuActivity";

    private HorizontalStepView step_view;
    private BottomNavigationView bottom_bar;
    private FirebaseFirestore mFirestore;
    private FoodRecyclerAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private Query mQuery;

    @BindView(R2.id.menuRecyclerView)
    RecyclerView menuRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        Log.d(TAG, "onCreate: Activity啟動.");
        ButterKnife.bind(this);
        Firestore();
        StepView();
        BottomBar();
    }

    private void Firestore() {
        mFirestore = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        mQuery = mFirestore.collection("FoodMenu");

        mAdapter = new FoodRecyclerAdapter(mQuery, this) {};

        mLinearLayoutManager = new LinearLayoutManager(this);
        menuRecyclerView.setLayoutManager(mLinearLayoutManager);
        menuRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    @Override
    public void onFoodSelected(DocumentSnapshot firebaseFood) {
        Intent intent = new Intent(this, FoodDetail.class);
        intent.putExtra(FoodDetail.KEY_FOOD_ID, firebaseFood.getId());
        startActivity(intent);
    }

    private void StepView() {
        step_view = findViewById(R.id.step_view);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("設定",1);
        StepBean stepBean1 = new StepBean("目錄",0);
        StepBean stepBean2 = new StepBean("餐藍",-1);
        StepBean stepBean3 = new StepBean("下單",-1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);

        step_view
                .setTextSize(14)

                //總步驟
                .setStepViewTexts(stepsBeanList)

                //設置StepsViewIndicator完成線的顏色
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(MenuActivity.this, android.R.color.white))

                //設置StepsViewIndicator未完成線的顏色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(MenuActivity.this, R.color.uncompleted_text_color))

                //設置StepsView text完成線的顏色
                .setStepViewComplectedTextColor(ContextCompat.getColor(MenuActivity.this, android.R.color.white))

                //設置StepsView text未完成線的顏色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(MenuActivity.this, R.color.uncompleted_text_color))

                //設置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(MenuActivity.this, R.drawable.complted))

                //設置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(MenuActivity.this, R.drawable.default_icon))

                //設置StepsViewIndicator AttentionIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(MenuActivity.this, R.drawable.attention));
    }

    private void BottomBar() {
        bottom_bar = findViewById(R.id.bottom_bar);
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_next :
                        startActivity(new Intent(MenuActivity.this, CartActivity.class));
                        break;
                    case R.id.action_back :
                        startActivity(new Intent(MenuActivity.this, FillActivity.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }
}
