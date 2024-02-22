package com.demo.neondevilsface.colorshader;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;


public class MyViewFlipper extends ViewFlipper {
    public MyViewFlipper(Context context) {
        super(context);
    }

    public MyViewFlipper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override 
    public void onDetachedFromWindow() {
        try {
            super.onDetachedFromWindow();
        } catch (IllegalArgumentException unused) {
            stopFlipping();
        }
    }
}
