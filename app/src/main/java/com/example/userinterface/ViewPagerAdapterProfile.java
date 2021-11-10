package com.example.userinterface;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.userinterface.screen.RecycleViewPost;

public class ViewPagerAdapterProfile extends FragmentPagerAdapter {
    public ViewPagerAdapterProfile(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RecycleViewPost();
            default:
                return new RecycleViewPost();

        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
