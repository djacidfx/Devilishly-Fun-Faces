package com.demo.neondevilsface.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.github.flipzeus.FlipDirection;
import com.github.flipzeus.ImageFlipper;


public class RotateImageView extends View {
    static final int FLIP_HORIZONTAL = 2;
    static final int FLIP_VERTICAL = 1;
    private Bitmap bitmap;
    private Paint bottomPaint;
    private RectF dstRect;
    private Matrix matrix;
    private Rect maxRect;
    private RectF originImageRect;
    private int rotateAngle;
    private float scale;
    private Rect srcRect;
    int type;
    private RectF wrapRect;

    public RotateImageView(Context context) {
        super(context);
        this.matrix = new Matrix();
        this.wrapRect = new RectF();
        init(context);
    }

    public RotateImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.matrix = new Matrix();
        this.wrapRect = new RectF();
        init(context);
    }

    public RotateImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.matrix = new Matrix();
        this.wrapRect = new RectF();
        init(context);
    }

    private void init(Context context) {
        this.srcRect = new Rect();
        this.dstRect = new RectF();
        this.maxRect = new Rect();
        this.bottomPaint = PaintUtil.newRotateBottomImagePaint();
        this.originImageRect = new RectF();
    }

    public void addBit(Bitmap bitmap, RectF rectF) {
        this.bitmap = bitmap;
        this.srcRect.set(0, 0, bitmap.getWidth(), this.bitmap.getHeight());
        this.dstRect = rectF;
        this.originImageRect.set(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());
        invalidate();
    }

    public void rotateImage(int i) {
        this.rotateAngle = i;
        invalidate();
    }

    public void settype(int i) {
        this.type = i;
        invalidate();
    }

    public void reset() {
        this.rotateAngle = 0;
        this.scale = 1.0f;
        invalidate();
    }

    @Override 
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.bitmap == null) {
            return;
        }
        this.maxRect.set(0, 0, getWidth(), getHeight());
        calculateWrapBox();
        this.scale = 1.0f;
        if (this.wrapRect.width() > getWidth()) {
            this.scale = getWidth() / this.wrapRect.width();
        }
        canvas.save();
        float f = this.scale;
        canvas.scale(f, f, canvas.getWidth() >> 1, canvas.getHeight() >> 1);
        canvas.drawRect(this.wrapRect, this.bottomPaint);
        canvas.rotate(this.rotateAngle, canvas.getWidth() >> 1, canvas.getHeight() >> 1);
        int i = this.type;
        if (i == 1) {
            Log.e("bitmap", "verticalinview");
            this.bitmap = ImageFlipper.flip(this.bitmap, FlipDirection.HORIZONTAL);
        } else if (i == 2) {
            Log.e("bitmap", "horizontalinview");
            this.bitmap = ImageFlipper.flip(this.bitmap, FlipDirection.VERTICAL);
        }
        canvas.drawBitmap(this.bitmap, this.srcRect, this.dstRect, (Paint) null);
        canvas.restore();
    }

    private void calculateWrapBox() {
        this.wrapRect.set(this.dstRect);
        this.matrix.reset();
        this.matrix.postRotate(this.rotateAngle, getWidth() >> 1, getHeight() >> 1);
        this.matrix.mapRect(this.wrapRect);
    }

    public RectF getImageNewRect() {
        Matrix matrix = new Matrix();
        matrix.postRotate(this.rotateAngle, this.originImageRect.centerX(), this.originImageRect.centerY());
        matrix.mapRect(this.originImageRect);
        return this.originImageRect;
    }

    public synchronized float getScale() {
        return this.scale;
    }

    public synchronized int getRotateAngle() {
        return this.rotateAngle;
    }
}
