package com.demo.neondevilsface.colorshader;

import android.graphics.PointF;
import android.view.MotionEvent;


public class MyMotionEvent {
    public static final int ACTION_MASK = 255;
    public static final int ACTION_POINTER_DOWN = 5;

    public static float spacing(MotionEvent motionEvent) {
        try {
            float x = motionEvent.getX(0) - motionEvent.getX(1);
            float y = motionEvent.getY(0) - motionEvent.getY(1);
            return (float) Math.sqrt((x * x) + (y * y));
        } catch (Exception unused) {
            return 9.0f;
        }
    }

    public static void midPoint(PointF pointF, MotionEvent motionEvent) {
        pointF.set((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
    }
}
