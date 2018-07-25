package com.bruce007tw.order.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.bruce007tw.order.R;

public class MainActivity extends AppCompatActivity {

//    private static final int RC_SIGN_IN = 9001;

    private BottomNavigationView bottom_bar;

//    private FirebaseFirestore mFirestore;
//    private Query mQuery;
//    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        bottomBar();

//        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
//        FirebaseFirestore.setLoggingEnabled(true);
//        mFirestore = FirebaseFirestore.getInstance();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if (shouldStartSignIn()) {
//            startSignIn();
//            return;
//        }
//    }
//
//    private boolean shouldStartSignIn() {
//        return (!mViewModel.getIsSigningIn() && FirebaseAuth.getInstance().getCurrentUser() == null);
//    }
//
//    private void startSignIn() {
//        // Sign in with FirebaseUI
//        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
//                .setAvailableProviders(Collections.singletonList(
//                        new AuthUI.IdpConfig.EmailBuilder().build()))
//                .setIsSmartLockEnabled(false)
//                .build();
//        startActivityForResult(intent, RC_SIGN_IN);
//        mViewModel.setIsSigningIn(true);
//    }

    private void bottomBar() {
        bottom_bar = findViewById(R.id.bottom_bar);
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_order :
                        startActivity(new Intent(MainActivity.this, FillActivity.class));
                        break;
                    case R.id.action_history :
                        startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }
}
