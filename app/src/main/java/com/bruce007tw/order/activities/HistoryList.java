package com.bruce007tw.order.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bruce007tw.order.adapters.HistoryRecyclerAdapter;
import com.bruce007tw.order.models.Keywords;
import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryList extends AppCompatActivity implements HistoryRecyclerAdapter.onHistorySelectedListener{

    private static final String TAG = "HistoryList";
    private static final String REQUEST_ID = "request_id";

    private BottomNavigationView bottom_bar;
    private FirebaseFirestore mFirestore;
    private HistoryRecyclerAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private Query mQuery;
    private List<Keywords> searchResults = new ArrayList<>();

    @BindView(R2.id.historyRecyclerView)
    RecyclerView historyRecyclerView;

    @BindView(R2.id.noHistory)
    TextView noHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_list);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        Firestore();
        bottomBar();
    }

    private void Firestore() {

        Bundle search = this.getIntent().getExtras();
        final String name = search.getString("name");
        final String phone = search.getString("phone");

        mFirestore = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        mQuery = mFirestore.collection("Requests")
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
                                    Keywords keywords = snapshot.toObject(Keywords.class);
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

        mAdapter = new HistoryRecyclerAdapter(mQuery, this){};
        mLinearLayoutManager = new LinearLayoutManager(this);
        historyRecyclerView.setLayoutManager(mLinearLayoutManager);
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
//        Intent intent = new Intent(this, HistoryDetail.class);
//        intent.putExtra(HistoryDetail.REQUSET_ID, firebaseFood.getId());
//        startActivity(intent);
    }

    private void bottomBar() {
        bottom_bar = findViewById(R.id.bottom_bar);
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
