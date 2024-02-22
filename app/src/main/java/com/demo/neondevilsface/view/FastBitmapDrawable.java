package com.demo.neondevilsface.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import java.io.InputStream;


public class FastBitmapDrawable extends Drawable implements IBitmapDrawable {
    protected Bitmap mBitmap;
    protected Paint mPaint;

    @Override 
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    public FastBitmapDrawable(Bitmap bitmap) {
        this.mBitmap = bitmap;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setDither(true);
        this.mPaint.setFilterBitmap(true);
    }

    public FastBitmapDrawable(Resources resources, InputStream inputStream) {
        this(BitmapFactory.decodeStream(inputStream));
    }

    @Override 
    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, this.mPaint);
    }

    @Override 
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override 
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    @Override 
    public int getIntrinsicWidth() {
        return this.mBitmap.getWidth();
    }

    @Override 
    public int getIntrinsicHeight() {
        return this.mBitmap.getHeight();
    }

    @Override 
    public int getMinimumWidth() {
        return this.mBitmap.getWidth();
    }

    @Override 
    public int getMinimumHeight() {
        return this.mBitmap.getHeight();
    }

    public void setAntiAlias(boolean z) {
        this.mPaint.setAntiAlias(z);
        invalidateSelf();
    }

    @Override 
    public Bitmap getBitmap() {
        return this.mBitmap;
    }
}
