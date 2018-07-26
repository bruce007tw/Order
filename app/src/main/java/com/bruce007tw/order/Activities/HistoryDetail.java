package com.bruce007tw.order.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bruce007tw.order.R;

public class HistoryDetail extends AppCompatActivity {

    private static final String TAG = "HistoryDetail";

    public static final String REQUSET_ID = "request_id";

    private String requestID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_detail);
        getSupportActionBar().hide();

        requestID = getIntent().getExtras().getString(REQUSET_ID);
        Log.d(TAG, "foodID：" + getIntent().getExtras().getString(REQUSET_ID));
    }
}
