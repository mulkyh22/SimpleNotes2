package com.app.simplenotes2.ui.about;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AboutPagerAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES = 2;

    public AboutPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new FragmentAboutPage();
        } else if (position == 1) {
            return new FragmentAboutPage1();
        }
        return null; // Or return a default fragment if needed
    }


    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}

//Signature :
//10120146 - Irshal Mulky H - IF4
