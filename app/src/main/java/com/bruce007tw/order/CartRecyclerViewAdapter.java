package com.bruce007tw.order;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolderCart>{
    private static final String TAG = "CartRecyclerViewAdapter";

    private Context mContext;
    private ArrayList<String> mfoodName = new ArrayList<>();
    private ArrayList<String> mfoodPic = new ArrayList<>();

    public CartRecyclerViewAdapter(Context mContext, ArrayList<String> mfoodName, ArrayList<String> mfoodPic) {
        this.mContext = mContext;
        this.mfoodName = mfoodName;
        this.mfoodPic = mfoodPic;
    }

    @NonNull
    @Override
    public ViewHolderCart onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_view, viewGroup, false);
        ViewHolderCart holder = new ViewHolderCart(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCart holder, final int position) {
        Log.i(TAG, "呼叫onBindViewHolder");

        GlideApp.with(mContext)
                .asBitmap()
                .load(mfoodPic.get(position))
                .into(holder.imgCart);

        holder.txtCart.setText(mfoodName.get(position));

        holder.cardViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "點擊: " + mfoodName.get(position));
                Toast.makeText(mContext, mfoodName.get(position), Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mfoodName.size();
    }

    public class ViewHolderCart extends RecyclerView.ViewHolder {
        CardView cardViewCart;
        LinearLayout cardLayoutCart;
        ImageView imgCart;
        TextView txtCart;

        public ViewHolderCart(View setView) {
            super(setView);
            cardLayoutCart = setView.findViewById(R.id.cardLayoutCart);
            cardViewCart = setView.findViewById(R.id.cardViewCart);
            imgCart = setView.findViewById(R.id.imgCart);
            txtCart = setView.findViewById(R.id.txtCart);
        }
    }
}
