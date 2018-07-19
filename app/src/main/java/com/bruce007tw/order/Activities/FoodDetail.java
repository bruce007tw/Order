package com.bruce007tw.order.Activities;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDetail extends AppCompatActivity {
    private static final String TAG = "FoodDetail";

    private FirebaseFirestore firestore;
    private CollectionReference collection;
    private String foodID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail);
        getSupportActionBar().hide();
        init();
        if (getIntent() != null) {
            foodID = getIntent().getStringExtra("foodID");
        }
        if (!foodID.isEmpty()) {
            getFoodDetail(foodID);
        }
    }

    private void init() {
        firestore = FirebaseFirestore.getInstance();
    }

    private void getFoodDetail(String foodID) {
        Query query = firestore.collection("FoodMenu");
        collection.getId().
    }

    public class DetailHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.detailFoodPic) ImageView detailFoodPic;
        @BindView(R2.id.detailCardView) CardView detailCardView;
        @BindView(R2.id.detailConstraintLayout) ConstraintLayout detailConstraintLayout;
        @BindView(R2.id.detailFoodName) TextView detailFoodName;
        @BindView(R2.id.detailFoodPrice) TextView detailFoodPrice;
        @BindView(R2.id.detailDetailTitle) TextView detailDetailTitle;
        @BindView(R2.id.detailFoodDetail) TextView detailFoodDetail;
        @BindView(R2.id.detailNumberBtn) ElegantNumberButton detailNumberBtn;
        @BindView(R2.id.detailAddBtn) Button detailAddBtn;

        public DetailHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
