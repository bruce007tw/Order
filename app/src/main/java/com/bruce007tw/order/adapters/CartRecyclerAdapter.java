package com.bruce007tw.order.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
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
import com.bruce007tw.order.room.OrderDatabase;
import com.bruce007tw.order.room.OrderEntity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartHolder> {

    private static final String TAG = "CartRecyclerAdapter";

    private Context mContext;
    private List<OrderEntity> orderEntityList;
    private OrderDatabase orderDatabase;

    public CartRecyclerAdapter(List<OrderEntity> orderEntityList, OrderDatabase orderDatabase, Context mContext) {
        orderEntityList = new ArrayList<>();
        this.orderEntityList = orderEntityList;
        this.orderDatabase = orderDatabase;
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

        final OrderEntity orderEntity = orderEntityList.get(position);

        holder.FoodName.setText(orderEntity.getFoodName());
        holder.FoodQuantity.setText(String.valueOf(orderEntity.getFoodQuantity()));

        Glide.with(mContext)
             .load(orderEntity.getFoodPic())
             .apply(new RequestOptions().centerCrop())
             .into(holder.FoodImage);

        // 增加數量
        holder.cartPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderEntity.getFoodQuantity() < 10) {
                    orderEntity.setFoodQuantity(orderEntity.getFoodQuantity() + 1);
                    holder.FoodQuantity.setText(String.valueOf(orderEntity.getFoodQuantity()));

                    // 更新資料庫
                    orderDatabase.orderDao().update(orderEntity);
                }
                else {
                    Toast.makeText(mContext, "單次點餐最多10份", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 減少數量
        holder.cartMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderEntity.getFoodQuantity() > 1) {
                    orderEntity.setFoodQuantity(orderEntity.getFoodQuantity() - 1);
                    holder.FoodQuantity.setText(String.valueOf(orderEntity.getFoodQuantity()));

                    // 更新資料庫
                    orderDatabase.orderDao().update(orderEntity);
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
                builder.setTitle("確定刪除?")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                OrderEntity mOrderEntity = new OrderEntity(
                                        orderEntity.getId(),
                                        orderEntity.getFoodName(),
                                        orderEntity.getFoodPic(),
                                        orderEntity.getFoodDetail(),
                                        orderEntity.getFoodPrice(),
                                        orderEntity.getFoodQuantity());

                                mOrderEntity.setId(orderEntityList.get(holder.getAdapterPosition()).getId());
                                new DeleteAsync().execute(mOrderEntity);
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

    public void addItems(List<OrderEntity> orderEntityList) {
        this.orderEntityList = orderEntityList;
        notifyDataSetChanged();
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

    private class DeleteAsync extends AsyncTask<OrderEntity, Void, Void> {

        @Override
        protected Void doInBackground(OrderEntity... orderEntities) {
            orderDatabase.orderDao().deleteOrder(orderEntities[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(mContext, "刪除成功", Toast.LENGTH_SHORT).show();
        }
    }
}
