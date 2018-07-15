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

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.ViewHolderMenu>{
    private static final String TAG = "MenuRecyclerViewAdapter";

    private Context mContext;
    private ArrayList<String> mfoodName = new ArrayList<>();
    private ArrayList<String> mfoodPic = new ArrayList<>();

    public MenuRecyclerViewAdapter(Context mContext, ArrayList<String> mfoodName, ArrayList<String> mfoodPic) {
        this.mContext = mContext;
        this.mfoodName = mfoodName;
        this.mfoodPic = mfoodPic;
    }

    @NonNull
    @Override
    public ViewHolderMenu onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_view, viewGroup, false);
        ViewHolderMenu holder = new ViewHolderMenu(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMenu holder, final int position) {
        Log.i(TAG, "呼叫onBindViewHolder");

        GlideApp.with(mContext)
                .asBitmap()
                .load(mfoodPic.get(position))
                .into(holder.imgMenu);

        holder.txtMenu.setText(mfoodName.get(position));

        holder.cardViewMenu.setOnClickListener(new View.OnClickListener() {
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

    public class ViewHolderMenu extends RecyclerView.ViewHolder {
        CardView cardViewMenu;
        LinearLayout cardLayoutMenu;
        ImageView imgMenu;
        TextView txtMenu;

        public ViewHolderMenu(View setView) {
            super(setView);
            cardLayoutMenu = setView.findViewById(R.id.cardLayoutMenu);
            cardViewMenu = setView.findViewById(R.id.cardViewMenu);
            imgMenu = setView.findViewById(R.id.imgMenu);
            txtMenu = setView.findViewById(R.id.txtMenu);
        }
    }
}
