package com.bruce007tw.order.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bruce007tw.order.Activities.FoodDetail;
import com.bruce007tw.order.Activities.MenuActivity;
import com.bruce007tw.order.DataFields.FoodMenu;
import com.bruce007tw.order.GlideApp;
import com.bruce007tw.order.R;

import java.util.List;

public class MenuFirestoreRecyclerAdapter extends RecyclerView.Adapter<MenuFirestoreRecyclerAdapter.ViewHolderMenu> {

    private static final String TAG = "MenuFirestoreAdapter";
    private Context mContext;
    private List<FoodMenu> FoodList;

    public MenuFirestoreRecyclerAdapter(Context mContext, List<FoodMenu> FoodList) {
        this.mContext = mContext;
        this.FoodList = FoodList;
    }

    @NonNull
    @Override
    public ViewHolderMenu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_view, parent, false);
        return new ViewHolderMenu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMenu holder, final int position) {
        Log.d(TAG, "呼叫onBindViewHolder");

        holder.menuCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "點擊：" + FoodList.get(position).getFoodName());
                Intent foodDetail = new Intent(mContext, FoodDetail.class);
                //foodDetail.putExtra("ID",FoodList.get(position).getFoodName());

            }
        });

        holder.menuFoodName.setText(FoodList.get(position).getFoodName());
        holder.menuFoodPrice.setText(FoodList.get(position).getFoodPrice());

        GlideApp.with(mContext)
                //.asBitmap()
                .load(FoodList.get(position).getFoodPic())
                .into(holder.menuFoodPic);
    }

    @Override
    public int getItemCount() {
        return FoodList.size();
    }

    public class ViewHolderMenu extends RecyclerView.ViewHolder {

        public CardView menuCardView;
        public LinearLayout menuCardLayout;
        public ImageView menuFoodPic;
        public TextView menuFoodName, menuFoodPrice;

        public ViewHolderMenu(View itemView) {
            super(itemView);
            menuCardView = itemView.findViewById(R.id.menuCardView);
            menuCardLayout = itemView.findViewById(R.id.menuCardLayout);
            menuFoodPic = itemView.findViewById(R.id.menuFoodPic);
            menuFoodName = itemView.findViewById(R.id.menuFoodName);
            menuFoodPrice = itemView.findViewById(R.id.menuFoodPrice);
        }
    }
}
