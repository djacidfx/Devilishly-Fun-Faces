package com.demo.neondevilsface.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import androidx.recyclerview.widget.ItemTouchHelper;


public abstract class ImageViewTouchBase extends ImageView implements IDisposable {
    protected static final boolean LOG_ENABLED = false;
    public static final String LOG_TAG = "ImageViewTouchBase";
    public static final float ZOOM_INVALID = -1.0f;
    protected final int DEFAULT_ANIMATION_DURATION;
    protected Matrix mBaseMatrix;
    private boolean mBitmapChanged;
    protected RectF mBitmapRect;
    private PointF mCenter;
    protected RectF mCenterRect;
    protected final Matrix mDisplayMatrix;
    private OnDrawableChangeListener mDrawableChangeListener;
    protected Easing mEasing;
    protected Handler mHandler;
    protected Runnable mLayoutRunnable;
    protected final float[] mMatrixValues;
    private float mMaxZoom;
    private boolean mMaxZoomDefined;
    private float mMinZoom;
    private boolean mMinZoomDefined;
    protected Matrix mNextMatrix;
    private OnLayoutChangeListener mOnLayoutChangeListener;
    protected DisplayType mScaleType;
    private boolean mScaleTypeChanged;
    protected RectF mScrollRect;
    protected Matrix mSuppMatrix;
    private int mThisHeight;
    private int mThisWidth;
    protected boolean mUserScaled;

    
    public enum DisplayType {
        NONE,
        FIT_TO_SCREEN,
        FIT_IF_BIGGER
    }

    
    public interface OnDrawableChangeListener {
        void onDrawableChanged(Drawable drawable);
    }

    
    public interface OnLayoutChangeListener {
        void onLayoutChanged(boolean z, int i, int i2, int i3, int i4);
    }

    @Override 
    public float getRotation() {
        return 0.0f;
    }

    protected void onImageMatrixChanged() {
    }

    protected void onZoom(float f) {
    }

    protected void onZoomAnimationCompleted(float f) {
    }

    public ImageViewTouchBase(Context context) {
        super(context);
        this.mEasing = new Cubic();
        this.mBaseMatrix = new Matrix();
        this.mSuppMatrix = new Matrix();
        this.mHandler = new Handler();
        this.mLayoutRunnable = null;
        this.mUserScaled = false;
        this.mMaxZoom = -1.0f;
        this.mMinZoom = -1.0f;
        this.mDisplayMatrix = new Matrix();
        this.mMatrixValues = new float[9];
        this.mThisWidth = -1;
        this.mThisHeight = -1;
        this.mCenter = new PointF();
        this.mScaleType = DisplayType.NONE;
        this.DEFAULT_ANIMATION_DURATION = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        this.mBitmapRect = new RectF();
        this.mCenterRect = new RectF();
        this.mScrollRect = new RectF();
        init();
    }

