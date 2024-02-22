package com.demo.neondevilsface.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;


public class ImageViewTouch extends ImageViewTouchBase {
    static final float SCROLL_DELTA_THRESHOLD = 1.0f;
    protected int mDoubleTapDirection;
    protected boolean mDoubleTapEnabled;
    private OnImageViewTouchDoubleTapListener mDoubleTapListener;
    private OnImageFlingListener mFlingListener;
    protected GestureDetector mGestureDetector;
    protected GestureDetector.OnGestureListener mGestureListener;
    protected ScaleGestureDetector mScaleDetector;
    protected boolean mScaleEnabled;
    protected float mScaleFactor;
    protected ScaleGestureDetector.OnScaleGestureListener mScaleListener;
    protected boolean mScrollEnabled;
    private OnImageViewTouchSingleTapListener mSingleTapListener;
    protected int mTouchSlop;
    private int rotateAngle;

    
    public interface OnImageFlingListener {
        void onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);
    }

    
    public interface OnImageViewTouchDoubleTapListener {
        void onDoubleTap();
    }

    
    public interface OnImageViewTouchSingleTapListener {
        void onSingleTapConfirmed();
    }

    @Override 
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public ImageViewTouch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDoubleTapEnabled = true;
        this.mScaleEnabled = true;
        this.mScrollEnabled = true;
    }

    
    @Override 
    public void init() {
        super.init();
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mGestureListener = getGestureListener();
        this.mScaleListener = getScaleListener();
        this.mScaleDetector = new ScaleGestureDetector(getContext(), this.mScaleListener);
        this.mGestureDetector = new GestureDetector(getContext(), this.mGestureListener, null, true);
        this.mDoubleTapDirection = 1;
    }

    public void setDoubleTapListener(OnImageViewTouchDoubleTapListener onImageViewTouchDoubleTapListener) {
        this.mDoubleTapListener = onImageViewTouchDoubleTapListener;
    }

    public void setSingleTapListener(OnImageViewTouchSingleTapListener onImageViewTouchSingleTapListener) {
        this.mSingleTapListener = onImageViewTouchSingleTapListener;
    }

    public void setFlingListener(OnImageFlingListener onImageFlingListener) {
        this.mFlingListener = onImageFlingListener;
    }

    public void setDoubleTapEnabled(boolean z) {
        this.mDoubleTapEnabled = z;
    }

    public void setScaleEnabled(boolean z) {
        this.mScaleEnabled = z;
        setDoubleTapEnabled(z);
    }

    public void setScrollEnabled(boolean z) {
        this.mScrollEnabled = z;
    }

    public boolean getDoubleTapEnabled() {
        return this.mDoubleTapEnabled;
    }

    protected GestureDetector.OnGestureListener getGestureListener() {
        return new GestureListener();
    }

    protected ScaleGestureDetector.OnScaleGestureListener getScaleListener() {
        return new ScaleListener();
    }

    
    @Override 
    public void _setImageDrawable(Drawable drawable, Matrix matrix, float f, float f2) {
        super._setImageDrawable(drawable, matrix, f, f2);
        this.mScaleFactor = getMaxScale() / 3.0f;
    }

    @Override 
    protected void onZoomAnimationCompleted(float f) {
        if (f < getMinScale()) {
            zoomTo(getMinScale(), 50.0f);
        }
    }

    protected float onDoubleTapPost(float f, float f2) {
        if (this.mDoubleTapDirection == 1) {
            float f3 = this.mScaleFactor;
            if ((2.0f * f3) + f <= f2) {
                return f + f3;
            }
            this.mDoubleTapDirection = -1;
            return f2;
        }
        this.mDoubleTapDirection = 1;
        return 1.0f;
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (!this.mScrollEnabled || motionEvent == null || motionEvent2 == null || motionEvent.getPointerCount() > 1 || motionEvent2.getPointerCount() > 1 || this.mScaleDetector.isInProgress() || getScale() == 1.0f) {
            return false;
        }
        this.mUserScaled = true;
        scrollBy(-f, -f2);
        invalidate();
        return true;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.mScrollEnabled) {
            OnImageFlingListener onImageFlingListener = this.mFlingListener;
            if (onImageFlingListener != null) {
                onImageFlingListener.onFling(motionEvent, motionEvent2, f, f2);
            }
            if (motionEvent.getPointerCount() > 1 || motionEvent2.getPointerCount() > 1 || this.mScaleDetector.isInProgress() || getScale() == 1.0f) {
                return false;
            }
            float x = motionEvent2.getX() - motionEvent.getX();
            float y = motionEvent2.getY() - motionEvent.getY();
            if (Math.abs(f) > 800.0f || Math.abs(f2) > 800.0f) {
                this.mUserScaled = true;
                scrollBy(x / 2.0f, y / 2.0f, 300.0d);
                invalidate();
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean canScroll(int i) {
        RectF bitmapRect = getBitmapRect();
        updateRect(bitmapRect, this.mScrollRect);
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        if (bitmapRect == null) {
            return false;
        }
        return (bitmapRect.right < ((float) rect.right) || i >= 0) ? ((double) Math.abs(bitmapRect.left - this.mScrollRect.left)) > 1.0d : Math.abs(bitmapRect.right - ((float) rect.right)) > 1.0f;
    }

    
    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        public GestureListener() {
        }

        @Override 
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            if (ImageViewTouch.this.mSingleTapListener != null) {
                ImageViewTouch.this.mSingleTapListener.onSingleTapConfirmed();
            }
            return super.onSingleTapConfirmed(motionEvent);
        }

        @Override 
        public boolean onDoubleTap(MotionEvent motionEvent) {
            Log.i(ImageViewTouchBase.LOG_TAG, "onDoubleTap. double tap enabled? " + ImageViewTouch.this.mDoubleTapEnabled);
            if (ImageViewTouch.this.mDoubleTapEnabled) {
                ImageViewTouch.this.mUserScaled = true;
                float scale = ImageViewTouch.this.getScale();
                ImageViewTouch imageViewTouch = ImageViewTouch.this;
                ImageViewTouch.this.zoomTo(Math.min(ImageViewTouch.this.getMaxScale(), Math.max(imageViewTouch.onDoubleTapPost(scale, imageViewTouch.getMaxScale()), ImageViewTouch.this.getMinScale())), motionEvent.getX(), motionEvent.getY(), 200.0f);
                ImageViewTouch.this.invalidate();
            }
            if (ImageViewTouch.this.mDoubleTapListener != null) {
                ImageViewTouch.this.mDoubleTapListener.onDoubleTap();
            }
            return super.onDoubleTap(motionEvent);
        }

        @Override 
        public void onLongPress(MotionEvent motionEvent) {
            if (!ImageViewTouch.this.isLongClickable() || ImageViewTouch.this.mScaleDetector.isInProgress()) {
                return;
            }
            ImageViewTouch.this.setPressed(true);
            ImageViewTouch.this.performLongClick();
        }

        @Override 
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return ImageViewTouch.this.onScroll(motionEvent, motionEvent2, f, f2);
        }

        @Override 
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return ImageViewTouch.this.onFling(motionEvent, motionEvent2, f, f2);
        }
    }

    
    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        protected boolean mScaled = false;

        public ScaleListener() {
        }

        @Override 
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float currentSpan = scaleGestureDetector.getCurrentSpan() - scaleGestureDetector.getPreviousSpan();
            float scale = ImageViewTouch.this.getScale() * scaleGestureDetector.getScaleFactor();
            if (ImageViewTouch.this.mScaleEnabled) {
                boolean z = this.mScaled;
                if (z && currentSpan != 0.0f) {
                    ImageViewTouch.this.mUserScaled = true;
                    ImageViewTouch.this.zoomTo(Math.min(ImageViewTouch.this.getMaxScale(), Math.max(scale, ImageViewTouch.this.getMinScale() - 0.1f)), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
                    ImageViewTouch.this.mDoubleTapDirection = 1;
                    ImageViewTouch.this.invalidate();
                    return true;
                } else if (!z) {
                    this.mScaled = true;
                }
            }
            return true;
        }
    }

    public void resetImage() {
        zoomTo(Math.min(getMaxScale(), Math.max(getScale(), getMinScale())), 0.0f, 0.0f, 200.0f);
        invalidate();
    }

    public void rotateImage(int i) {
        this.rotateAngle = i;
        invalidate();
    }

    public synchronized int getRotateAngle() {
        return this.rotateAngle;
    }
}
