package com.app.simplenotes2.ui.about;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.app.simplenotes2.R;

public class FragmentAbout extends Fragment {

    private ViewPager2 viewPager;
    private AboutPagerAdapter aboutPagerAdapter;

    public FragmentAbout() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        viewPager = rootView.findViewById(R.id.viewPager);
        aboutPagerAdapter = new AboutPagerAdapter(requireActivity());
        viewPager.setAdapter(aboutPagerAdapter);

        viewPager.setCurrentItem(0); // Set Page 2 as the default page

        return rootView;
    }
}

//Signature :
//10120146 - Irshal Mulky H - IF4