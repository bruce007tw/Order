package com.bruce007tw.order.Activities;

import android.app.AlertDialog;
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
import android.view.MenuItem;
import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.bruce007tw.order.Adapters.MenuFirestoreRecyclerAdapter;
import com.bruce007tw.order.CartActivity;
import com.bruce007tw.order.DataFields.FoodMenu;
import com.bruce007tw.order.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "MenuActivity";
    private static final String FIRE = "Firestore";

    private HorizontalStepView step_view;
    private BottomNavigationView bottom_bar;
    private FirebaseFirestore firestore;
    private List<FoodMenu> FoodList;
    private MenuFirestoreRecyclerAdapter menuFirestoreRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        Log.d(TAG, "onCreate: Activity啟動.");
        firestore();
        stepView();
        bottomBar();
    }

    private void firestore() {
        FoodList = new ArrayList<>();
        menuFirestoreRecyclerAdapter = new MenuFirestoreRecyclerAdapter(this, FoodList);

        RecyclerView menuRecyclerView = findViewById(R.id.menuRecyclerView);
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuRecyclerView.setAdapter(menuFirestoreRecyclerAdapter);

        firestore = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);

        firestore.collection("FoodMenu").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(FIRE, "錯誤：" + e.getMessage());
                }
                for (DocumentChange docChange: queryDocumentSnapshots.getDocumentChanges()) {
                    if (docChange.getType() == DocumentChange.Type.ADDED) {
                        FoodMenu mFoodMenu = docChange.getDocument().toObject(FoodMenu.class);
                        FoodList.add(mFoodMenu);
                        menuFirestoreRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
//        firestore.collection("FoodMenu").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d(FIRE, document.getId() + " => " + document.getData());
//                    }
//                }
//                else {
//                    Log.w(FIRE, "無法取得資料", task.getException());
//                }
//            }
//        });
    }

    private void stepView() {
        step_view = findViewById(R.id.step_view);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("選擇",0);
        StepBean stepBean1 = new StepBean("購物籃",-1);
        StepBean stepBean2 = new StepBean("填寫",-1);
        StepBean stepBean3 = new StepBean("送出",-1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);

        step_view
                .setStepViewTexts(stepsBeanList) //總步驟
                .setTextSize(14) //set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(MenuActivity.this, android.R.color.white)) //設置StepsViewIndicator完成線的顏色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(MenuActivity.this, R.color.uncompleted_text_color)) //設置StepsViewIndicator未完成線的顏色
                .setStepViewComplectedTextColor(ContextCompat.getColor(MenuActivity.this, android.R.color.white)) //設置StepsView text完成線的顏色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(MenuActivity.this, R.color.uncompleted_text_color)) //設置StepsView text未完成線的顏色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(MenuActivity.this, R.drawable.complted)) //設置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(MenuActivity.this, R.drawable.default_icon)) //設置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(MenuActivity.this, R.drawable.attention)); //設置StepsViewIndicator AttentionIcon
    }

    private void bottomBar() {
        bottom_bar = findViewById(R.id.bottom_bar);
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_next :
                        startActivity(new Intent(MenuActivity.this, CartActivity.class));
                        break;
                    case R.id.action_main :
                        AlertDialog dialog = null;
                        AlertDialog.Builder builder = null;
                        builder = new AlertDialog.Builder(MenuActivity.this);
                        builder.setTitle("警告")
                                .setMessage("點餐尚未完成，確定離開?")
                                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        startActivity(new Intent(MenuActivity.this, MainActivity.class));
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
}
