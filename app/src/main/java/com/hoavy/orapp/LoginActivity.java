package com.hoavy.orapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hoavy.orapp.utils.SharedHelper;

public class LoginActivity extends AppCompatActivity {
    SharedHelper sharedHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedHelper = SharedHelper.getInstance(this);
        boolean isLoggedIn = sharedHelper.isLoggedIn();
        if (!isLoggedIn) {
            addLoginFragment();
        } else {
            onLoginCompleted();
        }
    }

    public void onLoginCompleted() {
        startActivity(new Intent(this, BottomNavigationActivity.class));
        finish();
    }

    public void addLoginFragment() {
        LoginFragment fragment = new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_frame, fragment, LoginFragment.class.getSimpleName())
                .addToBackStack(LoginFragment.class.getSimpleName())
                .commitAllowingStateLoss();
    }
}