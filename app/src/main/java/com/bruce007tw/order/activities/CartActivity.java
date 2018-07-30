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
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;

import com.bruce007tw.order.adapters.CartRecyclerAdapter;
import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;

import com.bruce007tw.order.room.OrderDatabase;
import com.bruce007tw.order.room.OrderEntity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bruce007tw.order.activities.FillActivity.PREFS_NAME;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "CartActivity";

    private HorizontalStepView step_view;
    private BottomNavigationView bottom_bar;

    private FirebaseFirestore mFirestore;
    private CollectionReference mReference;
    private CartRecyclerAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private Query mQuery;

    private OrderDatabase orderDatabase;
    private List<OrderEntity> orderEntityList = new ArrayList<>();
    private int subtotal, total;

    @BindView(R2.id.cartRecyclerView)
    RecyclerView cartRecyclerView;

    @BindView(R2.id.cartTotal)
    TextView cartTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Log.d(TAG, "onCreate: Activity啟動.");
        ButterKnife.bind(this);
        Firestore();
        selectedFood();
        totalPrice();
        stepView();
        bottomBar();
    }

    private void Firestore() {
        mFirestore = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        mReference = mFirestore.collection("FoodMenu");

        mQuery = mFirestore.collection("FoodMenu");

        cartRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        cartRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    private void getOrderDatabase() {
        orderDatabase = OrderDatabase.getDatabase(CartActivity.this);
    }

    private void selectedFood() {
        getOrderDatabase();
        orderEntityList = orderDatabase.orderDao().getAll();
        mAdapter = new CartRecyclerAdapter(orderEntityList, this);
        cartRecyclerView.setAdapter(mAdapter);
    }

    private void totalPrice() {
        for (int position=0; position<=orderEntityList.size()-1; position++) {
            String price = orderEntityList.get(position).getFoodPrice();
            int quantity = orderEntityList.get(position).getFoodQuantity();
            int subtotal = (Integer.parseInt(price))*quantity;
            total = total + subtotal;
            Log.d(TAG, "totalPrice: " + total);
        }

        cartTotal.setText(String.valueOf(total));
    }

    private void stepView() {
        step_view = findViewById(R.id.step_view);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("設定",1);
        StepBean stepBean1 = new StepBean("目錄",1);
        StepBean stepBean2 = new StepBean("餐藍",0);
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
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(CartActivity.this, android.R.color.white))

                //設置StepsViewIndicator未完成線的顏色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(CartActivity.this, R.color.uncompleted_text_color))

                //設置StepsView text完成線的顏色
                .setStepViewComplectedTextColor(ContextCompat.getColor(CartActivity.this, android.R.color.white))

                //設置StepsView text未完成線的顏色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(CartActivity.this, R.color.uncompleted_text_color))

                //設置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(CartActivity.this, R.drawable.complted))

                //設置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(CartActivity.this, R.drawable.default_icon))

                //設置StepsViewIndicator AttentionIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(CartActivity.this, R.drawable.attention));
    }

    private void bottomBar() {
        bottom_bar = findViewById(R.id.bottom_bar);
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_send :
                        if (orderEntityList != null) {
                            AlertDialog dialog = null;
                            AlertDialog.Builder builder = null;
                            builder = new AlertDialog.Builder(CartActivity.this);
                            builder.setTitle("確認餐點無誤?")
                                    //.setMessage("確認餐點無誤?")
                                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 取得先前生成Document的ID
                                            String clientID = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                                                    .getString("referenceID", "");

                                            // 跑迴圈逐筆上傳購物資料
                                            for (int position=0; position<=orderEntityList.size()-1; position++) {

                                                // 對應購物資料與Firebase資料欄位
                                                int foodID = orderEntityList.get(position).getId();
                                                String name = orderEntityList.get(position).getFoodName();
                                                String price = orderEntityList.get(position).getFoodPrice();
                                                int quantity = orderEntityList.get(position).getFoodQuantity();
                                                int subtotal = (Integer.parseInt(price))*quantity;

                                                // 對應輸入資料與Firebase資料欄位
                                                Map<String, Object> orderMap = new HashMap<>();
                                                orderMap.put("foodID", foodID);
                                                orderMap.put("name", name);
                                                orderMap.put("price", price);
                                                orderMap.put("quantity", quantity);
                                                orderMap.put("subtotal", subtotal);

                                                // 上傳購物資料至先前生成的Document
                                                mFirestore.collection("Requests").document(clientID).collection("Orders")
                                                        .add(orderMap)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                String error = e.getMessage();
                                                                Toast.makeText(CartActivity.this, "發生錯誤：" + error, Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                            // 清空購物車
                                            orderDatabase.orderDao().nukeOrder();

                                            // 切換到完成頁
                                            startActivity(new Intent(CartActivity.this, FinishActivity.class));
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i) {
                                        }
                                    }).show();
                        }
                        else {
                            // 未選擇餐點警告跳窗
                            AlertDialog dialog = null;
                            AlertDialog.Builder builder = null;
                            builder = new AlertDialog.Builder(CartActivity.this);
                            builder.setTitle("警告")
                                    .setMessage("請先選擇餐點")
                                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i) {
                                        }
                                    }).show();
                        }
                        break;

                    case R.id.action_choose :
                        // 切換回餐點目錄頁
                        startActivity(new Intent(CartActivity.this, MenuActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });
    }
}
