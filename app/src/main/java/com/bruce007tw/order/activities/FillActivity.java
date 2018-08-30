package com.bruce007tw.order.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;

import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;
import com.bruce007tw.order.room.OrderDatabase;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FillActivity extends AppCompatActivity {

    private static final String TAG = "FillActivity";
    public static final String PREFS_NAME = "MyPrefsFile";

    private FirebaseFirestore mFirestore;
    private String name, phone, address, method, demandTime, orderDate, referenceID;

    @BindView(R2.id.editName)
    EditText editName;

    @BindView(R2.id.editPhone)
    EditText editPhone;

    @BindView(R2.id.editAddr)
    EditText editAddr;

    @BindView(R2.id.methodGroup)
    RadioGroup methodGroup;

    @BindView(R2.id.takeout)
    RadioButton takeout;

    @BindView(R2.id.delivery)
    RadioButton delivery;

    @BindView(R2.id.demandDate)
    SingleDateAndTimePicker demandDate;

    @BindView(R2.id.step_view)
    HorizontalStepView step_view;

    @BindView(R2.id.bottom_bar)
    BottomNavigationView bottom_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);
        ButterKnife.bind(this);
        stepView();
        bottomBar();

        mFirestore = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        // 取餐方式設定(在此才會於Activity啟動同時啟動RadioGroup)
        method();
    }

    private void method() {
        // 預設方式外送
        delivery.setChecked(true);
        method = delivery.getText().toString();

        methodGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == takeout.getId()){
                    method = takeout.getText().toString();
                }
                if (checkedId == delivery.getId()) {
                    method = delivery.getText().toString();
                }
            }
        });
        Log.d(TAG, "取餐方式：" + method);
    }

    private void demandTime() {
        SimpleDateFormat dDateFormat = new SimpleDateFormat("yyyy-MM-dd  kk:mm");
        Date demand = demandDate.getDate();

        // 取得現在時間30分鐘後
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 30);
        Date current = now.getTime();

        if (demand.before(current)) {
            demandTime = "";
            Log.d(TAG, "取餐時間：" + demandTime);
            Toast.makeText(FillActivity.this, "取餐時間請至少選擇30分鐘後", Toast.LENGTH_SHORT).show();
        }
        else {
            demandTime = dDateFormat.format(demand);
            Log.d(TAG, "取餐時間：" + demandTime);
        }
    }

    private void orderDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd  kk:mm");
        orderDate = sDateFormat.format(new java.util.Date());
        Log.d(TAG, "點餐時間：" + orderDate);
    }

    private void uploadToFirebase() {
        // 檢查資料是否皆有輸入
        if (name.matches("") || phone.matches("") || address.matches("")) {
            Toast toast = Toast.makeText(FillActivity.this, "請輸入完整資料", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        else if (name!=null && phone!=null && address!=null && demandTime!="") {
            new AlertDialog.Builder(FillActivity.this)
                    .setTitle("請確認資料是否填寫正確：")
                    .setMessage("\n訂購人：" + name + "\n聯絡電話：" + phone + "\n地址：" + address + "\n取餐方式：" + method + "\n取餐時間：" + demandTime)
                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {

                            // 對應輸入資料與Firebase資料欄位
                            Map<String, Object> clientMap = new HashMap<>();
                            clientMap.put("name", name);
                            clientMap.put("phone", phone);
                            clientMap.put("address", address);
                            clientMap.put("method", method);
                            clientMap.put("demandTime", demandTime);
                            clientMap.put("orderDate", orderDate);

                            // 上傳資料至Firebase/Requests(Collection)中並建立一個新的document
                            mFirestore.collection("Requests")
                                    .add(clientMap)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {

                                            // 取得自動生成的Document ID
                                            referenceID = documentReference.getId();
                                            SharedPreferences setting = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                                            setting.edit().putString("referenceID", referenceID).apply();

                                            // 切換到餐點目錄
                                            startActivity(new Intent(FillActivity.this, MenuActivity.class));
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "Fill錯誤：" + e);
                                            String error = e.getMessage();
                                            Toast.makeText(FillActivity.this, "發生錯誤：" + error, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    })
                    .setNegativeButton("更正", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                        }
                    }).show();
        }
    }

    private void stepView() {
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("設定",0);
        StepBean stepBean1 = new StepBean("目錄",-1);
        StepBean stepBean2 = new StepBean("餐籃",-1);
        StepBean stepBean3 = new StepBean("下單",-1);
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
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.action_next :
                        // 設定輸入資料
                        name = editName.getText().toString();
                        phone = editPhone.getText().toString();
                        address = editAddr.getText().toString();

                        // 取餐時間設定
                        demandTime();

                        // 取得系統時間
                        orderDate();

                        // 執行上傳
                        uploadToFirebase();

                        break;

                    case R.id.action_back :
                        new AlertDialog.Builder(FillActivity.this)
                                .setTitle("警告")
                                .setMessage("點餐尚未完成，確定回到主畫面?")
                                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {

                                        // 清空購物車
                                        OrderDatabase orderDatabase = OrderDatabase.getDatabase(FillActivity.this);
                                        orderDatabase.orderDao().nukeOrder();

                                        // 回到主畫面
                                        startActivity(new Intent(FillActivity.this, MainActivity.class));
                                        finish();
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

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return customeBackKey();
        }
        return super.onKeyUp(keyCode, event);
    }

    private boolean customeBackKey() {
        new AlertDialog.Builder(FillActivity.this)
                .setTitle("警告")
                .setMessage("點餐尚未完成，確定回到主畫面?")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        // 清空購物車
                        OrderDatabase orderDatabase = OrderDatabase.getDatabase(FillActivity.this);
                        orderDatabase.orderDao().nukeOrder();

                        // 回到主畫面
                        startActivity(new Intent(FillActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    }
                }).show();
        return true;
    }
}