package com.bruce007tw.order.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.bruce007tw.order.Adapters.FoodRecyclerAdapter;
import com.bruce007tw.order.Adapters.HistoryRecyclerAdapter;
import com.bruce007tw.order.Model.Keywords;
import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HistoryList extends AppCompatActivity {

    private static final String TAG = "HistoryList";

    private BottomNavigationView bottom_bar;
    private FirebaseFirestore mFirestore;
    private HistoryRecyclerAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private Query mQuery;
    private List<Keywords> searchResults = new ArrayList<>();
    private HistorySearch historySearch;

    @BindView(R2.id.historyRecyclerView)
    RecyclerView historyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_list);
        getSupportActionBar().hide();
        Firestore();
        bottomBar();
    }

    private void Firestore() {
        mFirestore = FirebaseFirestore.getInstance();

        mQuery = mFirestore.collection("Requests");

        searchResults = historySearch.searchResults();
        Log.d(TAG, "searchResults: " + searchResults);

        mLinearLayoutManager = new LinearLayoutManager(this);
        historyRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new HistoryRecyclerAdapter(searchResults, this);
        historyRecyclerView.setAdapter(mAdapter);
    }

//    @Override
//    public void onHistorySelected(DocumentSnapshot firebaseFood) {
//        Intent intent = new Intent(this, HistoryDetail.class);
//        intent.putExtra(HistoryDetail.REQUSET_ID, firebaseFood.getId());
//        startActivity(intent);
//    }

    private void bottomBar() {
        bottom_bar = findViewById(R.id.bottom_bar);
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
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
