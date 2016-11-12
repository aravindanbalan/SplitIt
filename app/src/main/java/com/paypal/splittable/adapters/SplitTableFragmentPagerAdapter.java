package com.paypal.splittable.adapters;

import com.paypal.splittable.fragments.CameraScannerFragment;
import com.paypal.splittable.fragments.MessageFragment;
import com.paypal.splittable.fragments.SplitBillFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by arbalan on 3/21/16.
 */
public class SplitTableFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 3;

    public SplitTableFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new SplitBillFragment();
                return fragment;
            case 1:
                fragment = new CameraScannerFragment();
                return fragment;
            case 2:
                fragment = new MessageFragment();
                return fragment;
            default:
                return fragment;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}