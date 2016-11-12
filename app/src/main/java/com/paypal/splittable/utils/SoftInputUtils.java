package com.paypal.splittable.utils;

import android.content.Context;
import android.os.IBinder;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by arbalan on 3/25/16.
 */
public class SoftInputUtils {

    public static void hideSoftInput(Context context, View view) {
        if (view != null) {
            hideSoftInput(context, view.getWindowToken());
        }
    }

    public static void hideSoftInput(Context context, IBinder windowToken) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, 0);
    }

    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void toggleSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, 0);
    }

    public static InputFilter getAlphaNumericInputFilter() {
        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String pattern = "^[a-zA-Z0-9]*$";
                if (source.toString().matches(pattern)) {
                    return null;
                }
                return "";
            }
        };
        return inputFilter;
    }
}
