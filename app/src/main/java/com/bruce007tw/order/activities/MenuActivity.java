package com.bruce007tw.order.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;

import com.bruce007tw.order.adapters.FoodRecyclerAdapter;
import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bruce007tw.order.activities.FillActivity.PREFS_NAME;

public class MenuActivity extends AppCompatActivity implements FoodRecyclerAdapter.onFoodSelectedListener {

    private static final String TAG = "MenuActivity";

    private FirebaseFirestore mFirestore;
    private FoodRecyclerAdapter mAdapter;

    @BindView(R2.id.menuRecyclerView)
    RecyclerView menuRecyclerView;

    @BindView(R2.id.step_view)
    HorizontalStepView step_view;

    @BindView(R2.id.bottom_bar)
    BottomNavigationView bottom_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Log.d(TAG, "onCreate: Activity啟動.");
        ButterKnife.bind(this);
        Firestore();
        FoodMenu();
        StepView();
        BottomBar();
    }

    private void Firestore() {
        mFirestore = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);
    }

    private void FoodMenu() {
        Query mQuery = mFirestore.collection("FoodMenu");
        mAdapter = new FoodRecyclerAdapter(mQuery, this) {};
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_next :
                        startActivity(new Intent(MenuActivity.this, CartActivity.class));
                        finish();
                        break;
                    case R.id.action_back :
                        new AlertDialog.Builder(MenuActivity.this)
                                .setTitle("重新填寫資料?")
                                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {

                                        // 刪除空訂單
                                        bundle();

                                        startActivity(new Intent(MenuActivity.this, FillActivity.class));
                                        finish();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                    }
                                }).show();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return customeBackKey();
        }
        return super.onKeyUp(keyCode, event);
    }

    private boolean customeBackKey() {
        new AlertDialog.Builder(MenuActivity.this)
                .setTitle("重新填寫資料?")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        // 刪除空訂單
                        bundle();

                        startActivity(new Intent(MenuActivity.this, FillActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    }
                }).show();
        return true;
    }

    private void bundle() {

        Firestore();

        // 取得先前生成Document ID
        String fillID = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString("referenceID", "");

        mFirestore.collection("Requests").document(fillID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "空訂單刪除成功");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "空訂單刪除失敗");
                    }
                });
    }
}
