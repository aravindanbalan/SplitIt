package com.paypal.splittable.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by arbalan on 3/25/16.
 */
public class SharedPreferenceUtils {
    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(IConstants.SHARED_KEYS, Context.MODE_PRIVATE);
    }

    public static String getPayPalMeUserName(Context context){
        SharedPreferences preferences = SharedPreferenceUtils.getSharedPreference(context);
        return preferences.getString(IConstants.PAYPAL_ME_USER_NAME, "");
    }
}