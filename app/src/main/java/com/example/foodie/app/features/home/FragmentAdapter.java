package com.example.foodie.app.features.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foodie.app.features.cartFragment.CartFragment;
import com.example.foodie.app.features.chatFragment.ChatFragment;
import com.example.foodie.app.features.homeFragment.HomeFragment;
import com.example.foodie.app.features.profileFragment.ProfileFragment;

public class FragmentAdapter extends FragmentStateAdapter {



    public FragmentAdapter(@NonNull  FragmentManager fragmentManager, @NonNull  Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull

    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1:
                return new ProfileFragment();
            case 2:
                return new CartFragment();
            case 3:
                return new ChatFragment();


        }
        return new HomeFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
