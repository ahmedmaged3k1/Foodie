package com.example.foodie.app.features.onBoard;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.foodie.R;
import com.example.foodie.app.core.SplashScreenActivity;
import com.example.foodie.app.features.home.HomeActivity;
import com.example.foodie.app.features.home.SliderAdapter;
import com.example.foodie.app.features.signIn.SignInActivity;

public class OnBoardActivity extends AppCompatActivity {
    ViewPager onBoardViewPager ;
    SliderAdapter sliderAdapter;
    AppCompatButton onBoardButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        getSupportActionBar().hide();
        setAdapter();
    }
    private void setAdapter(){
        onBoardViewPager=findViewById(R.id.onBoardViewPager);
        sliderAdapter= new SliderAdapter(this);
        onBoardViewPager.setAdapter(sliderAdapter);
        onBoardButton=findViewById(R.id.onBoardButton);
        if(onBoardViewPager.getCurrentItem()==0){
            onBoardButton.setText("Next");
        }
        onBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onBoardViewPager.getCurrentItem()==1){
                    Intent intent = new Intent(OnBoardActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
                onBoardViewPager.setCurrentItem(onBoardViewPager.getCurrentItem()+1,true);
                if(onBoardViewPager.getCurrentItem()==1){
                    onBoardButton.setText("Finish");
                }

            }
        });
    }
}