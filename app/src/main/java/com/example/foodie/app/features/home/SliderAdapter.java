package com.example.foodie.app.features.home;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.foodie.R;

public class SliderAdapter extends PagerAdapter {
    Context context ;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public  int[] sliderImages ={R.drawable.onboard_1,R.drawable.onboard_2};
    public String[] sliderTitles={"Find your  Comfort Food here","Food Ninja is Where Your Comfort Food Lives"};
    public String[] sliderDesc={"Here You Can find a chef or dish for every taste and color. Enjoy!","Enjoy a fast and smooth food delivery at your doorstep"};

    @Override
    public int getCount() {
        return sliderImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_on_board,container,false);
        ImageView sliderImage = view.findViewById(R.id.onBoardImage);
        TextView sliderTitle =view.findViewById(R.id.onBoardTitle);
        TextView sliderInfo =view.findViewById(R.id.onBoardInfo);
        sliderImage.setImageResource(sliderImages[position]);
        sliderTitle.setText(sliderTitles[position]);
        sliderInfo.setText(sliderDesc[position]);

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
