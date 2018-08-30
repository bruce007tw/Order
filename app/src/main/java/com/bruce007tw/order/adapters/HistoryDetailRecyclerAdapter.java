package com.bruce007tw.order.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;
import com.bruce007tw.order.models.Details;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryDetailRecyclerAdapter extends FirestoreAdapter<HistoryDetailRecyclerAdapter.DetailHolder> {

    private static final String TAG = "HistoryDetailAdapter";

    public HistoryDetailRecyclerAdapter(Query mQuery) {
        super(mQuery);
    }

    @NonNull
    @Override
    public DetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_detail_item, parent, false);
        return new DetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailHolder holder, int position) {
        DocumentSnapshot documentSnapshot = mDocumentSnapshot.get(position);
        Details details = documentSnapshot.toObject(Details.class);

        Log.d(TAG, "呼叫 onBindViewHolder");

        holder.FoodName.setText(details.getName());
        holder.FoodQuantity.setText("X" + String.valueOf(details.getQuantity()));
        holder.FoodSubtotal.setText(String.valueOf(details.getSubtotal()) + " (小計)");
    }

    @Override
    public int getItemCount() {
        return mDocumentSnapshot.size();
    }

    public class DetailHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.historyFoodName)
        TextView FoodName;

        @BindView(R2.id.historyFoodQuantity)
        TextView FoodQuantity;

        @BindView(R2.id.historyFoodSubtotal)
        TextView FoodSubtotal;

        public DetailHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
