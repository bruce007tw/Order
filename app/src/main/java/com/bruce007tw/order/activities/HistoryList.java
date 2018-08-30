package com.bruce007tw.order.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bruce007tw.order.adapters.HistoryListRecyclerAdapter;
import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryList extends AppCompatActivity implements HistoryListRecyclerAdapter.onHistorySelectedListener{

    private static final String TAG = "HistoryList";

    private HistoryListRecyclerAdapter mAdapter;

    @BindView(R2.id.historyRecyclerView)
    RecyclerView historyRecyclerView;

    @BindView(R2.id.noHistory)
    TextView noHistory;

    @BindView(R2.id.bottom_bar)
    BottomNavigationView bottom_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_list);
        ButterKnife.bind(this);
        Firestore();
        bottomBar();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(HistoryList.this, HistorySearch.class));
        finish();
    }

    private void Firestore() {

        Bundle search = this.getIntent().getExtras();
        final String name = search.getString("name");
        final String phone = search.getString("phone");

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        Query mQuery = mFirestore.collection("Requests")
                .orderBy("orderDate", Query.Direction.DESCENDING)
                .whereEqualTo("name", name)
                .whereEqualTo("phone", phone);

        mQuery.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        boolean isExisting = false;
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            // 檢查是否有匹配結果
                            String fName = snapshot.getString("name");
                            String fPhone = snapshot.getString("phone");
                            if (fName.equals(name)) {
                                if (fPhone.equals(phone)) {
                                    isExisting = true;
                                }
                            }
                        }
                        // 無匹配結果
                        if (!isExisting) {
                            historyRecyclerView.setVisibility(View.INVISIBLE);
                            noHistory.setVisibility(View.VISIBLE);
                        }
                    }
                });

        mAdapter = new HistoryListRecyclerAdapter(mQuery, this){};
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setAdapter(mAdapter);
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

    @Override
    public void onHistorySelected(DocumentSnapshot firebaseFood) {
        Intent intent = new Intent(this, HistoryDetail.class);
        intent.putExtra(HistoryDetail.REQUSET_ID, firebaseFood.getId());
        startActivity(intent);
    }

    private void bottomBar() {
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_redo :
                        startActivity(new Intent(HistoryList.this, HistorySearch.class));
                        break;
                    case R.id.action_main :
                        startActivity(new Intent(HistoryList.this, MainActivity.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }
}
