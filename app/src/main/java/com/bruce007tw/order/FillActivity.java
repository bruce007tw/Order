package com.bruce007tw.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;

import com.bruce007tw.order.Activities.CartActivity;
import com.bruce007tw.order.Activities.FinishActivity;

import com.bruce007tw.order.Activities.MainActivity;
import com.bruce007tw.order.Activities.MenuActivity;
import com.bruce007tw.order.Room.OrderDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FillActivity extends AppCompatActivity {

    private static final String TAG = "FillActivity";

    private HorizontalStepView step_view;
    private BottomNavigationView bottom_bar;

    private FirebaseFirestore mFirestore;

    @BindView(R2.id.editName)
    EditText editName;

    @BindView(R2.id.editPhone)
    EditText editPhone;

    @BindView(R2.id.editAddr)
    EditText editAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        stepView();
        bottomBar();

        mFirestore = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);
    }

    private void stepView() {
        step_view = findViewById(R.id.step_view);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("設定",0);
        StepBean stepBean1 = new StepBean("目錄",-1);
        StepBean stepBean2 = new StepBean("餐籃",-1);
        StepBean stepBean3 = new StepBean("送出",-1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);

        step_view
                .setTextSize(15)

                //總步驟
                .setStepViewTexts(stepsBeanList)

                //設置StepsViewIndicator完成線的顏色
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(FillActivity.this, android.R.color.white))

                //設置StepsViewIndicator未完成線的顏色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(FillActivity.this, R.color.uncompleted_text_color))

                //設置StepsView text完成線的顏色
                .setStepViewComplectedTextColor(ContextCompat.getColor(FillActivity.this, android.R.color.white))

                //設置StepsView text未完成線的顏色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(FillActivity.this, R.color.uncompleted_text_color))

                //設置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(FillActivity.this, R.drawable.complted))

                //設置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(FillActivity.this, R.drawable.default_icon))

                //設置StepsViewIndicator AttentionIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(FillActivity.this, R.drawable.attention));
    }

    private void bottomBar() {
        bottom_bar = findViewById(R.id.bottom_bar);
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.action_next :

                        String name = editName.getText().toString();
                        String phone = editPhone.getText().toString();
                        String address = editAddr.getText().toString();
                        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String orderDate = sDateFormat.format(new java.util.Date());

                        Map<String, String> clientMap = new HashMap<>();
                        clientMap.put("name", name);
                        clientMap.put("phone", phone);
                        clientMap.put("address", address);
                        clientMap.put("orderDate", orderDate);

                        //DocumentReference documentReference = mFirestore.collection("Orders").document();

                        mFirestore.collection("Orders")
                                .add(clientMap)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "訂單ID: " + documentReference.getId());

                                        String clientID = documentReference.getId();
                                        Log.d(TAG, "clientID：" + clientID);

                                        startActivity(new Intent(FillActivity.this, MenuActivity.class));
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        String error = e.getMessage();
                                        Toast.makeText(FillActivity.this, "發生錯誤：" + error, Toast.LENGTH_SHORT).show();
                                    }
                                });
                        break;

                    case R.id.action_back :
                        AlertDialog dialog = null;
                        AlertDialog.Builder builder = null;
                        builder = new AlertDialog.Builder(FillActivity.this);
                        builder.setTitle("警告")
                                .setMessage("點餐尚未完成，確定離開?")
                                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {

                                        // 清空購物車
                                        OrderDatabase orderDatabase = OrderDatabase.getDatabase(FillActivity.this);
                                        orderDatabase.orderDao().nukeOrder();

                                        startActivity(new Intent(FillActivity.this, MainActivity.class));
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                    }
                                }).show();
                        break;
                }
                return true;
            }
        });
    }
}
