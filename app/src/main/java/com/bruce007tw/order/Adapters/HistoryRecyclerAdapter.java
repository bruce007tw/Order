package com.bruce007tw.order.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce007tw.order.Activities.HistorySearch;
import com.bruce007tw.order.Model.Foods;
import com.bruce007tw.order.Model.Keywords;
import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryRecyclerAdapter extends FirestoreAdapter<HistoryRecyclerAdapter.HistoryHolder> {

    private static final String TAG = "HistoryRecyclerViewAdap";

//    private Context mContext;
//    private List<Keywords> searchResults;
//
//    public HistoryRecyclerAdapter(List<Keywords> searchResults, Context mContext) {
//        this.mContext = mContext;
//        this.searchResults = searchResults;
//    }

    public interface onHistorySelectedListener {
        void onHistorySelected(DocumentSnapshot firebaseFood);
    }

    private onHistorySelectedListener mListener;

    public HistoryRecyclerAdapter(Query mQuery, onHistorySelectedListener mListener) {
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
        DocumentSnapshot documentSnapshot = mDocumentSnapshot.get(position);
        Keywords keywords = documentSnapshot.toObject(Keywords.class);
        Log.d(TAG, "呼叫 onBindViewHolder");

//        Keywords current = searchResults.get(position);

        holder.orderDate.setText(keywords.getOrderDate());
    }

    @Override
    public int getItemCount() {
        return mDocumentSnapshot.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.historyOrderDate)
        TextView orderDate;

        public HistoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
