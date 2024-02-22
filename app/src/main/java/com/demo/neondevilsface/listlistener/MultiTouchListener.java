package com.demo.neondevilsface.listlistener;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.demo.neondevilsface.view.ResizableStickerView;


public class MultiTouchListener implements View.OnTouchListener {
    private static final int INVALID_POINTER_ID = -1;
    Bitmap bitmap;
    Context mContext;
    private float mPrevX;
    private float mPrevY;
    boolean bt = false;
    GestureDetector gd = null;
    public boolean isRotateEnabled = true;
    public boolean isRotationEnabled = false;
    public boolean isTranslateEnabled = true;
    private TouchCallbackListener listener = null;
    private int mActivePointerId = -1;
    public float maximumScale = 8.0f;
    public float minimumScale = 0.5f;
    private boolean isTransparencyCheckEnabled = true;
    private boolean disContinueHandleTransparecy = true;
    private ScaleGestureDetector mScaleGestureDetector = new ScaleGestureDetector(new ScaleGestureListener());

    
    public interface TouchCallbackListener {
        void onCenterPosX(View view);

        void onCenterPosXY(View view);

        void onCenterPosY(View view);

        void onOtherPos(View view);

        void onTouchCallback(View view);

        void onTouchMoveCallback(View view);

        void onTouchUpCallback(View view);
    }

    private static float adjustAngle(float f) {
        return f > 180.0f ? f - 360.0f : f < -180.0f ? f + 360.0f : f;
    }

    
    
    public class TransformInfo {
        public float deltaAngle;
        public float deltaScale;
        public float deltaX;
        public float deltaY;
        public float maximumScale;
        public float minimumScale;
        public float pivotX;
        public float pivotY;

        private TransformInfo() {
        }
    }

    
    private class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float mPivotX;
        private float mPivotY;
        private Vector2D mPrevSpanVector;

        private ScaleGestureListener() {
            this.mPrevSpanVector = new Vector2D();
        }

        @Override 
        public boolean onScaleBegin(View view, ScaleGestureDetector scaleGestureDetector) {
            this.mPivotX = scaleGestureDetector.getFocusX();
            this.mPivotY = scaleGestureDetector.getFocusY();
            this.mPrevSpanVector.set(scaleGestureDetector.getCurrentSpanVector());
            return true;
        }

