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
import com.google.api.LogDescriptor;
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
    private ArrayList<Keywords> searchResults = new ArrayList<>();

    @BindView(R2.id.searchName)
    EditText searchName;

    @BindView(R2.id.searchPhone)
    EditText searchPhone;

    @BindView(R2.id.btnSearch)
    Button btnSearch;

    @BindView(R2.id.btnToMain)
    Button btnToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_search);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        Search();
        BackToMain();

//        mFirestore = FirebaseFirestore.getInstance();
//
//        mQuery = mFirestore.collection("Requests");

//        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setTimestampsInSnapshotsEnabled(true)
//                .build();
//        firestore.setFirestoreSettings(settings);
    }

    public void Search() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistorySearch.this, HistoryList.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", searchName.getText().toString());
                bundle.putString("phone", searchPhone.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void BackToMain() {
        btnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistorySearch.this, MainActivity.class));
            }
        });
    }

//    public void search() {
//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mFirestore.collection("Requests")
//                        .whereEqualTo("name", searchName.getText().toString())
//                        .whereEqualTo("phone", searchPhone.getText().toString())
//                        .get()
////                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
////                            @Override
////                            public void onSuccess(QuerySnapshot snapshots) {
////                                if (snapshots.isEmpty()) {
////                                    Log.d(TAG, "搜尋結果：LIST EMPTY");
////                                    return;
////                                }
////                                else {
////                                    List<Keywords> keywords = snapshots.toObjects(Keywords.class);
////                                    searchResults.addAll(keywords);
////                                    Log.d(TAG, "搜尋結果：" + searchResults);
////                                }
////                            }
////                        });
//
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (DocumentSnapshot snapshot : task.getResult()) {
//                                        String ID = snapshot.getId();
//                                        Log.d(TAG, "onComplete: " + ID);
////                                        Keywords keywords = snapshot.toObject(Keywords.class);
////                                        Keywords keywords = new Keywords();
////                                        Log.d(TAG, "搜尋結果：" + keywords);
////                                        keywords.setName(snapshot.getString("name"));
////                                        keywords.setPhone(snapshot.getString("phone"));
////                                        keywords.setAddress(snapshot.getString("address"));
////                                        keywords.setOrderDate(snapshot.getString("orderDate"));
////                                        searchResults.add(keywords);
//                                        //Log.d(TAG, "搜尋結果：" + searchResults);
////                                        Log.d(TAG, "搜尋結果：" + snapshot.getId() + " => " + snapshot.getData());
//                                        //startActivity(new Intent(HistorySearch.this, HistoryList.class));
//                                    }
//                                }
//                                else {
//                                    Log.d(TAG, "錯誤：" + task.getException());
//                                }
//                            }
//                        });
//            }
//        });
//    }
//
//    public List<Keywords> searchResults() {
//        return ID;
//    }
}
