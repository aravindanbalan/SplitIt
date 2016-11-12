package com.paypal.splittable.activities;

import com.paypal.splittable.R;
import com.paypal.splittable.fragments.ContainerFragment;
import com.paypal.splittable.fragments.FirstTimeUseFragment;
import com.paypal.splittable.utils.IConstants;
import com.paypal.splittable.utils.SharedPreferenceUtils;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class SplitTableActivity extends AppCompatActivity {
    private static final String LOG_TAG = SplitTableActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_table);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment;

        if (checkForFirstTimeUse()) {
            fragment = new FirstTimeUseFragment();
        } else {
            fragment = new ContainerFragment();
        }

        ft.add(R.id.landing_container, fragment);
        ft.commit();
    }

    //Check if the user has already registered his PYPL ME account
    private boolean checkForFirstTimeUse() {
        SharedPreferences sharedPreferences = SharedPreferenceUtils.getSharedPreference(getApplicationContext());
        return sharedPreferences.getBoolean(IConstants.FIRST_TIME_USE_KEY, true);
    }
}