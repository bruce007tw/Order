package com.bruce007tw.order.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruce007tw.order.models.Foods;

import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodRecyclerAdapter extends FirestoreAdapter<FoodRecyclerAdapter.FoodHolder> {

    private static final String TAG = "FoodRecyclerAdapter";

    public interface onFoodSelectedListener {
        void onFoodSelected(DocumentSnapshot firebaseFood);
    }

    private onFoodSelectedListener mListener;

    public FoodRecyclerAdapter(Query mQuery, onFoodSelectedListener mListener) {
        super(mQuery);
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        final DocumentSnapshot documentSnapshot = mDocumentSnapshot.get(position);
        Foods foods = documentSnapshot.toObject(Foods.class);

        Log.d(TAG, "呼叫 onBindViewHolder");

        Glide.with(holder.FoodImage.getContext())
                .load(foods.getFoodPic())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate())
                .into(holder.FoodImage);

        holder.FoodName.setText(foods.getFoodName());
        holder.FoodPrice.setText(String.valueOf("NT. " + foods.getFoodPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onFoodSelected(documentSnapshot);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDocumentSnapshot.size();
    }

    public class FoodHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.menuFoodImage)
        ImageView FoodImage;

        @BindView(R2.id.menuFoodName)
        TextView FoodName;

        @BindView(R2.id.menuFoodPrice)
        TextView FoodPrice;

        public FoodHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}