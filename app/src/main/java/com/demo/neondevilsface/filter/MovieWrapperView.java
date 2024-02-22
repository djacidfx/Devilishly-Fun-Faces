package com.demo.neondevilsface.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class MovieWrapperView extends FrameLayout {
    public MovieWrapperView(Context context) {
        super(context);
    }

    public MovieWrapperView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MovieWrapperView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override 
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        setMeasuredDimension(measuredWidth, (measuredWidth / 16) * 9);
    }
}
