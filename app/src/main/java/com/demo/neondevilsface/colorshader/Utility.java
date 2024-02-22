package com.demo.neondevilsface.colorshader;

import android.graphics.BitmapFactory;
import android.os.Debug;


public class Utility {
    public static final int sizeDivider = 100000;

    private static long getFreeMemory() {
        return Runtime.getRuntime().maxMemory() - Debug.getNativeHeapAllocatedSize();
    }

    public static int maxSizeForDimension() {
        return (int) Math.sqrt(getFreeMemory() / 40.0d);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        double ceil;
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 > i2 || i4 > i) {
            if (i4 > i3) {
                ceil = Math.ceil(i3 / i2);
            } else {
                ceil = Math.ceil(i4 / i);
            }
            return (int) ceil;
        }
        return 1;
    }
}
