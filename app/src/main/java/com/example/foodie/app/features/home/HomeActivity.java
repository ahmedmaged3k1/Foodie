package com.example.foodie.app.features.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.foodie.R;
import com.example.foodie.app.core.animation.DepthPageTransformer;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {
    private TabLayout tabManger;
    private ViewPager2 viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        setFragments();
    }
    private void setFragments() {

        tabManger = findViewById(R.id.tabController);
        viewPagerAdapter = findViewById(R.id.viewPager);
        viewPagerAdapter.setPageTransformer(new DepthPageTransformer());
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPagerAdapter.setAdapter(fragmentAdapter);
        tabManger.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerAdapter.setCurrentItem(tab.getPosition());
               // tabManger.getTabAt(0).setIcon(R.drawable.home_active);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
              //  tabManger.getTabAt(0).setIcon(R.drawable.home);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPagerAdapter.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                tabManger.selectTab(tabManger.getTabAt(position));
            }
        });
    }
}