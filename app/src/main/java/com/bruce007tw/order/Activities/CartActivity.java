package com.bruce007tw.order.Activities;

import android.content.Context;
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
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;

import com.bruce007tw.order.Adapters.CartRecyclerAdapter;
import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;

import com.bruce007tw.order.Room.OrderDatabase;
import com.bruce007tw.order.Room.OrderEntity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bruce007tw.order.Activities.FillActivity.PREFS_NAME;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "CartActivity";

    private HorizontalStepView step_view;
    private BottomNavigationView bottom_bar;

    private FirebaseFirestore mFirestore;
    private CollectionReference mReference;
    private CartRecyclerAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private Query mQuery;
    private List<OrderEntity> orderEntityList = new ArrayList<>();

    @BindView(R2.id.cartRecyclerView)
    RecyclerView cartRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().hide();
        Log.d(TAG, "onCreate: Activity啟動.");
        ButterKnife.bind(this);

        Firestore();
        selectedFood();
        stepView();
        bottomBar();
    }

    private void Firestore() {
        mFirestore = FirebaseFirestore.getInstance();
        mReference = mFirestore.collection("FoodMenu");

//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setTimestampsInSnapshotsEnabled(true)
//                .build();
//        mFirestore.setFirestoreSettings(settings);

        mQuery = mFirestore.collection("FoodMenu");

        cartRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        cartRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    private void selectedFood() {
        OrderDatabase orderDatabase = OrderDatabase.getDatabase(CartActivity.this);
        orderEntityList = orderDatabase.orderDao().getAll();
        mAdapter = new CartRecyclerAdapter(orderEntityList, this);
        cartRecyclerView.setAdapter(mAdapter);
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
                    case R.id.action_next :

                        String clientID = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                                .getString("referenceID", "");

                        for (int position=0; position<=orderEntityList.size()-1; position++) {

                            int foodID = orderEntityList.get(position).getId();
                            String name = orderEntityList.get(position).getFoodName();
                            String Price = orderEntityList.get(position).getFoodPrice();
                            int quantity = orderEntityList.get(position).getFoodQuantity();

                            Map<String, Object> orderMap = new HashMap<>();
                            orderMap.put("foodID", foodID);
                            orderMap.put("name", name);
                            orderMap.put("Price", Price);
                            orderMap.put("quantity", quantity);

                            mFirestore.collection("Orders").document(clientID).collection("Orders")
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
                        OrderDatabase orderDatabase = OrderDatabase.getDatabase(CartActivity.this);
                        orderDatabase.orderDao().nukeOrder();
                        startActivity(new Intent(CartActivity.this, FinishActivity.class));
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
}
