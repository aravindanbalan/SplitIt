package com.paypal.splittable.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by arbalan on 3/28/16.
 */
public final class SplitTableApplication extends Application {
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}