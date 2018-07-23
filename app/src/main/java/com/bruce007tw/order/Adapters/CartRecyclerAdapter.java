package com.bruce007tw.order.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce007tw.order.Model.Foods;
import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;
import com.bruce007tw.order.Room.OrderEntity;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartHolder> {

    private static final String TAG = "CartRecyclerAdapter";

    public interface onCartSelectedListener {
        void onCartSelected(DocumentSnapshot firebaseFood);
    }

    private onCartSelectedListener mListener;

    private List<OrderEntity> orderEntityList = new ArrayList<>();
    private Context mContext;

    public CartRecyclerAdapter(List<OrderEntity> orderEntityList, Context mContext) {
        this.orderEntityList = orderEntityList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_item, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {

        Glide.with(mContext)
                    .load(orderEntityList.get(position).getFoodPic())
                    .into(holder.FoodImage);

        holder.FoodName.setText(orderEntityList.get(position).getFoodName());
    }

    @Override
    public int getItemCount() {
        return orderEntityList.size();
    }



//    public CartRecyclerAdapter(Query mQuery, onCartSelectedListener mListener) {
//        super(mQuery);
//        this.mListener = mListener;
//    }
//
//    @NonNull
//    @Override
//    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
//        return new CartHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
//        final DocumentSnapshot documentSnapshot = mDocumentSnapshot.get(position);
//        final Foods foods = documentSnapshot.toObject(Foods.class);
//
//        Log.d(TAG, "呼叫 onBindViewHolder");
//
//        holder.bind(getSnapshot(position), mListener);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDocumentSnapshot.size();
//    }

    public class CartHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R2.id.cartFoodImage)
        ImageView FoodImage;

        @BindView(R2.id.cartFoodName)
        TextView FoodName;

        @BindView(R2.id.cartFoodSpinner)
        Spinner FoodSpinner;

        @BindView(R2.id.cartFoodRemove)
        ImageView FoodRemove;

//        public void setFoodName(TextView foodName) {
//            FoodName = foodName;
//        }

        public CartHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

//        public void bind (final DocumentSnapshot documentSnapshot, final onCartSelectedListener mListener) {
//            Foods foods = documentSnapshot.toObject(Foods.class);
//
//            Glide.with(FoodImage.getContext())
//                    .load(foods.getFoodPic())
//                    .into(FoodImage);
//
//            FoodName.setText(foods.getFoodName());
//            FoodPrice.setText(foods.getFoodPrice());
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mListener != null) {
//                        mListener.onCartSelected(documentSnapshot);
//                    }
//                }
//            });
//
//        }

        @Override
        public void onClick(View v) {

        }
    }
}
