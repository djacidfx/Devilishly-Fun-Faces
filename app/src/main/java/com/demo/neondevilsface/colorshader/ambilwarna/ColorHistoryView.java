package com.demo.neondevilsface.colorshader.ambilwarna;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class ColorHistoryView extends View {
    Paint paint;

    public ColorHistoryView(Context context, int i) {
        super(context);
        Paint paint = new Paint();
        this.paint = paint;
        paint.setColor(i);
    }

    public ColorHistoryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paint = new Paint();
    }

    @Override 
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(this.paint);
    }

    public void setColor(int i) {
        this.paint.setColor(i);
        invalidate();
    }

    @Override 
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        float f = getResources().getDisplayMetrics().density;
        if (f > 0.0f) {
            i3 = (int) (40.0f * f);
            i4 = (int) (f * 60.0f);
        } else {
            i3 = 40;
            i4 = 60;
        }
        setMeasuredDimension(i4, i3);
    }
}