        @Override 
        public boolean onScale(View view, ScaleGestureDetector scaleGestureDetector) {
            TransformInfo transformInfo = new TransformInfo();
            transformInfo.deltaAngle = MultiTouchListener.this.isRotateEnabled ? Vector2D.getAngle(this.mPrevSpanVector, scaleGestureDetector.getCurrentSpanVector()) : 0.0f;
            transformInfo.deltaX = MultiTouchListener.this.isTranslateEnabled ? scaleGestureDetector.getFocusX() - this.mPivotX : 0.0f;
            transformInfo.deltaY = MultiTouchListener.this.isTranslateEnabled ? scaleGestureDetector.getFocusY() - this.mPivotY : 0.0f;
            transformInfo.pivotX = this.mPivotX;
            transformInfo.pivotY = this.mPivotY;
            transformInfo.minimumScale = MultiTouchListener.this.minimumScale;
            transformInfo.maximumScale = MultiTouchListener.this.maximumScale;
            MultiTouchListener.this.move(view, transformInfo);
            return false;
        }
    }

    public MultiTouchListener setGestureListener(GestureDetector gestureDetector) {
        this.gd = gestureDetector;
        return this;
    }

    public MultiTouchListener setOnTouchCallbackListener(TouchCallbackListener touchCallbackListener) {
        this.listener = touchCallbackListener;
        return this;
    }

    public MultiTouchListener enableTransparencyCheck(boolean z) {
        this.isTransparencyCheckEnabled = z;
        return this;
    }

    public MultiTouchListener(Context context) {
        this.mContext = context;
    }

    public MultiTouchListener enableRotation(boolean z) {
        this.isRotationEnabled = z;
        return this;
    }

    public MultiTouchListener setMinScale(float f) {
        this.minimumScale = f;
        return this;
    }

    
    public void move(View view, TransformInfo transformInfo) {
        if (this.isRotationEnabled) {
            float adjustAngle = adjustAngle(view.getRotation() + transformInfo.deltaAngle);
            Log.e("testing", view.getRotation() + " " + transformInfo.deltaAngle + " " + adjustAngle);
            view.setRotation(adjustAngle);
        }
    }

    private void adjustTranslation(View view, float f, float f2) {
        boolean z;
        boolean z2 = false;
        float[] fArr = {f, f2};
        view.getMatrix().mapVectors(fArr);
        float translationY = view.getTranslationY() + fArr[1];
        view.setTranslationX(view.getTranslationX() + fArr[0]);
        view.setTranslationY(translationY);
        ResizableStickerView resizableStickerView = (ResizableStickerView) view;
        float mainWidth = resizableStickerView.getMainWidth();
        float mainHeight = resizableStickerView.getMainHeight();
        this.mContext.getResources();
        float width = view.getWidth() / 2;
        float height = view.getHeight() / 2;
        int y = (int) (view.getY() + height);
        float x = (int) (view.getX() + width);
        float f3 = mainWidth / 2.0f;
        float f4 = (int) (Resources.getSystem().getDisplayMetrics().density * 5.0f);
        if (x <= f3 - f4 || x >= f3 + f4) {
            z = false;
        } else {
            view.setX(f3 - width);
            z = true;
        }
        float f5 = y;
        float f6 = mainHeight / 2.0f;
        if (f5 > f6 - f4 && f5 < f4 + f6) {
            view.setY(f6 - height);
            z2 = true;
        }
        if (z && z2) {
            TouchCallbackListener touchCallbackListener = this.listener;
            if (touchCallbackListener != null) {
                touchCallbackListener.onCenterPosXY(view);
            }
        } else if (z) {
            TouchCallbackListener touchCallbackListener2 = this.listener;
            if (touchCallbackListener2 != null) {
                touchCallbackListener2.onCenterPosX(view);
            }
        } else if (z2) {
            TouchCallbackListener touchCallbackListener3 = this.listener;
            if (touchCallbackListener3 != null) {
                touchCallbackListener3.onCenterPosY(view);
            }
        } else {
            TouchCallbackListener touchCallbackListener4 = this.listener;
            if (touchCallbackListener4 != null) {
                touchCallbackListener4.onOtherPos(view);
            }
        }
        float rotation = view.getRotation();
        if (Math.abs(90.0f - Math.abs(rotation)) <= 5.0f) {
            rotation = rotation > 0.0f ? 90.0f : -90.0f;
        }
        if (Math.abs(0.0f - Math.abs(rotation)) <= 5.0f) {
            rotation = rotation > 0.0f ? 0.0f : -0.0f;
        }
        if (Math.abs(180.0f - Math.abs(rotation)) <= 5.0f) {
            rotation = rotation > 0.0f ? 180.0f : -180.0f;
        }
        view.setRotation(rotation);
    }

    private static void computeRenderOffset(View view, float f, float f2) {
        if (view.getPivotX() == f && view.getPivotY() == f2) {
            return;
        }
        float[] fArr = {0.0f, 0.0f};
        view.getMatrix().mapPoints(fArr);
        view.setPivotX(f);
        view.setPivotY(f2);
        float[] fArr2 = {0.0f, 0.0f};
        view.getMatrix().mapPoints(fArr2);
        float f3 = fArr2[1] - fArr[1];
        view.setTranslationX(view.getTranslationX() - (fArr2[0] - fArr[0]));
        view.setTranslationY(view.getTranslationY() - f3);
    }



    @Override 
    public boolean onTouch(View view, MotionEvent motionEvent) {
        RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
        this.mScaleGestureDetector.onTouchEvent(view, motionEvent);
        if (this.disContinueHandleTransparecy && this.isTransparencyCheckEnabled) {
            this.disContinueHandleTransparecy = false;
        }
        if (this.isTranslateEnabled) {
            int action = motionEvent.getAction();
            int actionMasked = motionEvent.getActionMasked() & action;
            if (actionMasked == 0) {
                if (relativeLayout != null) {
                    relativeLayout.requestDisallowInterceptTouchEvent(true);
                }
                TouchCallbackListener touchCallbackListener = this.listener;
                if (touchCallbackListener != null) {
                    touchCallbackListener.onTouchCallback(view);
                }
                view.bringToFront();
                if (view instanceof ResizableStickerView) {
                    ((ResizableStickerView) view).setBorderVisibility(true);
                }
                this.mPrevX = motionEvent.getX();
                this.mPrevY = motionEvent.getY();
                this.mActivePointerId = motionEvent.getPointerId(0);
            } else if (actionMasked == 1) {
                this.mActivePointerId = -1;
                this.disContinueHandleTransparecy = true;
                TouchCallbackListener touchCallbackListener2 = this.listener;
                if (touchCallbackListener2 != null) {
                    touchCallbackListener2.onTouchUpCallback(view);
                }
                float rotation = view.getRotation();
                if (Math.abs(90.0f - Math.abs(rotation)) <= 5.0f) {
                    rotation = rotation > 0.0f ? 90.0f : -90.0f;
                }
                if (Math.abs(0.0f - Math.abs(rotation)) <= 5.0f) {
                    rotation = rotation > 0.0f ? 0.0f : -0.0f;
                }
                if (Math.abs(180.0f - Math.abs(rotation)) <= 5.0f) {
                    rotation = rotation > 0.0f ? 180.0f : -180.0f;
                }
                view.setRotation(rotation);
                Log.i("testing", "Final Rotation : " + rotation);
            } else if (actionMasked == 2) {
                if (relativeLayout != null) {
                    relativeLayout.requestDisallowInterceptTouchEvent(true);
                }
                TouchCallbackListener touchCallbackListener3 = this.listener;
                if (touchCallbackListener3 != null) {
                    touchCallbackListener3.onTouchMoveCallback(view);
                }
                int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex != -1) {
                    float x = motionEvent.getX(findPointerIndex);
                    float y = motionEvent.getY(findPointerIndex);
                    if (!this.mScaleGestureDetector.isInProgress()) {
                        adjustTranslation(view, x - this.mPrevX, y - this.mPrevY);
                    }
                }
            } else if (actionMasked == 3) {
                this.mActivePointerId = -1;
            } else if (actionMasked == 6) {
                int i = (65280 & action) >> 8;
                if (motionEvent.getPointerId(i) == this.mActivePointerId) {
                    int i2 = i == 0 ? 1 : 0;
                    this.mPrevX = motionEvent.getX(i2);
                    this.mPrevY = motionEvent.getY(i2);
                    this.mActivePointerId = motionEvent.getPointerId(i2);
                }
            }
            return true;
        }
        return true;
    }
}
