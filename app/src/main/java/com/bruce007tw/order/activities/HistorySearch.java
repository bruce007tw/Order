package com.bruce007tw.order.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bruce007tw.order.R;
import com.bruce007tw.order.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistorySearch extends AppCompatActivity {

    private String name, phone;

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
        ButterKnife.bind(this);
        Search();
        BackToMain();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(HistorySearch.this, MainActivity.class));
        finish();
    }

    public void Search() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = searchName.getText().toString();
                phone = searchPhone.getText().toString();
                if (name.matches("") || phone.matches("")) {
                    Toast.makeText(HistorySearch.this, "請輸入完整資訊以便找尋紀錄", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(HistorySearch.this, HistoryList.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    bundle.putString("phone", phone);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void BackToMain() {
        btnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistorySearch.this, MainActivity.class));
                finish();
            }
        });
    }
}
