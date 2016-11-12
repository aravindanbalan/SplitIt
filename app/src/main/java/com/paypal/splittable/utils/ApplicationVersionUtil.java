package com.paypal.splittable.utils;

import com.paypal.splittable.app.SplitTableApplication;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import java.util.Locale;

/**
 * Created by arbalan on 3/28/16.
 */
public class ApplicationVersionUtil {
    private static String sVersion;
    private static String sUserAgent;

    public static String getVersion() {
        if (TextUtils.isEmpty(sVersion)) {
            try {
                Context context = SplitTableApplication.getContext();
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                if (packageInfo != null) {
                    sVersion = packageInfo.versionName;
                }
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }
        return sVersion;
    }

    /**
     * Returns a user agent string containing device and application info.
     *
     * @return The user agent string
     */
    public static String getUserAgent() {
        if (sUserAgent == null) {
            StringBuilder sb = new StringBuilder(256);

            sb.append("Mozilla/5.0 (Linux; U; Android ");
            sb.append(Build.VERSION.RELEASE);
            sb.append("; ");
            sb.append(Locale.getDefault().toString().toLowerCase(Locale.getDefault()).replace("_", "-"));

            String model = Build.MODEL;
            if (model != null && model.length() != 0) {
                sb.append("; ");
                sb.append(model);
            }

            String buildId = Build.ID;
            if (buildId != null && buildId.length() != 0) {
                sb.append(" Build/");
                sb.append(buildId);
            }

            sb.append(")");

            Context context = SplitTableApplication.getContext();
            if (context != null) {
                int versionCode = 0;
                String packageName = context.getPackageName();
                try {
                    PackageManager manager = context.getPackageManager();
                    PackageInfo packageInfo = manager.getPackageInfo(packageName, 0);
                    versionCode = packageInfo.versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    // Use default versionCode 0.
                }

                sb.append(" ");
                sb.append(packageName);
                sb.append("/");
                sb.append(versionCode);
            }

            sUserAgent = sb.toString();
        }

        return sUserAgent;
    }
}
