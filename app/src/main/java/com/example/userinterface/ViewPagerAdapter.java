package com.example.userinterface;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.userinterface.screen.ChatFragment;
import com.example.userinterface.screen.HomeFragment;
import com.example.userinterface.screen.PostFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new PostFragment();
            case 2:
                return new ChatFragment();
            default:
                return new HomeFragment();

        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
