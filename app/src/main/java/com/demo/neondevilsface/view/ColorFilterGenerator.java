package com.demo.neondevilsface.view;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;


public class ColorFilterGenerator {
    public static ColorFilter adjustHue(float f, String str) {
        ColorMatrix colorMatrix = new ColorMatrix();
        adjustHue(colorMatrix, f, str);
        return new ColorMatrixColorFilter(colorMatrix);
    }

    public static void adjustHue(ColorMatrix colorMatrix, float f, String str) {
        float cleanValue;
        if (str.equals("normal")) {
            cleanValue = cleanValue(f, 180.0f);
        } else {
            cleanValue = cleanValue(f, 360.0f);
        }
        float f2 = (cleanValue / 180.0f) * 3.1415927f;
        if (f2 != 0.0f) {
            double d = f2;
            float cos = (float) Math.cos(d);
            float sin = (float) Math.sin(d);
            float f3 = (cos * (-1.06057171E9f)) + 0.715f;
            float f4 = ((-1.03307386E9f) * cos) + 0.072f;
            float f5 = ((-1.04609299E9f) * cos) + 0.213f;
            colorMatrix.postConcat(new ColorMatrix(new float[]{(0.787f * cos) + 0.213f + (sin * (-1.04609299E9f)), ((-1.06057171E9f) * sin) + f3, (sin * 0.928f) + f4, 0.0f, 0.0f, (0.143f * sin) + f5, (0.28500003f * cos) + 0.715f + (0.14f * sin), f4 + ((-0.283f) * sin), 0.0f, 0.0f, f5 + ((-0.787f) * sin), f3 + (0.715f * sin), (cos * 0.928f) + 0.072f + (sin * 0.072f), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f}));
        }
    }

    protected static float cleanValue(float f, float f2) {
        return Math.min(f2, Math.max(-f2, f));
    }
}
