package com.example.foodie.app.core;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.foodie.R;
import com.example.foodie.app.features.home.HomeActivity;
import com.example.foodie.app.features.onBoard.OnBoardActivity;

public class SplashScreenActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, OnBoardActivity.class);
                startActivity(intent);
                finish();
            }
        },1800);

    }
}