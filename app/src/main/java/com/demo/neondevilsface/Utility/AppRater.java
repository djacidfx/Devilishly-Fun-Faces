package com.demo.neondevilsface.Utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;


public class AppRater {
    public static int DAYS_UNTIL_PROMPT = 1;
    public static final int MAX_NEVER_PROMPT = 1;
    public static final int MAX_RATE_PROMPT = 2;
    public static final int MAX_REMIND_PROMPT = 3;
    public static Context context;
    public static Long first_launch_date_time;
    public static Long launch_date_time;
    public static int never_count;
    public static int rate_count;
    public static int total_launch_count;

    public static void app_launched(Context context2, int i, int i2, int i3, int i4) {
        context = context2;
        SharedPreferences sharedPreferences = context2.getSharedPreferences("app_rater", 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        total_launch_count = sharedPreferences.getInt("total_launch_count", 1);
        never_count = sharedPreferences.getInt("never_count", 1);
        rate_count = sharedPreferences.getInt("rate_count", 1);
        if (sharedPreferences.getBoolean("do_not_show_again", false)) {
            return;
        }
        Long valueOf = Long.valueOf(sharedPreferences.getLong("first_launch_date_time", 0L));
        first_launch_date_time = valueOf;
        if (valueOf.longValue() == 0) {
            Long valueOf2 = Long.valueOf(System.currentTimeMillis());
            first_launch_date_time = valueOf2;
            edit.putLong("first_launch_date_time", valueOf2.longValue());
        }
        launch_date_time = Long.valueOf(sharedPreferences.getLong("launch_date_time", 0L));
        if (System.currentTimeMillis() >= launch_date_time.longValue() + 86400000 && DAYS_UNTIL_PROMPT <= 3) {
            edit.putLong("launch_date_time", System.currentTimeMillis());
            DAYS_UNTIL_PROMPT++;
        }
        int i5 = total_launch_count;
        if (i5 <= 3) {
            if (edit != null) {
                edit.putInt("total_launch_count", i5 + 1);
                edit.commit();
            }
            if (total_launch_count == 1) {
                showRateDialog(context, i, i2, i3, i4);
            } else if (System.currentTimeMillis() >= launch_date_time.longValue() + 86400000) {
                showRateDialog(context, i, i2, i3, i4);
            }
        }
        edit.commit();
    }

    public static void showRateDialog(final Context context2, int i, int i2, int i3, int i4) {
        SharedPreferences sharedPreferences = context2.getSharedPreferences("app_rater", 0);
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        if (sharedPreferences.getBoolean("do_not_show_again", false)) {
            return;
        }
        final Dialog dialog = new Dialog(context2);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(null);
        View inflate = ((Activity) context).getLayoutInflater().inflate(i, (ViewGroup) null);
        View findViewById = inflate.findViewById(i2);
        View findViewById2 = inflate.findViewById(i3);
        View findViewById3 = inflate.findViewById(i4);
        findViewById.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (AppRater.never_count <= 1) {
                    SharedPreferences.Editor editor = edit;
                    if (editor != null) {
                        editor.putInt("never_count", AppRater.never_count + 1);
                        edit.commit();
                    }
                } else {
                    SharedPreferences.Editor editor2 = edit;
                    if (editor2 != null) {
                        editor2.putBoolean("do_not_show_again", true);
                        edit.commit();
                    }
                }
                dialog.dismiss();
            }
        });
        findViewById2.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        findViewById3.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (AppRater.rate_count <= 2) {
                    SharedPreferences.Editor editor = edit;
                    if (editor != null) {
                        editor.putInt("rate_count", AppRater.rate_count + 1);
                        edit.commit();
                    }
                    Context context3 = context2;
                    context3.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + AppRater.context.getPackageName())));
                } else {
                    SharedPreferences.Editor editor2 = edit;
                    if (editor2 != null) {
                        editor2.putBoolean("do_not_show_again", true);
                        edit.commit();
                    }
                }
                dialog.dismiss();
            }
        });
        dialog.setContentView(inflate);
        dialog.getWindow().setBackgroundDrawableResource(17170445);
        dialog.show();
    }
}
