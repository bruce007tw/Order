package com.bruce007tw.order;

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

import java.util.ArrayList;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolderHistory>{
    private static final String TAG = "HistoryRecyclerViewAdap";

    private Context mContext;
    private ArrayList<String> mHistoryName = new ArrayList<>();
    //private ArrayList<String> mfoodPic = new ArrayList<>();

    public HistoryRecyclerViewAdapter(Context mContext, ArrayList<String> mHistoryName) {
        this.mContext = mContext;
        this.mHistoryName = mHistoryName;
        //this.mfoodPic = mfoodPic;
    }

    @NonNull
    @Override
    public ViewHolderHistory onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_item, viewGroup, false);
        ViewHolderHistory holder = new ViewHolderHistory(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHistory holder, final int position) {
        Log.i(TAG, "呼叫onBindViewHolder");

//        GlideApp.with(mContext)
//                .asBitmap()
//                .load(mfoodPic.get(position))
//                .into(holder.imgMenu);

        holder.txtHistory.setText(mHistoryName.get(position));

        holder.cardViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "點擊: " + mHistoryName.get(position));
                Toast.makeText(mContext, mHistoryName.get(position), Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHistoryName.size();
    }

    public class ViewHolderHistory extends RecyclerView.ViewHolder {
        CardView cardViewHistory;
        LinearLayout cardLayoutHistory;
        //ImageView imgMenu;
        TextView txtHistory;

        public ViewHolderHistory(View setView) {
            super(setView);
            cardLayoutHistory = setView.findViewById(R.id.cardLayoutHistory);
            cardViewHistory = setView.findViewById(R.id.cardViewHistory);
            //imgMenu = setView.findViewById(R.id.imgMenu);
            txtHistory = setView.findViewById(R.id.txtHistory);
        }
    }
}
