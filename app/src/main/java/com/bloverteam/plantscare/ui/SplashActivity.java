package com.bloverteam.plantscare.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bloverteam.plantscare.R;

@SuppressWarnings("deprecation")
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIMED_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        new Handler().postDelayed(() -> {
            Intent toActivityInput = new Intent(SplashActivity.this, LoginActivity.class);
            toActivityInput.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(toActivityInput);
            finish();
        },SPLASH_TIMED_OUT);
    }
}