package com.paypal.splittable.fragments;

import com.paypal.splittable.R;
import com.paypal.splittable.adapters.SplitTableFragmentPagerAdapter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by arbalan on 3/21/16.
 */
public class ContainerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View top = inflater.inflate(R.layout.fragment_container, container, false);

        Toolbar toolbar = (Toolbar) top.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.white));

        TabLayout tabLayout = (TabLayout) top.findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.split_bill_tab).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.icon_bill)));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.camera_scan_tab).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.icon_camera)));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.message).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.icon_camera)));

        final ViewPager viewPager = (ViewPager) top.findViewById(R.id.viewpager);
        viewPager.setAdapter(new SplitTableFragmentPagerAdapter(getActivity().getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return top;
    }
}