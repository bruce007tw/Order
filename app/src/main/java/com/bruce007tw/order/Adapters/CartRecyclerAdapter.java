package com.bruce007tw.order.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;
import com.bruce007tw.order.Room.OrderDatabase;
import com.bruce007tw.order.Room.OrderEntity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartHolder> {

    private static final String TAG = "CartRecyclerAdapter";

    private OrderEntity orderEntity;
    private OrderDatabase orderDatabase;
    private List<OrderEntity> orderEntityList;
    private Context mContext;
    private int foodID, quantity;

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
    public void onBindViewHolder(@NonNull final CartHolder holder, final int position) {

        foodID = orderEntityList.get(position).getId();
        quantity = orderEntityList.get(position).getFoodQuantity();

        orderEntity = new OrderEntity(foodID,
                orderEntityList.get(position).getFoodName(),
                orderEntityList.get(position).getFoodPic(),
                orderEntityList.get(position).getFoodPrice(),
                orderEntityList.get(position).getFoodDetail(),
                quantity);

        Glide.with(mContext)
             .load(orderEntityList.get(position).getFoodPic())
             .apply(new RequestOptions().centerCrop())
             .into(holder.FoodImage);

        holder.FoodName.setText(orderEntityList.get(position).getFoodName());

        holder.FoodQuantity.setText(String.valueOf(quantity));

        // 增加數量
        holder.cartPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity < 10){
                    quantity++;
                    holder.FoodQuantity.setText(String.valueOf(quantity));
                    orderDatabase = OrderDatabase.getDatabase(mContext);
                    //orderEntity.setFoodQuantity(quantity);
                    orderDatabase.orderDao().updateOrder(quantity, foodID);
                } else {
                    Toast.makeText(mContext, "單次點餐最多10份", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 減少數量
        holder.cartMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1){
                    quantity--;
                    holder.FoodQuantity.setText(String.valueOf(quantity));
                    orderDatabase = OrderDatabase.getDatabase(mContext);
                    //orderEntity.setFoodQuantity(quantity);
                    orderDatabase.orderDao().updateOrder(quantity, foodID);
                }
            }
        });

        // 刪除單項
        holder.FoodRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = null;
                AlertDialog.Builder builder = null;
                builder = new AlertDialog.Builder(mContext);
                builder.setTitle("警告")
                        .setMessage("確定刪除?")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                orderDatabase = OrderDatabase.getDatabase(mContext);
                                orderDatabase.orderDao().deleteOrder(foodID);
                                Toast.makeText(mContext, "刪除成功", Toast.LENGTH_SHORT).show();

                                // 更新orderEntityList
                                orderEntityList.remove(position);
                                notifyItemChanged(position);
                                notifyItemRangeChanged(position, getItemCount());
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                            }
                        }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderEntityList.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.cartFoodImage)
        ImageView FoodImage;

        @BindView(R2.id.cartFoodName)
        TextView FoodName;

        @BindView(R2.id.cartFoodQuantity)
        TextView FoodQuantity;

        @BindView(R2.id.cartPlus)
        ImageButton cartPlus;

        @BindView(R2.id.cartMinus)
        ImageButton cartMinus;

        @BindView(R2.id.cartRemove)
        ImageButton FoodRemove;


        public CartHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
