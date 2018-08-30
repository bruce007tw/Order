package com.bruce007tw.order.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bruce007tw.order.models.Keywords;
import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryListRecyclerAdapter extends FirestoreAdapter<HistoryListRecyclerAdapter.HistoryHolder> {

    private static final String TAG = "HistoryRecyclerViewAdap";

    public interface onHistorySelectedListener {
        void onHistorySelected(DocumentSnapshot firebaseFood);
    }

    private onHistorySelectedListener mListener;

    public HistoryListRecyclerAdapter(Query mQuery, onHistorySelectedListener mListener) {
        super(mQuery);
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        final DocumentSnapshot documentSnapshot = mDocumentSnapshot.get(position);
        Keywords keywords = documentSnapshot.toObject(Keywords.class);

        Log.d(TAG, "呼叫 onBindViewHolder");

        holder.orderDate.setText(keywords.getOrderDate());
        holder.demandTime.setText(keywords.getDemandTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onHistorySelected(documentSnapshot);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDocumentSnapshot.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.historyOrderDate)
        TextView orderDate;

        @BindView(R2.id.historyDemandTime)
        TextView demandTime;

        public HistoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
