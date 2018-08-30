package com.bruce007tw.order.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;
import com.bruce007tw.order.adapters.HistoryDetailRecyclerAdapter;
import com.bruce007tw.order.models.Details;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryDetail extends AppCompatActivity {

    private static final String TAG = "HistoryDetail";
    public static final String REQUSET_ID = "request_id";

    private HistoryDetailRecyclerAdapter mAdapter;
    private int total;

    @BindView(R2.id.HistoryDetailRecyclerView)
    RecyclerView HistoryDetailRecyclerView;

    @BindView(R2.id.HistoryDetailTotal)
    TextView HistoryDetailTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_detail);
        ButterKnife.bind(this);
        Firestore();
    }

    private void Firestore() {
        String requestID = getIntent().getExtras().getString(REQUSET_ID);
        Log.d(TAG, "requestID：" + getIntent().getExtras().getString(REQUSET_ID));

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        Query mQuery = mFirestore.collection("Requests").document(requestID).collection("Orders");

        mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        Details details = documentSnapshot.toObject(Details.class);
                        int price = details.getPrice();
                        int quantity = details.getQuantity();
                        total = total + (price*quantity);
                    }
                    Log.d(TAG, "總金額：" + total);
                    HistoryDetailTotal.setText("金額總計：NT." + String.valueOf(total));
                }
                else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        mAdapter = new HistoryDetailRecyclerAdapter(mQuery){};
        HistoryDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        HistoryDetailRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }
}
