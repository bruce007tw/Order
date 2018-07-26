package com.bruce007tw.order.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bruce007tw.order.Adapters.HistoryRecyclerAdapter;
import com.bruce007tw.order.Model.Keywords;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistorySearch extends AppCompatActivity {

    private static final String TAG = "HistorySearch";

    private HistoryRecyclerAdapter mAdapter;
    private FirebaseFirestore mFirestore;
    private Query mQuery;
    private List<Keywords> searchResults = new ArrayList<>();

    @BindView(R2.id.searchName)
    EditText searchName;

    @BindView(R2.id.searchPhone)
    EditText searchPhone;

    @BindView(R2.id.btnSearch)
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_search);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        search();

        mFirestore = FirebaseFirestore.getInstance();

        mQuery = mFirestore.collection("Requests");

//        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setTimestampsInSnapshotsEnabled(true)
//                .build();
//        firestore.setFirestoreSettings(settings);
    }

    public void search() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirestore.collection("Requests")
                        .whereEqualTo("name", searchName.getText().toString())
                        .whereEqualTo("phone", searchPhone.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot snapshot : task.getResult()) {
                                        Keywords keywords = snapshot.toObject(Keywords.class);
//                                        Keywords keywords = new Keywords();
//                                        keywords.setName(snapshot.getString("name"));
//                                        keywords.setPhone(snapshot.getString("phone"));
//                                        keywords.setAddress(snapshot.getString("address"));
//                                        keywords.setOrderDate(snapshot.getString("orderDate"));
                                        searchResults.add(keywords);
                                        Log.d(TAG, "搜尋結果：" + searchResults);
                                        //Log.d(TAG, "搜尋結果：" + snapshot.getId() + " => " + snapshot.getData());
                                        startActivity(new Intent(HistorySearch.this, HistoryList.class));
                                    }
                                }
                                else {
                                    Log.d(TAG, "錯誤：" + task.getException());
                                }
                            }
                        });
            }
        });
    }

    public List<Keywords> searchResults() {
        return searchResults;
    }
}
