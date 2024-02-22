package com.demo.neondevilsface.Utility;

import android.os.Handler;


public class AppUtil {
    private static boolean clicked = true;

    public static boolean isClickable() {
        if (clicked) {
            clicked = false;
            new Handler().postDelayed(new Runnable() { 
                @Override 
                public void run() {
                    boolean unused = AppUtil.clicked = true;
                }
            }, 900L);
            return true;
        }
        return false;
    }
}
