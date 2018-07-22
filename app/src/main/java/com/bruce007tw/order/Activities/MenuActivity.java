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

import com.bruce007tw.order.Adapters.FoodRecyclerAdapter;
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
    //private FirestoreRecyclerAdapter mAdapter;
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
        //init();
        //getFoodList();
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

        mAdapter = new FoodRecyclerAdapter(mQuery, this) {
        };

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

//    private void init() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//        menuRecyclerView.setLayoutManager(linearLayoutManager);
//        mFirestore = FirebaseFirestore.getInstance();
//    }
//
//    private void getFoodList() {
//        Query query = mFirestore.collection("FoodMenu");
//
//        FirestoreRecyclerOptions<Foods> foods = new FirestoreRecyclerOptions.Builder<Foods>()
//                .setQuery(query, Foods.class)
//                .build();
//
//        mAdapter = new FirestoreRecyclerAdapter<Foods, FoodHolder>(foods) {
//            @Override
//            public void onBindViewHolder(@NonNull FoodHolder holder, int position, @NonNull Foods model) {
//                Log.d(TAG, "呼叫onBindViewHolder");
//
//                Glide.with(getApplicationContext())
//                        .load(model.getFoodPic())
//                        .into(holder.menuFoodPic);
//
//                holder.menuFoodName.setText(model.getFoodName());
//                holder.menuFoodPrice.setText(model.getFoodPrice());
//
//                holder.menuCardView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.d(TAG, "點擊：" + model.getFoodName());
//                        DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
//                        Intent foodDetail = new Intent(MenuActivity.this, FoodDetail.class);
//                        foodDetail.putExtra("foodID", snapshot.getId());
//                        startActivity(foodDetail);
//                    }
//                });
//            }
//
//            @NonNull
//            @Override
//            public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
//                return new FoodHolder(view);
//            }
//
//            @Override
//            public void onError(@NonNull FirebaseFirestoreException e) {
//                super.onError(e);
//                Log.e("錯誤：", e.getMessage());
//            }
//        };
//        mAdapter.notifyDataSetChanged();
//        menuRecyclerView.setAdapter(mAdapter);
//    }
//
//    public class FoodHolder extends RecyclerView.ViewHolder {
//        @BindView(R2.id.menuCardView) CardView menuCardView;
//        @BindView(R2.id.menuCardLayout) LinearLayout menuCardLayout;
//        @BindView(R2.id.menufoodImage) ImageView menuFoodPic;
//        @BindView(R2.id.menuFoodName) TextView menuFoodName;
//        @BindView(R2.id.menuFoodPrice) TextView menuFoodPrice;
//
//        public FoodHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        mAdapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        mAdapter.stopListening();
//    }

    private void StepView() {
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
