package com.demo.neondevilsface.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.demo.neondevilsface.R;


public class BrushImageView extends AppCompatImageView {
    int alpga;
    public float centerx;
    public float centery;
    int density;
    DisplayMetrics metrics;
    public float offset;
    public float smallRadious;
    public float width;

    public BrushImageView(Context context) {
        super(context);
        this.alpga = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.metrics = displayMetrics;
        int i = (int) displayMetrics.density;
        this.density = i;
        this.centerx = i * 166;
        this.centery = i * ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        this.offset = i * 100;
        this.smallRadious = i * 3;
        this.width = i * 33;
    }

    public BrushImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.alpga = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.metrics = displayMetrics;
        int i = (int) displayMetrics.density;
        this.density = i;
        this.centerx = i * 166;
        this.centery = i * ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        this.offset = i * 100;
        this.smallRadious = i * 3;
        this.width = i * 33;
    }

    @Override 
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas.getSaveCount() > 1) {
            canvas.restore();
        }
        canvas.save();
        if (this.offset > 0.0f) {
            Paint paint = new Paint();
            paint.setColor(Color.argb(255, 255, 0, 0));
            paint.setAntiAlias(true);
            canvas.drawCircle(this.centerx, this.centery, this.smallRadious, paint);
        }
        Paint paint2 = new Paint();
        paint2.setColor(getResources().getColor(R.color.color_table));
        paint2.setAntiAlias(true);
        canvas.drawCircle(this.centerx, this.centery - this.offset, this.width, paint2);
    }
}