    public ImageViewTouchBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEasing = new Cubic();
        this.mBaseMatrix = new Matrix();
        this.mSuppMatrix = new Matrix();
        this.mHandler = new Handler();
        this.mLayoutRunnable = null;
        this.mUserScaled = false;
        this.mMaxZoom = -1.0f;
        this.mMinZoom = -1.0f;
        this.mDisplayMatrix = new Matrix();
        this.mMatrixValues = new float[9];
        this.mThisWidth = -1;
        this.mThisHeight = -1;
        this.mCenter = new PointF();
        this.mScaleType = DisplayType.NONE;
        this.DEFAULT_ANIMATION_DURATION = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        this.mBitmapRect = new RectF();
        this.mCenterRect = new RectF();
        this.mScrollRect = new RectF();
        init();
    }

    public void setOnDrawableChangedListener(OnDrawableChangeListener onDrawableChangeListener) {
        this.mDrawableChangeListener = onDrawableChangeListener;
    }

    public void setOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
        this.mOnLayoutChangeListener = onLayoutChangeListener;
    }

    
    public void init() {
        setScaleType(ScaleType.MATRIX);
    }

    @Override 
    public void setScaleType(ScaleType scaleType) {
        if (scaleType == ScaleType.MATRIX) {
            super.setScaleType(scaleType);
        } else {
            Log.w(LOG_TAG, "Unsupported scaletype. Only MATRIX can be used");
        }
    }

    public void clear() {
        setImageBitmap(null);
    }

    public void setDisplayType(DisplayType displayType) {
        if (displayType != this.mScaleType) {
            this.mUserScaled = false;
            this.mScaleType = displayType;
            this.mScaleTypeChanged = true;
            requestLayout();
        }
    }

    public DisplayType getDisplayType() {
        return this.mScaleType;
    }

    protected void setMinScale(float f) {
        this.mMinZoom = f;
    }

    protected void setMaxScale(float f) {
        this.mMaxZoom = f;
    }

    @Override 
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {

        int i5;
        int i6;
        float defaultScale;
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            int i7 = this.mThisWidth;
            int i8 = this.mThisHeight;
            int i9 = i3 - i;
            this.mThisWidth = i9;
            int i10 = i4 - i2;
            this.mThisHeight = i10;
            i5 = i9 - i7;
            i6 = i10 - i8;
            this.mCenter.x = i9 / 2.0f;
            this.mCenter.y = this.mThisHeight / 2.0f;
        } else {
            i5 = 0;
            i6 = 0;
        }
        Runnable runnable = this.mLayoutRunnable;
        if (runnable != null) {
            this.mLayoutRunnable = null;
            runnable.run();
        }
        float r12 = 1;
        Drawable drawable = getDrawable();
        if (drawable != null) {
            if (z || this.mScaleTypeChanged || this.mBitmapChanged) {
                getDefaultScale(this.mScaleType);
                float scale = getScale(this.mBaseMatrix);
                float scale2 = getScale();
                float min = Math.min(1.0f, 1.0f / scale);
                getProperBaseMatrix(drawable, this.mBaseMatrix);
                float scale3 = getScale(this.mBaseMatrix);
                if (this.mBitmapChanged || this.mScaleTypeChanged) {
                    Matrix matrix = this.mNextMatrix;
                    if (matrix != null) {
                        this.mSuppMatrix.set(matrix);
                        this.mNextMatrix = null;
                        defaultScale = getScale();
                    } else {
                        this.mSuppMatrix.reset();
                        defaultScale = getDefaultScale(this.mScaleType);
                    }
                    r12 = defaultScale;
                    setImageMatrix(getImageViewMatrix());
                    if (r12 != getScale()) {
                        zoomTo(r12);
                    }
                } else if (z) {
                    if (!this.mMinZoomDefined) {
                        this.mMinZoom = -1.0f;
                    }
                    if (!this.mMaxZoomDefined) {
                        this.mMaxZoom = -1.0f;
                    }
                    setImageMatrix(getImageViewMatrix());
                    postTranslate(-i5, -i6);
                    if (!this.mUserScaled) {
                        r12 = getDefaultScale(this.mScaleType);
                        zoomTo(r12);
                    } else {
                        r12 = ((double) Math.abs(scale2 - min)) > 0.001d ? (scale / scale3) * scale2 : 1.0f;
                        zoomTo(r12);
                    }
                }
                this.mUserScaled = false;
                if (r12 > getMaxScale() || r12 < getMinScale()) {
                    zoomTo(r12);
                }
                center(true, true);
                if (this.mBitmapChanged) {
                    onDrawableChanged(drawable);
                }
                if (z || this.mBitmapChanged || this.mScaleTypeChanged) {
                    onLayoutChanged(i, i2, i3, i4);
                }
                if (this.mScaleTypeChanged) {
                    this.mScaleTypeChanged = false;
                }
                if (this.mBitmapChanged) {
                    this.mBitmapChanged = false;
                    return;
                }
                return;
            }
            return;
        }
        if (this.mBitmapChanged) {
            onDrawableChanged(drawable);
        }
        if (z || this.mBitmapChanged || this.mScaleTypeChanged) {
            onLayoutChanged(i, i2, i3, i4);
        }
        if (this.mBitmapChanged) {
            this.mBitmapChanged = false;
        }
        if (this.mScaleTypeChanged) {
            this.mScaleTypeChanged = false;
        }
    }

    public void resetDisplay() {
        this.mBitmapChanged = true;
        requestLayout();
    }

    protected float getDefaultScale(DisplayType displayType) {
        if (displayType == DisplayType.FIT_TO_SCREEN) {
            return 1.0f;
        }
        if (displayType == DisplayType.FIT_IF_BIGGER) {
            return Math.min(1.0f, 1.0f / getScale(this.mBaseMatrix));
        }
        return 1.0f / getScale(this.mBaseMatrix);
    }

    @Override 
    public void setImageResource(int i) {
        setImageDrawable(getContext().getResources().getDrawable(i));
    }

    @Override 
    public void setImageBitmap(Bitmap bitmap) {
        setImageBitmap(bitmap, null, -1.0f, -1.0f);
    }

    public void setImageBitmap(Bitmap bitmap, Matrix matrix, float f, float f2) {
        if (bitmap != null) {
            setImageDrawable(new FastBitmapDrawable(bitmap), matrix, f, f2);
        } else {
            setImageDrawable(null, matrix, f, f2);
        }
    }

    @Override 
    public void setImageDrawable(Drawable drawable) {
        setImageDrawable(drawable, null, -1.0f, -1.0f);
    }

    public void setImageDrawable(final Drawable drawable, final Matrix matrix, final float f, final float f2) {
        if (getWidth() <= 0) {
            this.mLayoutRunnable = new Runnable() { 
                @Override 
                public void run() {
                    ImageViewTouchBase.this.setImageDrawable(drawable, matrix, f, f2);
                }
            };
        } else {
            _setImageDrawable(drawable, matrix, f, f2);
        }
    }

    
    public void _setImageDrawable(Drawable drawable, Matrix matrix, float f, float f2) {
        if (drawable != null) {
            super.setImageDrawable(drawable);
        } else {
            this.mBaseMatrix.reset();
            super.setImageDrawable(null);
        }
        if (f != -1.0f && f2 != -1.0f) {
            float min = Math.min(f, f2);
            float max = Math.max(min, f2);
            this.mMinZoom = min;
            this.mMaxZoom = max;
            this.mMinZoomDefined = true;
            this.mMaxZoomDefined = true;
            if (this.mScaleType == DisplayType.FIT_TO_SCREEN || this.mScaleType == DisplayType.FIT_IF_BIGGER) {
                if (this.mMinZoom >= 1.0f) {
                    this.mMinZoomDefined = false;
                    this.mMinZoom = -1.0f;
                }
                if (this.mMaxZoom <= 1.0f) {
                    this.mMaxZoomDefined = true;
                    this.mMaxZoom = -1.0f;
                }
            }
        } else {
            this.mMinZoom = -1.0f;
            this.mMaxZoom = -1.0f;
            this.mMinZoomDefined = false;
            this.mMaxZoomDefined = false;
        }
        if (matrix != null) {
            this.mNextMatrix = new Matrix(matrix);
        }
        this.mBitmapChanged = true;
        requestLayout();
    }

    protected void onDrawableChanged(Drawable drawable) {
        fireOnDrawableChangeListener(drawable);
    }

    protected void fireOnLayoutChangeListener(int i, int i2, int i3, int i4) {
        OnLayoutChangeListener onLayoutChangeListener = this.mOnLayoutChangeListener;
        if (onLayoutChangeListener != null) {
            onLayoutChangeListener.onLayoutChanged(true, i, i2, i3, i4);
        }
    }

    protected void fireOnDrawableChangeListener(Drawable drawable) {
        OnDrawableChangeListener onDrawableChangeListener = this.mDrawableChangeListener;
        if (onDrawableChangeListener != null) {
            onDrawableChangeListener.onDrawableChanged(drawable);
        }
    }

    protected void onLayoutChanged(int i, int i2, int i3, int i4) {
        fireOnLayoutChangeListener(i, i2, i3, i4);
    }

    protected float computeMaxZoom() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return 1.0f;
        }
        return Math.max(drawable.getIntrinsicWidth() / this.mThisWidth, drawable.getIntrinsicHeight() / this.mThisHeight) * 8.0f;
    }

    protected float computeMinZoom() {
        if (getDrawable() == null) {
            return 1.0f;
        }
        return Math.min(1.0f, 1.0f / getScale(this.mBaseMatrix));
    }

    public float getMaxScale() {
        if (this.mMaxZoom == -1.0f) {
            this.mMaxZoom = computeMaxZoom();
        }
        return this.mMaxZoom;
    }

    public float getMinScale() {
        if (this.mMinZoom == -1.0f) {
            this.mMinZoom = computeMinZoom();
        }
        return this.mMinZoom;
    }

    public Matrix getImageViewMatrix() {
        return getImageViewMatrix(this.mSuppMatrix);
    }

    public Matrix getImageViewMatrix(Matrix matrix) {
        this.mDisplayMatrix.set(this.mBaseMatrix);
        this.mDisplayMatrix.postConcat(matrix);
        return this.mDisplayMatrix;
    }

    @Override 
    public void setImageMatrix(Matrix matrix) {
        Matrix imageMatrix = getImageMatrix();
        boolean z = (matrix == null && !imageMatrix.isIdentity()) || !(matrix == null || imageMatrix.equals(matrix));
        super.setImageMatrix(matrix);
        if (z) {
            onImageMatrixChanged();
        }
    }

    public Matrix getDisplayMatrix() {
        return new Matrix(this.mSuppMatrix);
    }

    protected void getProperBaseMatrix(Drawable drawable, Matrix matrix) {
        float f = this.mThisWidth;
        float f2 = this.mThisHeight;
        float intrinsicWidth = drawable.getIntrinsicWidth();
        float intrinsicHeight = drawable.getIntrinsicHeight();
        matrix.reset();
        if (intrinsicWidth > f || intrinsicHeight > f2) {
            float min = Math.min(f / intrinsicWidth, f2 / intrinsicHeight);
            matrix.postScale(min, min);
            matrix.postTranslate((f - (intrinsicWidth * min)) / 2.0f, (f2 - (intrinsicHeight * min)) / 2.0f);
            return;
        }
        float min2 = Math.min(f / intrinsicWidth, f2 / intrinsicHeight);
        matrix.postScale(min2, min2);
        matrix.postTranslate((f - (intrinsicWidth * min2)) / 2.0f, (f2 - (intrinsicHeight * min2)) / 2.0f);
    }

    protected void getProperBaseMatrix2(Drawable drawable, Matrix matrix) {
        float f = this.mThisWidth;
        float f2 = this.mThisHeight;
        float intrinsicWidth = drawable.getIntrinsicWidth();
        float intrinsicHeight = drawable.getIntrinsicHeight();
        matrix.reset();
        float min = Math.min(f / intrinsicWidth, f2 / intrinsicHeight);
        matrix.postScale(min, min);
        matrix.postTranslate((f - (intrinsicWidth * min)) / 2.0f, (f2 - (intrinsicHeight * min)) / 2.0f);
    }

    protected float getValue(Matrix matrix, int i) {
        matrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[i];
    }

    public void printMatrix(Matrix matrix) {
        float value = getValue(matrix, 0);
        float value2 = getValue(matrix, 4);
        float value3 = getValue(matrix, 2);
        float value4 = getValue(matrix, 5);
        Log.d(LOG_TAG, "matrix: { x: " + value3 + ", y: " + value4 + ", scalex: " + value + ", scaley: " + value2 + " }");
    }

    public RectF getBitmapRect() {
        return getBitmapRect(this.mSuppMatrix);
    }

    protected RectF getBitmapRect(Matrix matrix) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return null;
        }
        Matrix imageViewMatrix = getImageViewMatrix(matrix);
        this.mBitmapRect.set(0.0f, 0.0f, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        imageViewMatrix.mapRect(this.mBitmapRect);
        return this.mBitmapRect;
    }

    protected float getScale(Matrix matrix) {
        return getValue(matrix, 0);
    }

    public float getScale() {
        return getScale(this.mSuppMatrix);
    }

    protected void center(boolean z, boolean z2) {
        if (getDrawable() == null) {
            return;
        }
        RectF center = getCenter(this.mSuppMatrix, z, z2);
        if (center.left == 0.0f && center.top == 0.0f) {
            return;
        }
        postTranslate(center.left, center.top);
    }

    
    
    protected RectF getCenter(Matrix supportMatrix, boolean horizontal, boolean vertical) {
        final Drawable drawable = getDrawable();

        if (drawable == null) {
            return new RectF(0, 0, 0, 0);
        }

        mCenterRect.set(0, 0, 0, 0);
        RectF rect = getBitmapRect(supportMatrix);
        float height = rect.height();
        float width = rect.width();
        float deltaX = 0, deltaY = 0;
        if (vertical) {
            if (height < getBitmapRect().height()) {
                deltaY = (getBitmapRect().height() - height) / 2 - (rect.top - getBitmapRect().top);
            } else if (rect.top > getBitmapRect().top) {
                deltaY = -(rect.top - getBitmapRect().top);
            } else if (rect.bottom < getBitmapRect().bottom) {
                deltaY = getBitmapRect().bottom - rect.bottom;
            }
        }
        if (horizontal) {
            if (width < getBitmapRect().width()) {
                deltaX = (getBitmapRect().width() - width) / 2 - (rect.left - getBitmapRect().left);
            } else if (rect.left > getBitmapRect().left) {
                deltaX = -(rect.left - getBitmapRect().left);
            } else if (rect.right < getBitmapRect().right) {
                deltaX = getBitmapRect().right - rect.right;
            }
        }
        mCenterRect.set(deltaX, deltaY, 0, 0);
        return mCenterRect;
    }

    protected void postTranslate(float f, float f2) {
        if (f == 0.0f && f2 == 0.0f) {
            return;
        }
        this.mSuppMatrix.postTranslate(f, f2);
        setImageMatrix(getImageViewMatrix());
    }

    protected void postScale(float f, float f2, float f3) {
        this.mSuppMatrix.postScale(f, f, f2, f3);
        setImageMatrix(getImageViewMatrix());
    }

    protected PointF getCenter() {
        return this.mCenter;
    }

    protected void zoomTo(float f) {
        if (f > getMaxScale()) {
            f = getMaxScale();
        }
        if (f < getMinScale()) {
            f = getMinScale();
        }
        PointF center = getCenter();
        zoomTo(f, center.x, center.y);
    }

    public void zoomTo(float f, float f2) {
        PointF center = getCenter();
        zoomTo(f, center.x, center.y, f2);
    }

    
    public void zoomTo(float f, float f2, float f3) {
        if (f > getMaxScale()) {
            f = getMaxScale();
        }
        postScale(f / getScale(), f2, f3);
        onZoom(getScale());
        center(true, true);
    }

    public void scrollBy(float f, float f2) {
        panBy(f, f2);
    }

    protected void panBy(double d, double d2) {
        RectF bitmapRect = getBitmapRect();
        this.mScrollRect.set((float) d, (float) d2, 0.0f, 0.0f);
        updateRect(bitmapRect, this.mScrollRect);
        postTranslate(this.mScrollRect.left, this.mScrollRect.top);
        center(true, true);
    }

    
    public void updateRect(RectF rectF, RectF rectF2) {
        if (rectF == null) {
            return;
        }
        if (rectF.top >= 0.0f && rectF.bottom <= this.mThisHeight) {
            rectF2.top = 0.0f;
        }
        if (rectF.left >= 0.0f && rectF.right <= this.mThisWidth) {
            rectF2.left = 0.0f;
        }
        if (rectF.top + rectF2.top >= 0.0f && rectF.bottom > this.mThisHeight) {
            rectF2.top = (int) (0.0f - rectF.top);
        }
        if (rectF.bottom + rectF2.top <= this.mThisHeight + 0 && rectF.top < 0.0f) {
            rectF2.top = (int) ((this.mThisHeight + 0) - rectF.bottom);
        }
        if (rectF.left + rectF2.left >= 0.0f) {
            rectF2.left = (int) (0.0f - rectF.left);
        }
        float f = rectF.right + rectF2.left;
        int i = this.mThisWidth;
        if (f <= i + 0) {
            rectF2.left = (int) ((i + 0) - rectF.right);
        }
    }

    
    public void scrollBy(float f, float f2, final double d) {
        final double d2 = f;
        final double d3 = f2;
        final long currentTimeMillis = System.currentTimeMillis();
        this.mHandler.post(new Runnable() { 
            double old_x = 0.0d;
            double old_y = 0.0d;

            @Override 
            public void run() {
                double min = Math.min(d, System.currentTimeMillis() - currentTimeMillis);
                double easeOut = ImageViewTouchBase.this.mEasing.easeOut(min, 0.0d, d2, d);
                double easeOut2 = ImageViewTouchBase.this.mEasing.easeOut(min, 0.0d, d3, d);
                ImageViewTouchBase.this.panBy(easeOut - this.old_x, easeOut2 - this.old_y);
                this.old_x = easeOut;
                this.old_y = easeOut2;
                if (min < d) {
                    ImageViewTouchBase.this.mHandler.post(this);
                    return;
                }
                ImageViewTouchBase imageViewTouchBase = ImageViewTouchBase.this;
                RectF center = imageViewTouchBase.getCenter(imageViewTouchBase.mSuppMatrix, true, true);
                if (center.left == 0.0f && center.top == 0.0f) {
                    return;
                }
                ImageViewTouchBase.this.scrollBy(center.left, center.top);
            }
        });
    }

    
    public void zoomTo(float f, float f2, float f3, final float f4) {
        if (f > getMaxScale()) {
            f = getMaxScale();
        }
        final long currentTimeMillis = System.currentTimeMillis();
        final float scale = getScale();
        final float f5 = f - scale;
        Matrix matrix = new Matrix(this.mSuppMatrix);
        matrix.postScale(f, f, f2, f3);
        RectF center = getCenter(matrix, true, true);
        final float f6 = f2 + (center.left * f);
        final float f7 = f3 + (center.top * f);
        this.mHandler.post(new Runnable() { 
            @Override 
            public void run() {
                float min = Math.min(f4, (float) (System.currentTimeMillis() - currentTimeMillis));
                ImageViewTouchBase.this.zoomTo(scale + ((float) ImageViewTouchBase.this.mEasing.easeInOut(min, 0.0d, f5, f4)), f6, f7);
                if (min < f4) {
                    ImageViewTouchBase.this.mHandler.post(this);
                    return;
                }
                ImageViewTouchBase imageViewTouchBase = ImageViewTouchBase.this;
                imageViewTouchBase.onZoomAnimationCompleted(imageViewTouchBase.getScale());
                ImageViewTouchBase.this.center(true, true);
            }
        });
    }

    @Override 
    public void dispose() {
        clear();
    }
}
