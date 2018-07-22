package com.bruce007tw.order.Activities;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruce007tw.order.Model.Foods;
import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;

import com.bumptech.glide.Glide;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDetail extends AppCompatActivity implements EventListener<DocumentSnapshot>{

    private static final String TAG = "FoodDetail";

    public static final String KEY_FOOD_ID = "key_food_id";

    @BindView(R2.id.detailFoodPic)
    ImageView detailFoodPic;

    @BindView(R2.id.detailCardView)
    CardView detailCardView;

    @BindView(R2.id.detailConstraintLayout)
    ConstraintLayout detailConstraintLayout;

    @BindView(R2.id.detailFoodName)
    TextView detailFoodName;

    @BindView(R2.id.detailFoodPrice)
    TextView detailFoodPrice;

    @BindView(R2.id.detailDetailTitle)
    TextView detailDetailTitle;

    @BindView(R2.id.detailFoodDetail)
    TextView detailFoodDetail;

    @BindView(R2.id.detailNumberBtn)
    ElegantNumberButton detailNumberBtn;

    @BindView(R2.id.detailAddBtn)
    Button addBtn;

    private FirebaseFirestore mFirestore;
    private DocumentReference mReference;
    private ListenerRegistration mRegistration;
    private Query mQuery;

    private Boolean isCartEmpty, isItemAlreadyInCart = false;
    int indexOfAlreadyPresentItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        //init();

        String foodID = getIntent().getExtras().getString(KEY_FOOD_ID);
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
        Foods foods = documentSnapshot.toObject(Foods.class);

        Glide.with(this)
                .load(foods.getFoodPic())
                .into(detailFoodPic);
        Log.d(TAG, "圖片載入：" + foods.getFoodPic());

        detailFoodName.setText(foods.getFoodName());
        detailFoodPrice.setText(foods.getFoodPrice());
        detailFoodDetail.setText(foods.getFoodDetail());
    }

    private void AddToCart (final DocumentSnapshot documentSnapshot) {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCartEmpty){

                    int tempIndex = 0;

                    //for (DataSnapshot snapshot : dataSnapshot.child("cartItems").getChildren()){

                    String foodID = getIntent().getExtras().getString(KEY_FOOD_ID);

                        if (foodID == documentSnapshot.getId()){
                            isItemAlreadyInCart = true;
                            indexOfAlreadyPresentItem = tempIndex;
                        }

                    LoadFoodDetail(documentSnapshot);

                        tempIndex++;
                }
                if(isCartEmpty){
                    cartItems = new ArrayList<>();
                    Map<String, Object> cartState = new HashMap<>();
                    cartState.put("isCartEmpty", Boolean.FALSE);
                    myRef.updateChildren(cartState);
                }

                if (isItemAlreadyInCart){
                    cartItems.get(indexOfAlreadyPresentItem)
                            .setQuantity(cartItems.get(indexOfAlreadyPresentItem).getQuantity() + quantity);
                }
                else {
                    item.setQuantity(quantity);
                    cartItems.add(item);
                }

                Map<String, Object> cartItemsMap = new HashMap<>();
                cartItemsMap.put("cartItems", cartItems);

                myRef.updateChildren(cartItemsMap);
            };
        });
    }
}
