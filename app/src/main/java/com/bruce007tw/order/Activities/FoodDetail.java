package com.bruce007tw.order.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce007tw.order.Model.Foods;
import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;

import com.bruce007tw.order.Room.OrderDatabase;
import com.bruce007tw.order.Room.OrderEntity;
import com.bumptech.glide.Glide;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDetail extends AppCompatActivity implements EventListener<DocumentSnapshot>{

    private static final String TAG = "FoodDetail";

    public static final String KEY_FOOD_ID = "key_food_id";

    private FirebaseFirestore mFirestore;
    private DocumentReference mReference;
    private ListenerRegistration mRegistration;
    private String foodID;
    private Foods currentFood;
    private int quantity = 1;

    @BindView(R2.id.detailFoodPic)
    ImageView detailFoodPic;

    @BindView(R2.id.detailFoodName)
    TextView detailFoodName;

    @BindView(R2.id.detailFoodPrice)
    TextView detailFoodPrice;

    @BindView(R2.id.detailFoodDetail)
    TextView detailFoodDetail;

    @BindView(R2.id.detailMinus)
    ImageButton detailMinus;

    @BindView(R2.id.detailPlus)
    ImageButton detailPlus;

    @BindView(R2.id.detailQuantity)
    TextView detailQuantity;

    @BindView(R2.id.detailAddBtn)
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        setQuantity();
        AddToCart();

        foodID = getIntent().getExtras().getString(KEY_FOOD_ID);
        Log.d(TAG, "foodID：" + getIntent().getExtras().getString(KEY_FOOD_ID));

        detailQuantity.setText(String.valueOf(quantity));

        mFirestore = FirebaseFirestore.getInstance();
        mReference = mFirestore.collection("FoodMenu").document(foodID);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRegistration = mReference.addSnapshotListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRegistration != null) {
            mRegistration.remove();
            mRegistration = null;
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
        if (e != null) {
            Log.d(TAG, "onEvent錯誤：", e);
            return;
        }
        LoadFoodDetail(documentSnapshot);
    }

    private void LoadFoodDetail(DocumentSnapshot documentSnapshot) {
        currentFood = documentSnapshot.toObject(Foods.class);

        Glide.with(this)
                .load(currentFood.getFoodPic())
                .into(detailFoodPic);
        Log.d(TAG, "圖片：" + currentFood.getFoodPic());

        detailFoodName.setText(currentFood.getFoodName());
        detailFoodPrice.setText(currentFood.getFoodPrice());
        detailFoodDetail.setText(currentFood.getFoodDetail());
    }

    private void setQuantity() {
        detailPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity < 10){
                    quantity++;
                    detailQuantity.setText(String.valueOf(quantity));
                } else {
                    Toast.makeText(getApplicationContext(), "單次點餐最多10份", Toast.LENGTH_SHORT).show();
                }
            }
        });

        detailMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1){
                    quantity--;
                    detailQuantity.setText(String.valueOf(quantity));
                }
            }
        });
    }

    private void AddToCart () {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderEntity orderEntity = new OrderEntity(Integer.parseInt(foodID),
                        currentFood.getFoodName(),
                        currentFood.getFoodPic(),
                        currentFood.getFoodPrice(),
                        currentFood.getFoodDetail(),
                        quantity);
                OrderDatabase.getDatabase(getApplicationContext()).orderDao().addOrder(orderEntity);
                Log.d(TAG, "購物車：" + orderEntity);
                Toast.makeText(getApplicationContext(), "新增成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(FoodDetail.this, MenuActivity.class));
            }
        });
    }
}
