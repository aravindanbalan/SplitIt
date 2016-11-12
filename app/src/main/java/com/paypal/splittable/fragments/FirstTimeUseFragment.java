package com.paypal.splittable.fragments;

import com.paypal.splittable.R;
import com.paypal.splittable.utils.IConstants;
import com.paypal.splittable.utils.SharedPreferenceUtils;
import com.paypal.splittable.utils.SoftInputUtils;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by arbalan on 3/21/16.
 */
public class FirstTimeUseFragment extends Fragment {
    private String mUserName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View top = inflater.inflate(R.layout.fragment_first_time_use, container, false);
        Toolbar toolbar = (Toolbar) top.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.white));

        EditText username = (EditText) top.findViewById(R.id.username);
        username.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if ((keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    onUserNameEntered();
                    return true;
                } else {
                    return false;
                }
            }
        });
        return top;
    }

    private void onUserNameEntered() {
        View view = getView();
        if (view != null) {
            EditText username = (EditText) view.findViewById(R.id.username);
            if (username != null && !TextUtils.isEmpty(username.getText())) {
                mUserName = username.getText().toString();
                hideSoftKeyboard(view);
                updateSharedPreference(mUserName);
                navigateToContainerFragment();
            }
        }
    }

    private void updateSharedPreference(String userName) {
        if (!TextUtils.isEmpty(userName)) {
            userName = userName.trim();
            SharedPreferences preferences = SharedPreferenceUtils.getSharedPreference(getActivity());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(IConstants.FIRST_TIME_USE_KEY, false);
            editor.putString(IConstants.PAYPAL_ME_USER_NAME, userName);
            editor.commit();
        }
    }

    private void navigateToContainerFragment() {
        ContainerFragment containerFragment = new ContainerFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (ft != null) {
            ft.replace(R.id.landing_container, containerFragment);
            ft.commit();
        }
    }

    public void hideSoftKeyboard(View view) {
        if (null != view) {
            View editor = view.findViewById(R.id.username);
            if (null != editor) {
                SoftInputUtils.hideSoftInput(getActivity(), editor);
            }
        }
    }
}