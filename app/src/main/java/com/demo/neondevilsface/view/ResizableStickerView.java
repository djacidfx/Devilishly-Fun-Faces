package com.demo.neondevilsface.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.neondevilsface.R;
import com.demo.neondevilsface.activity.NeonDevilEditActivity;
import com.demo.neondevilsface.listlistener.MultiTouchListener;
import com.demo.neondevilsface.model.ComponentInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;


public class ResizableStickerView extends RelativeLayout implements MultiTouchListener.TouchCallbackListener {
    private static final int SELF_SIZE_DP = 30;
    public static final String TAG = "ResizableStickerView";
    private int alphaProg;
    double angle;
    int baseh;
    int basew;
    int basex;
    int basey;
    private ImageView border_iv;
    private Bitmap btmp;
    float cX;
    float cY;
    private double centerX;
    private double centerY;
    private String colorType;
    private Context context;
    double dAngle;
    private ImageView delete_iv;
    private String drawableId;
    private String field_four;
    private int field_one;
    private String field_two;
    private ImageView flip_iv;
    boolean ft;
    private int he;
    float heightMain;
    private int hueProg;
    private int imgAlpha;
    private int imgColor;
    private boolean isBorderVisible;
    private boolean isColorFilterEnable;
    public boolean isHandleTransparency;
    public boolean isMultiTouchEnabled;
    private boolean isSticker;
    private boolean isdeleteEnable;
    private int leftMargin;
    private TouchEventListener listener;
    String lockStatus;
    private OnTouchListener mTouchListener;
    private OnTouchListener mTouchListener1;
    public ImageView main_iv;
    private int margin;
    private int margin1;
    int margl;
    int margt;
    double onTouchAngle;
    private OnTouchListener rTouchListener;
    private Uri resUri;
    private ImageView rotate_iv;
    private float rotation;
    private int s;
    private int s2;
    Animation scale;
    private int scaleRotateProg;
    private ImageView scale_iv;
    private double scale_orgHeight;
    private double scale_orgWidth;
    private float scale_orgX;
    private float scale_orgY;
    int screenHeight;
    int screenWidth;
    private String stkr_path;
    double tAngle;
    private float this_orgX;
    private float this_orgY;
    private int topMargin;
    double vAngle;
    private int wi;
    float widthMain;
    private int xRotateProg;
    private int yRotateProg;
    private float yRotation;
    private int zRotateProg;
    Animation zoomInScale;
    Animation zoomOutScale;


    public interface TouchEventListener {
        byte[] getResBytes(Context context, String str);

        void onCenterX(View view);

        void onCenterXY(View view);

        void onCenterY(View view);

        void onDelete();

        void onDoubleTap();

        void onEdit(View view, Uri uri);

        void onOtherXY(View view);

        void onRotateDown(View view);

        void onRotateMove(View view);

        void onRotateUp(View view);

        void onScaleDown(View view);

        void onScaleMove(View view);

        void onScaleUp(View view);

        void onTouchDown(View view);

        void onTouchMove(View view);

        void onTouchUp(View view);
    }

    public void optimize(float f, float f2) {
    }

    public void setFilterImage(Bitmap bitmap) {
        Glide.with(this.context).load(bitmap).into(this.main_iv);
    }

    public ResizableStickerView setOnTouchCallbackListener(NeonDevilEditActivity neonDevilEditActivity) {
        this.listener = neonDevilEditActivity;
        return this;
    }

    public ResizableStickerView(Context context) {
        super(context);
        this.alphaProg = 0;
        this.angle = 0.0d;
        this.btmp = null;
        this.cX = 0.0f;
        this.cY = 0.0f;
        this.colorType = "colored";
        this.dAngle = 0.0d;
        this.field_four = "";
        this.field_one = 0;
        this.field_two = "0,0";
        this.ft = true;
        this.heightMain = 0.0f;
        this.hueProg = 2;
        this.imgAlpha = 100;
        this.imgColor = 0;
        this.isBorderVisible = false;
        this.isColorFilterEnable = false;
        this.isHandleTransparency = true;
        this.isMultiTouchEnabled = true;
        this.isSticker = true;
        this.isdeleteEnable = true;
        this.leftMargin = 0;
        this.listener = null;
        this.lockStatus = "UNLOCKED";
        this.mTouchListener = new OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int i;

                int action = motionEvent.getAction();
                if (action == 0) {
                    ResizableStickerView resizableStickerView5 = ResizableStickerView.this;
                    resizableStickerView5.this_orgX = resizableStickerView5.getX();
                    resizableStickerView5.this_orgY = resizableStickerView5.getY();
                    ResizableStickerView.this.scale_orgX = motionEvent.getRawX();
                    ResizableStickerView.this.scale_orgY = motionEvent.getRawY();
                    ResizableStickerView.this.scale_orgWidth = resizableStickerView5.getLayoutParams().width;
                    ResizableStickerView.this.scale_orgHeight = resizableStickerView5.getLayoutParams().height;
                    ResizableStickerView.this.centerX = ((View) resizableStickerView5.getParent()).getX() + ResizableStickerView.this.getX() + (ResizableStickerView.this.getWidth() / 2.0f);
                    int identifier = ResizableStickerView.this.getResources().getIdentifier("status_bar_height", "dimen", "android");
                    int dimensionPixelSize = identifier > 0 ? ResizableStickerView.this.getResources().getDimensionPixelSize(identifier) : 0;
                    ResizableStickerView.this.centerY = ((View) resizableStickerView5.getParent()).getY() + ResizableStickerView.this.getY() + dimensionPixelSize + (ResizableStickerView.this.getHeight() / 2.0f);
                    return true;
                } else if (action == 1) {
                    ResizableStickerView resizableStickerView7 = ResizableStickerView.this;
                    resizableStickerView7.wi = resizableStickerView7.getLayoutParams().width;
                    ResizableStickerView resizableStickerView8 = ResizableStickerView.this;
                    resizableStickerView8.he = resizableStickerView8.getLayoutParams().height;
                    return true;
                } else if (action != 2) {
                    return true;
                } else {
                    double abs = (Math.abs(Math.atan2(motionEvent.getRawY() - ResizableStickerView.this.scale_orgY, motionEvent.getRawX() - ResizableStickerView.this.scale_orgX) - Math.atan2(ResizableStickerView.this.scale_orgY - ResizableStickerView.this.centerY, ResizableStickerView.this.scale_orgX - ResizableStickerView.this.centerX)) * 180.0d) / 3.141592653589793d;
                    Log.v(ResizableStickerView.TAG, "angle_diff: " + abs);
                    ResizableStickerView resizableStickerView9 = ResizableStickerView.this;
                    double length = resizableStickerView9.getLength(resizableStickerView9.centerX, ResizableStickerView.this.centerY, (double) ResizableStickerView.this.scale_orgX, (double) ResizableStickerView.this.scale_orgY);
                    ResizableStickerView resizableStickerView10 = ResizableStickerView.this;
                    double length2 = resizableStickerView10.getLength(resizableStickerView10.centerX, ResizableStickerView.this.centerY, motionEvent.getRawX(), motionEvent.getRawY());
                    ResizableStickerView resizableStickerView11 = ResizableStickerView.this;
                    int dpToPx = resizableStickerView11.dpToPx(resizableStickerView11.getContext(), 30);




                    if (length2 > length && (abs < 25.0d || Math.abs(abs - 180.0d) < 25.0d)) {
                        double round = Math.round(Math.max(Math.abs(motionEvent.getRawX() - ResizableStickerView.this.scale_orgX), Math.abs(motionEvent.getRawY() - ResizableStickerView.this.scale_orgY)));
                        ResizableStickerView.this.getLayoutParams().width = (int) ( ResizableStickerView.this.getLayoutParams().width + round);
                        ResizableStickerView.this.getLayoutParams().height = (int) ( ResizableStickerView.this.getLayoutParams().height + round);
                    } else if (length2 < length && ((abs < 25.0d || Math.abs(abs - 180.0d) < 25.0d) && ResizableStickerView.this.getLayoutParams().width > (i = dpToPx / 2) && ResizableStickerView.this.getLayoutParams().height > i)) {
                        double round2 = Math.round(Math.max(Math.abs(motionEvent.getRawX() - ResizableStickerView.this.scale_orgX), Math.abs(motionEvent.getRawY() - ResizableStickerView.this.scale_orgY)));
                        ResizableStickerView.this.getLayoutParams().width = (int) ( ResizableStickerView.this.getLayoutParams().width - round2);
                        ResizableStickerView.this.getLayoutParams().height = (int) ( ResizableStickerView.this.getLayoutParams().height - round2);
                    }
                    ResizableStickerView.this.scale_orgX = motionEvent.getRawX();
                    ResizableStickerView.this.scale_orgY = motionEvent.getRawY();
                    ResizableStickerView.this.postInvalidate();
                    ResizableStickerView.this.requestLayout();
                    return true;
                }
            }
        };
        this.mTouchListener1 = new OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ResizableStickerView resizableStickerView = (ResizableStickerView) view.getParent();
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                LayoutParams layoutParams = (LayoutParams) ResizableStickerView.this.getLayoutParams();
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (resizableStickerView != null) {
                        resizableStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleDown(ResizableStickerView.this);
                    }
                    ResizableStickerView.this.invalidate();
                    ResizableStickerView.this.basex = rawX;
                    ResizableStickerView.this.basey = rawY;
                    ResizableStickerView resizableStickerView2 = ResizableStickerView.this;
                    resizableStickerView2.basew = resizableStickerView2.getWidth();
                    ResizableStickerView resizableStickerView3 = ResizableStickerView.this;
                    resizableStickerView3.baseh = resizableStickerView3.getHeight();
                    ResizableStickerView.this.getLocationOnScreen(new int[2]);
                    ResizableStickerView.this.margl = layoutParams.leftMargin;
                    ResizableStickerView.this.margt = layoutParams.topMargin;
                } else if (action == 1) {
                    ResizableStickerView resizableStickerView4 = ResizableStickerView.this;
                    resizableStickerView4.wi = resizableStickerView4.getLayoutParams().width;
                    ResizableStickerView resizableStickerView5 = ResizableStickerView.this;
                    resizableStickerView5.he = resizableStickerView5.getLayoutParams().height;
                    ResizableStickerView resizableStickerView6 = ResizableStickerView.this;
                    resizableStickerView6.leftMargin = ((LayoutParams) resizableStickerView6.getLayoutParams()).leftMargin;
                    ResizableStickerView resizableStickerView7 = ResizableStickerView.this;
                    resizableStickerView7.topMargin = ((LayoutParams) resizableStickerView7.getLayoutParams()).topMargin;
                    ResizableStickerView resizableStickerView8 = ResizableStickerView.this;
                    resizableStickerView8.field_two = String.valueOf(ResizableStickerView.this.leftMargin) + "," + String.valueOf(ResizableStickerView.this.topMargin);
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleUp(ResizableStickerView.this);
                    }
                } else if (action == 2) {
                    if (resizableStickerView != null) {
                        resizableStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleMove(ResizableStickerView.this);
                    }
                    float degrees = (float) Math.toDegrees(Math.atan2(rawY - ResizableStickerView.this.basey, rawX - ResizableStickerView.this.basex));
                    if (degrees < 0.0f) {
                        degrees += 360.0f;
                    }
                    int i = rawX - ResizableStickerView.this.basex;
                    int i2 = rawY - ResizableStickerView.this.basey;
                    int i3 = i2 * i2;
                    int sqrt = (int) (Math.sqrt((i * i) + i3) * Math.cos(Math.toRadians(degrees - ResizableStickerView.this.getRotation())));
                    int sqrt2 = (int) (Math.sqrt((sqrt * sqrt) + i3) * Math.sin(Math.toRadians(degrees - ResizableStickerView.this.getRotation())));
                    int i4 = (sqrt * 2) + ResizableStickerView.this.basew;
                    int i5 = (sqrt2 * 2) + ResizableStickerView.this.baseh;
                    if (i4 > ResizableStickerView.this.s2) {
                        layoutParams.width = i4;
                        layoutParams.leftMargin = ResizableStickerView.this.margl - sqrt;
                    }
                    if (i5 > ResizableStickerView.this.s2) {
                        layoutParams.height = i5;
                        layoutParams.topMargin = ResizableStickerView.this.margt - sqrt2;
                    }
                    ResizableStickerView.this.setLayoutParams(layoutParams);
                    ResizableStickerView.this.performLongClick();
                }
                return true;
            }
        };
        this.onTouchAngle = 0.0d;
        this.rTouchListener = new OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ResizableStickerView resizableStickerView = (ResizableStickerView) view.getParent();
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (resizableStickerView != null) {
                        resizableStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onRotateDown(ResizableStickerView.this);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    ResizableStickerView.this.cX = rect.exactCenterX();
                    ResizableStickerView.this.cY = rect.exactCenterY();
                    ResizableStickerView.this.vAngle = ((View) view.getParent()).getRotation();
                    ResizableStickerView resizableStickerView2 = ResizableStickerView.this;
                    resizableStickerView2.tAngle = (Math.atan2(resizableStickerView2.cY - motionEvent.getRawY(), ResizableStickerView.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                    ResizableStickerView resizableStickerView3 = ResizableStickerView.this;
                    resizableStickerView3.dAngle = resizableStickerView3.vAngle - ResizableStickerView.this.tAngle;
                } else if (action != 1) {
                    if (action == 2) {
                        if (resizableStickerView != null) {
                            resizableStickerView.requestDisallowInterceptTouchEvent(true);
                        }
                        if (ResizableStickerView.this.listener != null) {
                            ResizableStickerView.this.listener.onRotateMove(ResizableStickerView.this);
                        }
                        ResizableStickerView resizableStickerView4 = ResizableStickerView.this;
                        resizableStickerView4.angle = (Math.atan2(resizableStickerView4.cY - motionEvent.getRawY(), ResizableStickerView.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                        float f = (float) (ResizableStickerView.this.angle + ResizableStickerView.this.dAngle);
                        ((View) view.getParent()).setRotation(f);
                        ((View) view.getParent()).invalidate();
                        ((View) view.getParent()).requestLayout();
                        if (Math.abs(90.0f - Math.abs(f)) <= 5.0f) {
                            f = f > 0.0f ? 90.0f : -90.0f;
                        }
                        if (Math.abs(0.0f - Math.abs(f)) <= 5.0f) {
                            f = f > 0.0f ? 0.0f : -0.0f;
                        }
                        if (Math.abs(180.0f - Math.abs(f)) <= 5.0f) {
                            f = f > 0.0f ? 180.0f : -180.0f;
                        }
                        ((View) view.getParent()).setRotation(f);
                    }
                } else if (ResizableStickerView.this.listener != null) {
                    ResizableStickerView.this.listener.onRotateUp(ResizableStickerView.this);
                }
                return true;
            }
        };
        this.resUri = null;
        this.scaleRotateProg = 0;
        this.scale_orgHeight = -1.0d;
        this.scale_orgWidth = -1.0d;
        this.scale_orgX = -1.0f;
        this.scale_orgY = -1.0f;
        this.screenHeight = 300;
        this.screenWidth = 300;
        this.stkr_path = "";
        this.tAngle = 0.0d;
        this.this_orgX = -1.0f;
        this.this_orgY = -1.0f;
        this.topMargin = 0;
        this.vAngle = 0.0d;
        this.widthMain = 0.0f;
        this.xRotateProg = 0;
        this.yRotateProg = 0;
        this.yRotation = 0.0f;
        this.zRotateProg = 0;
        init(context);
    }

    public ResizableStickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.alphaProg = 0;
        this.angle = 0.0d;
        this.btmp = null;
        this.cX = 0.0f;
        this.cY = 0.0f;
        this.colorType = "colored";
        this.dAngle = 0.0d;
        this.field_four = "";
        this.field_one = 0;
        this.field_two = "0,0";
        this.ft = true;
        this.heightMain = 0.0f;
        this.hueProg = 2;
        this.imgAlpha = 100;
        this.imgColor = 0;
        this.isBorderVisible = false;
        this.isColorFilterEnable = false;
        this.isHandleTransparency = true;
        this.isMultiTouchEnabled = true;
        this.isSticker = true;
        this.isdeleteEnable = true;
        this.leftMargin = 0;
        this.listener = null;
        this.lockStatus = "UNLOCKED";
        this.mTouchListener = new OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int i;

                int action = motionEvent.getAction();
                if (action == 0) {
                    ResizableStickerView resizableStickerView5 = ResizableStickerView.this;
                    resizableStickerView5.this_orgX = resizableStickerView5.getX();
                    resizableStickerView5.this_orgY = resizableStickerView5.getY();
                    ResizableStickerView.this.scale_orgX = motionEvent.getRawX();
                    ResizableStickerView.this.scale_orgY = motionEvent.getRawY();
                    ResizableStickerView.this.scale_orgWidth = resizableStickerView5.getLayoutParams().width;
                    ResizableStickerView.this.scale_orgHeight = resizableStickerView5.getLayoutParams().height;
                    ResizableStickerView.this.centerX = ((View) resizableStickerView5.getParent()).getX() + ResizableStickerView.this.getX() + (ResizableStickerView.this.getWidth() / 2.0f);
                    int identifier = ResizableStickerView.this.getResources().getIdentifier("status_bar_height", "dimen", "android");
                    int dimensionPixelSize = identifier > 0 ? ResizableStickerView.this.getResources().getDimensionPixelSize(identifier) : 0;
                    ResizableStickerView.this.centerY = ((View) resizableStickerView5.getParent()).getY() + ResizableStickerView.this.getY() + dimensionPixelSize + (ResizableStickerView.this.getHeight() / 2.0f);
                    return true;
                } else if (action == 1) {
                    ResizableStickerView resizableStickerView7 = ResizableStickerView.this;
                    resizableStickerView7.wi = resizableStickerView7.getLayoutParams().width;
                    ResizableStickerView resizableStickerView8 = ResizableStickerView.this;
                    resizableStickerView8.he = resizableStickerView8.getLayoutParams().height;
                    return true;
                } else if (action != 2) {
                    return true;
                } else {
                    double abs = (Math.abs(Math.atan2(motionEvent.getRawY() - ResizableStickerView.this.scale_orgY, motionEvent.getRawX() - ResizableStickerView.this.scale_orgX) - Math.atan2(ResizableStickerView.this.scale_orgY - ResizableStickerView.this.centerY, ResizableStickerView.this.scale_orgX - ResizableStickerView.this.centerX)) * 180.0d) / 3.141592653589793d;
                    Log.v(ResizableStickerView.TAG, "angle_diff: " + abs);
                    ResizableStickerView resizableStickerView9 = ResizableStickerView.this;
                    double length = resizableStickerView9.getLength(resizableStickerView9.centerX, ResizableStickerView.this.centerY, (double) ResizableStickerView.this.scale_orgX, (double) ResizableStickerView.this.scale_orgY);
                    ResizableStickerView resizableStickerView10 = ResizableStickerView.this;
                    double length2 = resizableStickerView10.getLength(resizableStickerView10.centerX, ResizableStickerView.this.centerY, motionEvent.getRawX(), motionEvent.getRawY());
                    ResizableStickerView resizableStickerView11 = ResizableStickerView.this;
                    int dpToPx = resizableStickerView11.dpToPx(resizableStickerView11.getContext(), 30);


                    if (length2 > length && (abs < 25.0d || Math.abs(abs - 180.0d) < 25.0d)) {
                        double round = Math.round(Math.max(Math.abs(motionEvent.getRawX() - ResizableStickerView.this.scale_orgX), Math.abs(motionEvent.getRawY() - ResizableStickerView.this.scale_orgY)));
                        ResizableStickerView.this.getLayoutParams().width = (int) ( ResizableStickerView.this.getLayoutParams().width + round);
                        ResizableStickerView.this.getLayoutParams().height = (int) ( ResizableStickerView.this.getLayoutParams().height + round);
                    } else if (length2 < length && ((abs < 25.0d || Math.abs(abs - 180.0d) < 25.0d) && ResizableStickerView.this.getLayoutParams().width > (i = dpToPx / 2) && ResizableStickerView.this.getLayoutParams().height > i)) {
                        double round2 = Math.round(Math.max(Math.abs(motionEvent.getRawX() - ResizableStickerView.this.scale_orgX), Math.abs(motionEvent.getRawY() - ResizableStickerView.this.scale_orgY)));
                        ResizableStickerView.this.getLayoutParams().width = (int) ( ResizableStickerView.this.getLayoutParams().width - round2);
                        ResizableStickerView.this.getLayoutParams().height = (int) ( ResizableStickerView.this.getLayoutParams().height - round2);
                    }
                    ResizableStickerView.this.scale_orgX = motionEvent.getRawX();
                    ResizableStickerView.this.scale_orgY = motionEvent.getRawY();
                    ResizableStickerView.this.postInvalidate();
                    ResizableStickerView.this.requestLayout();
                    return true;
                }
            }
        };
        this.mTouchListener1 = new OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ResizableStickerView resizableStickerView = (ResizableStickerView) view.getParent();
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                LayoutParams layoutParams = (LayoutParams) ResizableStickerView.this.getLayoutParams();
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (resizableStickerView != null) {
                        resizableStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleDown(ResizableStickerView.this);
                    }
                    ResizableStickerView.this.invalidate();
                    ResizableStickerView.this.basex = rawX;
                    ResizableStickerView.this.basey = rawY;
                    ResizableStickerView resizableStickerView2 = ResizableStickerView.this;
                    resizableStickerView2.basew = resizableStickerView2.getWidth();
                    ResizableStickerView resizableStickerView3 = ResizableStickerView.this;
                    resizableStickerView3.baseh = resizableStickerView3.getHeight();
                    ResizableStickerView.this.getLocationOnScreen(new int[2]);
                    ResizableStickerView.this.margl = layoutParams.leftMargin;
                    ResizableStickerView.this.margt = layoutParams.topMargin;
                } else if (action == 1) {
                    ResizableStickerView resizableStickerView4 = ResizableStickerView.this;
                    resizableStickerView4.wi = resizableStickerView4.getLayoutParams().width;
                    ResizableStickerView resizableStickerView5 = ResizableStickerView.this;
                    resizableStickerView5.he = resizableStickerView5.getLayoutParams().height;
                    ResizableStickerView resizableStickerView6 = ResizableStickerView.this;
                    resizableStickerView6.leftMargin = ((LayoutParams) resizableStickerView6.getLayoutParams()).leftMargin;
                    ResizableStickerView resizableStickerView7 = ResizableStickerView.this;
                    resizableStickerView7.topMargin = ((LayoutParams) resizableStickerView7.getLayoutParams()).topMargin;
                    ResizableStickerView resizableStickerView8 = ResizableStickerView.this;
                    resizableStickerView8.field_two = String.valueOf(ResizableStickerView.this.leftMargin) + "," + String.valueOf(ResizableStickerView.this.topMargin);
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleUp(ResizableStickerView.this);
                    }
                } else if (action == 2) {
                    if (resizableStickerView != null) {
                        resizableStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleMove(ResizableStickerView.this);
                    }
                    float degrees = (float) Math.toDegrees(Math.atan2(rawY - ResizableStickerView.this.basey, rawX - ResizableStickerView.this.basex));
                    if (degrees < 0.0f) {
                        degrees += 360.0f;
                    }
                    int i = rawX - ResizableStickerView.this.basex;
                    int i2 = rawY - ResizableStickerView.this.basey;
                    int i3 = i2 * i2;
                    int sqrt = (int) (Math.sqrt((i * i) + i3) * Math.cos(Math.toRadians(degrees - ResizableStickerView.this.getRotation())));
                    int sqrt2 = (int) (Math.sqrt((sqrt * sqrt) + i3) * Math.sin(Math.toRadians(degrees - ResizableStickerView.this.getRotation())));
                    int i4 = (sqrt * 2) + ResizableStickerView.this.basew;
                    int i5 = (sqrt2 * 2) + ResizableStickerView.this.baseh;
                    if (i4 > ResizableStickerView.this.s2) {
                        layoutParams.width = i4;
                        layoutParams.leftMargin = ResizableStickerView.this.margl - sqrt;
                    }
                    if (i5 > ResizableStickerView.this.s2) {
                        layoutParams.height = i5;
                        layoutParams.topMargin = ResizableStickerView.this.margt - sqrt2;
                    }
                    ResizableStickerView.this.setLayoutParams(layoutParams);
                    ResizableStickerView.this.performLongClick();
                }
                return true;
            }
        };
        this.onTouchAngle = 0.0d;
        this.rTouchListener = new OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ResizableStickerView resizableStickerView = (ResizableStickerView) view.getParent();
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (resizableStickerView != null) {
                        resizableStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onRotateDown(ResizableStickerView.this);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    ResizableStickerView.this.cX = rect.exactCenterX();
                    ResizableStickerView.this.cY = rect.exactCenterY();
                    ResizableStickerView.this.vAngle = ((View) view.getParent()).getRotation();
                    ResizableStickerView resizableStickerView2 = ResizableStickerView.this;
                    resizableStickerView2.tAngle = (Math.atan2(resizableStickerView2.cY - motionEvent.getRawY(), ResizableStickerView.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                    ResizableStickerView resizableStickerView3 = ResizableStickerView.this;
                    resizableStickerView3.dAngle = resizableStickerView3.vAngle - ResizableStickerView.this.tAngle;
                } else if (action != 1) {
                    if (action == 2) {
                        if (resizableStickerView != null) {
                            resizableStickerView.requestDisallowInterceptTouchEvent(true);
                        }
                        if (ResizableStickerView.this.listener != null) {
                            ResizableStickerView.this.listener.onRotateMove(ResizableStickerView.this);
                        }
                        ResizableStickerView resizableStickerView4 = ResizableStickerView.this;
                        resizableStickerView4.angle = (Math.atan2(resizableStickerView4.cY - motionEvent.getRawY(), ResizableStickerView.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                        float f = (float) (ResizableStickerView.this.angle + ResizableStickerView.this.dAngle);
                        ((View) view.getParent()).setRotation(f);
                        ((View) view.getParent()).invalidate();
                        ((View) view.getParent()).requestLayout();
                        if (Math.abs(90.0f - Math.abs(f)) <= 5.0f) {
                            f = f > 0.0f ? 90.0f : -90.0f;
                        }
                        if (Math.abs(0.0f - Math.abs(f)) <= 5.0f) {
                            f = f > 0.0f ? 0.0f : -0.0f;
                        }
                        if (Math.abs(180.0f - Math.abs(f)) <= 5.0f) {
                            f = f > 0.0f ? 180.0f : -180.0f;
                        }
                        ((View) view.getParent()).setRotation(f);
                    }
                } else if (ResizableStickerView.this.listener != null) {
                    ResizableStickerView.this.listener.onRotateUp(ResizableStickerView.this);
                }
                return true;
            }
        };
        this.resUri = null;
        this.scaleRotateProg = 0;
        this.scale_orgHeight = -1.0d;
        this.scale_orgWidth = -1.0d;
        this.scale_orgX = -1.0f;
        this.scale_orgY = -1.0f;
        this.screenHeight = 300;
        this.screenWidth = 300;
        this.stkr_path = "";
        this.tAngle = 0.0d;
        this.this_orgX = -1.0f;
        this.this_orgY = -1.0f;
        this.topMargin = 0;
        this.vAngle = 0.0d;
        this.widthMain = 0.0f;
        this.xRotateProg = 0;
        this.yRotateProg = 0;
        this.yRotation = 0.0f;
        this.zRotateProg = 0;
        init(context);
    }

    public ResizableStickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.alphaProg = 0;
        this.angle = 0.0d;
        this.btmp = null;
        this.cX = 0.0f;
        this.cY = 0.0f;
        this.colorType = "colored";
        this.dAngle = 0.0d;
        this.field_four = "";
        this.field_one = 0;
        this.field_two = "0,0";
        this.ft = true;
        this.heightMain = 0.0f;
        this.hueProg = 2;
        this.imgAlpha = 100;
        this.imgColor = 0;
        this.isBorderVisible = false;
        this.isColorFilterEnable = false;
        this.isHandleTransparency = true;
        this.isMultiTouchEnabled = true;
        this.isSticker = true;
        this.isdeleteEnable = true;
        this.leftMargin = 0;
        this.listener = null;
        this.lockStatus = "UNLOCKED";
        this.mTouchListener = new OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int i2;
                ViewGroup.LayoutParams layoutParams;
                int action = motionEvent.getAction();
                if (action == 0) {
                    ResizableStickerView resizableStickerView5 = ResizableStickerView.this;
                    resizableStickerView5.this_orgX = resizableStickerView5.getX();
                    resizableStickerView5.this_orgY = resizableStickerView5.getY();
                    ResizableStickerView.this.scale_orgX = motionEvent.getRawX();
                    ResizableStickerView.this.scale_orgY = motionEvent.getRawY();
                    ResizableStickerView.this.scale_orgWidth = resizableStickerView5.getLayoutParams().width;
                    ResizableStickerView.this.scale_orgHeight = resizableStickerView5.getLayoutParams().height;
                    ResizableStickerView.this.centerX = ((View) resizableStickerView5.getParent()).getX() + ResizableStickerView.this.getX() + (ResizableStickerView.this.getWidth() / 2.0f);
                    int identifier = ResizableStickerView.this.getResources().getIdentifier("status_bar_height", "dimen", "android");
                    int dimensionPixelSize = identifier > 0 ? ResizableStickerView.this.getResources().getDimensionPixelSize(identifier) : 0;
                    ResizableStickerView.this.centerY = ((View) resizableStickerView5.getParent()).getY() + ResizableStickerView.this.getY() + dimensionPixelSize + (ResizableStickerView.this.getHeight() / 2.0f);
                    return true;
                } else if (action == 1) {
                    ResizableStickerView resizableStickerView7 = ResizableStickerView.this;
                    resizableStickerView7.wi = resizableStickerView7.getLayoutParams().width;
                    ResizableStickerView resizableStickerView8 = ResizableStickerView.this;
                    resizableStickerView8.he = resizableStickerView8.getLayoutParams().height;
                    return true;
                } else if (action != 2) {
                    return true;
                } else {
                    double abs = (Math.abs(Math.atan2(motionEvent.getRawY() - ResizableStickerView.this.scale_orgY, motionEvent.getRawX() - ResizableStickerView.this.scale_orgX) - Math.atan2(ResizableStickerView.this.scale_orgY - ResizableStickerView.this.centerY, ResizableStickerView.this.scale_orgX - ResizableStickerView.this.centerX)) * 180.0d) / 3.141592653589793d;
                    Log.v(ResizableStickerView.TAG, "angle_diff: " + abs);
                    ResizableStickerView resizableStickerView9 = ResizableStickerView.this;
                    double length = resizableStickerView9.getLength(resizableStickerView9.centerX, ResizableStickerView.this.centerY, (double) ResizableStickerView.this.scale_orgX, (double) ResizableStickerView.this.scale_orgY);
                    ResizableStickerView resizableStickerView10 = ResizableStickerView.this;
                    double length2 = resizableStickerView10.getLength(resizableStickerView10.centerX, ResizableStickerView.this.centerY, motionEvent.getRawX(), motionEvent.getRawY());
                    ResizableStickerView resizableStickerView11 = ResizableStickerView.this;
                    int dpToPx = resizableStickerView11.dpToPx(resizableStickerView11.getContext(), 30);




                    if (length2 > length && (abs < 25.0d || Math.abs(abs - 180.0d) < 25.0d)) {
                        double round = Math.round(Math.max(Math.abs(motionEvent.getRawX() - ResizableStickerView.this.scale_orgX), Math.abs(motionEvent.getRawY() - ResizableStickerView.this.scale_orgY)));
                        ResizableStickerView.this.getLayoutParams().width = (int) ( ResizableStickerView.this.getLayoutParams().width + round);
                        ResizableStickerView.this.getLayoutParams().height = (int) ( ResizableStickerView.this.getLayoutParams().height + round);
                    } else if (length2 < length && ((abs < 25.0d || Math.abs(abs - 180.0d) < 25.0d) && ResizableStickerView.this.getLayoutParams().width > (i2 = dpToPx / 2) && ResizableStickerView.this.getLayoutParams().height > i2)) {
                        double round2 = Math.round(Math.max(Math.abs(motionEvent.getRawX() - ResizableStickerView.this.scale_orgX), Math.abs(motionEvent.getRawY() - ResizableStickerView.this.scale_orgY)));
                        ResizableStickerView.this.getLayoutParams().width = (int) ( ResizableStickerView.this.getLayoutParams().width - round2);
                        ResizableStickerView.this.getLayoutParams().height = (int) ( ResizableStickerView.this.getLayoutParams().height - round2);
                    }
                    ResizableStickerView.this.scale_orgX = motionEvent.getRawX();
                    ResizableStickerView.this.scale_orgY = motionEvent.getRawY();
                    ResizableStickerView.this.postInvalidate();
                    ResizableStickerView.this.requestLayout();
                    return true;
                }
            }
        };
        this.mTouchListener1 = new OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ResizableStickerView resizableStickerView = (ResizableStickerView) view.getParent();
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                LayoutParams layoutParams = (LayoutParams) ResizableStickerView.this.getLayoutParams();
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (resizableStickerView != null) {
                        resizableStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleDown(ResizableStickerView.this);
                    }
                    ResizableStickerView.this.invalidate();
                    ResizableStickerView.this.basex = rawX;
                    ResizableStickerView.this.basey = rawY;
                    ResizableStickerView resizableStickerView2 = ResizableStickerView.this;
                    resizableStickerView2.basew = resizableStickerView2.getWidth();
                    ResizableStickerView resizableStickerView3 = ResizableStickerView.this;
                    resizableStickerView3.baseh = resizableStickerView3.getHeight();
                    ResizableStickerView.this.getLocationOnScreen(new int[2]);
                    ResizableStickerView.this.margl = layoutParams.leftMargin;
                    ResizableStickerView.this.margt = layoutParams.topMargin;
                } else if (action == 1) {
                    ResizableStickerView resizableStickerView4 = ResizableStickerView.this;
                    resizableStickerView4.wi = resizableStickerView4.getLayoutParams().width;
                    ResizableStickerView resizableStickerView5 = ResizableStickerView.this;
                    resizableStickerView5.he = resizableStickerView5.getLayoutParams().height;
                    ResizableStickerView resizableStickerView6 = ResizableStickerView.this;
                    resizableStickerView6.leftMargin = ((LayoutParams) resizableStickerView6.getLayoutParams()).leftMargin;
                    ResizableStickerView resizableStickerView7 = ResizableStickerView.this;
                    resizableStickerView7.topMargin = ((LayoutParams) resizableStickerView7.getLayoutParams()).topMargin;
                    ResizableStickerView resizableStickerView8 = ResizableStickerView.this;
                    resizableStickerView8.field_two = String.valueOf(ResizableStickerView.this.leftMargin) + "," + String.valueOf(ResizableStickerView.this.topMargin);
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleUp(ResizableStickerView.this);
                    }
                } else if (action == 2) {
                    if (resizableStickerView != null) {
                        resizableStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleMove(ResizableStickerView.this);
                    }
                    float degrees = (float) Math.toDegrees(Math.atan2(rawY - ResizableStickerView.this.basey, rawX - ResizableStickerView.this.basex));
                    if (degrees < 0.0f) {
                        degrees += 360.0f;
                    }
                    int i2 = rawX - ResizableStickerView.this.basex;
                    int i22 = rawY - ResizableStickerView.this.basey;
                    int i3 = i22 * i22;
                    int sqrt = (int) (Math.sqrt((i2 * i2) + i3) * Math.cos(Math.toRadians(degrees - ResizableStickerView.this.getRotation())));
                    int sqrt2 = (int) (Math.sqrt((sqrt * sqrt) + i3) * Math.sin(Math.toRadians(degrees - ResizableStickerView.this.getRotation())));
                    int i4 = (sqrt * 2) + ResizableStickerView.this.basew;
                    int i5 = (sqrt2 * 2) + ResizableStickerView.this.baseh;
                    if (i4 > ResizableStickerView.this.s2) {
                        layoutParams.width = i4;
                        layoutParams.leftMargin = ResizableStickerView.this.margl - sqrt;
                    }
                    if (i5 > ResizableStickerView.this.s2) {
                        layoutParams.height = i5;
                        layoutParams.topMargin = ResizableStickerView.this.margt - sqrt2;
                    }
                    ResizableStickerView.this.setLayoutParams(layoutParams);
                    ResizableStickerView.this.performLongClick();
                }
                return true;
            }
        };
        this.onTouchAngle = 0.0d;
        this.rTouchListener = new OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ResizableStickerView resizableStickerView = (ResizableStickerView) view.getParent();
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (resizableStickerView != null) {
                        resizableStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onRotateDown(ResizableStickerView.this);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    ResizableStickerView.this.cX = rect.exactCenterX();
                    ResizableStickerView.this.cY = rect.exactCenterY();
                    ResizableStickerView.this.vAngle = ((View) view.getParent()).getRotation();
                    ResizableStickerView resizableStickerView2 = ResizableStickerView.this;
                    resizableStickerView2.tAngle = (Math.atan2(resizableStickerView2.cY - motionEvent.getRawY(), ResizableStickerView.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                    ResizableStickerView resizableStickerView3 = ResizableStickerView.this;
                    resizableStickerView3.dAngle = resizableStickerView3.vAngle - ResizableStickerView.this.tAngle;
                } else if (action != 1) {
                    if (action == 2) {
                        if (resizableStickerView != null) {
                            resizableStickerView.requestDisallowInterceptTouchEvent(true);
                        }
                        if (ResizableStickerView.this.listener != null) {
                            ResizableStickerView.this.listener.onRotateMove(ResizableStickerView.this);
                        }
                        ResizableStickerView resizableStickerView4 = ResizableStickerView.this;
                        resizableStickerView4.angle = (Math.atan2(resizableStickerView4.cY - motionEvent.getRawY(), ResizableStickerView.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                        float f = (float) (ResizableStickerView.this.angle + ResizableStickerView.this.dAngle);
                        ((View) view.getParent()).setRotation(f);
                        ((View) view.getParent()).invalidate();
                        ((View) view.getParent()).requestLayout();
                        if (Math.abs(90.0f - Math.abs(f)) <= 5.0f) {
                            f = f > 0.0f ? 90.0f : -90.0f;
                        }
                        if (Math.abs(0.0f - Math.abs(f)) <= 5.0f) {
                            f = f > 0.0f ? 0.0f : -0.0f;
                        }
                        if (Math.abs(180.0f - Math.abs(f)) <= 5.0f) {
                            f = f > 0.0f ? 180.0f : -180.0f;
                        }
                        ((View) view.getParent()).setRotation(f);
                    }
                } else if (ResizableStickerView.this.listener != null) {
                    ResizableStickerView.this.listener.onRotateUp(ResizableStickerView.this);
                }
                return true;
            }
        };
        this.resUri = null;
        this.scaleRotateProg = 0;
        this.scale_orgHeight = -1.0d;
        this.scale_orgWidth = -1.0d;
        this.scale_orgX = -1.0f;
        this.scale_orgY = -1.0f;
        this.screenHeight = 300;
        this.screenWidth = 300;
        this.stkr_path = "";
        this.tAngle = 0.0d;
        this.this_orgX = -1.0f;
        this.this_orgY = -1.0f;
        this.topMargin = 0;
        this.vAngle = 0.0d;
        this.widthMain = 0.0f;
        this.xRotateProg = 0;
        this.yRotateProg = 0;
        this.yRotation = 0.0f;
        this.zRotateProg = 0;
        init(context);
    }

    public void stickerDelete() {
        final ViewGroup viewGroup = (ViewGroup) getParent();
        this.zoomInScale.setAnimationListener(new Animation.AnimationListener() { 
            @Override 
            public void onAnimationRepeat(Animation animation) {
            }

            @Override 
            public void onAnimationStart(Animation animation) {
            }

            @Override 
            public void onAnimationEnd(Animation animation) {
                viewGroup.removeView(ResizableStickerView.this);
            }
        });
        this.main_iv.startAnimation(this.zoomInScale);
        setBorderVisibility(false);
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onDelete();
        }
    }

    public void init(Context context) {
        this.context = context;
        this.main_iv = new ImageView(this.context);
        this.scale_iv = new ImageView(this.context);
        this.border_iv = new ImageView(this.context);
        this.flip_iv = new ImageView(this.context);
        this.rotate_iv = new ImageView(this.context);
        this.delete_iv = new ImageView(this.context);
        this.margin = dpToPx(this.context, 12);
        this.margin1 = dpToPx(this.context, -5);
        this.s = dpToPx(this.context, 25);
        this.s2 = dpToPx(this.context, 55);
        this.wi = dpToPx(this.context, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.he = dpToPx(this.context, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.scale_iv.setImageResource(R.drawable.sticker_scale);
        this.border_iv.setImageResource(R.drawable.sticker_border_gray_bg);
        this.flip_iv.setImageResource(R.drawable.sticker_border_gray);
        this.rotate_iv.setImageResource(R.drawable.rotate);
        this.delete_iv.setImageResource(R.drawable.sticker_delete1);
        LayoutParams layoutParams = new LayoutParams(this.wi, this.he);
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        int i = this.s;
        layoutParams2.setMargins(i, i, i, i);
        layoutParams2.addRule(17);
        int i2 = this.s;
        LayoutParams layoutParams3 = new LayoutParams(i2, i2);
        layoutParams3.addRule(12);
        layoutParams3.addRule(11);
        int i3 = this.s;
        LayoutParams layoutParams4 = new LayoutParams(i3, i3);
        layoutParams4.addRule(10);
        layoutParams4.addRule(11);
        int i4 = this.s;
        LayoutParams layoutParams5 = new LayoutParams(i4, i4);
        layoutParams5.addRule(12);
        layoutParams5.addRule(9);
        int i5 = this.s;
        LayoutParams layoutParams6 = new LayoutParams(i5, i5);
        layoutParams6.addRule(10);
        layoutParams6.addRule(9);
        LayoutParams layoutParams7 = new LayoutParams(-1, -1);
        setLayoutParams(layoutParams);
        addView(this.border_iv);
        this.border_iv.setLayoutParams(layoutParams7);
        this.border_iv.setScaleType(ImageView.ScaleType.FIT_XY);
        this.border_iv.setTag("border_iv");
        ImageView imageView = this.border_iv;
        int i6 = this.margin;
        imageView.setPadding(i6, i6, i6, i6);
        addView(this.main_iv);
        this.main_iv.setLayoutParams(layoutParams2);
        addView(this.flip_iv);
        this.flip_iv.setLayoutParams(layoutParams4);
        this.flip_iv.setOnClickListener(new OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (ResizableStickerView.this.yRotation == 0.0d) {
                    ResizableStickerView.this.yRotation = 1.0f;
                } else {
                    ResizableStickerView.this.yRotation = 0.0f;
                }
                if (ResizableStickerView.this.stkr_path.equals("")) {
                    if (ResizableStickerView.this.drawableId.equals("0")) {
                        ResizableStickerView.this.addStkrBitmap(false);
                        return;
                    }
                    ResizableStickerView resizableStickerView = ResizableStickerView.this;
                    resizableStickerView.setBgDrawable(resizableStickerView.drawableId, false);
                    return;
                }
                ResizableStickerView resizableStickerView2 = ResizableStickerView.this;
                resizableStickerView2.setStrPath(resizableStickerView2.stkr_path, false);
            }
        });
        addView(this.rotate_iv);
        this.rotate_iv.setLayoutParams(layoutParams5);
        this.rotate_iv.setOnTouchListener(this.rTouchListener);
        addView(this.delete_iv);
        this.delete_iv.setLayoutParams(layoutParams6);
        this.delete_iv.setOnClickListener(new OnClickListener() { 
            @Override 
            public void onClick(View view) {
                final ViewGroup viewGroup = (ViewGroup) ResizableStickerView.this.getParent();
                ResizableStickerView.this.zoomInScale.setAnimationListener(new Animation.AnimationListener() { 
                    @Override 
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override 
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override 
                    public void onAnimationEnd(Animation animation) {
                        viewGroup.removeView(ResizableStickerView.this);
                    }
                });
                ResizableStickerView.this.main_iv.startAnimation(ResizableStickerView.this.zoomInScale);
                ResizableStickerView.this.setBorderVisibility(false);
                if (ResizableStickerView.this.listener != null) {
                    ResizableStickerView.this.listener.onDelete();
                }
            }
        });
        addView(this.scale_iv);
        this.scale_iv.setLayoutParams(layoutParams3);
        this.scale_iv.setOnTouchListener(this.mTouchListener1);
        this.scale_iv.setTag("scale_iv");
        this.rotation = getRotation();
        this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_anim);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_out);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_in);
        this.isMultiTouchEnabled = setDefaultTouchListener(true);
    }

    public boolean setDefaultTouchListener(boolean z) {
        if (z) {
            this.lockStatus = "UNLOCKED";
            setOnTouchListener(new MultiTouchListener(this.context).enableRotation(true).enableTransparencyCheck(this.isHandleTransparency).setOnTouchCallbackListener(this));
            return true;
        }
        this.lockStatus = "LOCKED";
        setOnTouchListener(null);
        return false;
    }

    public void setBorderVisibility(boolean z) {
        this.isBorderVisible = z;
        if (!z) {
            this.border_iv.setVisibility(View.GONE);
            this.scale_iv.setVisibility(View.GONE);
            this.flip_iv.setVisibility(View.GONE);
            this.rotate_iv.setVisibility(View.GONE);
            this.delete_iv.setVisibility(View.GONE);
            setBackgroundResource(0);
            if (this.isColorFilterEnable) {
                this.main_iv.setColorFilter(Color.parseColor("#303828"));
            }
        } else if (this.border_iv.getVisibility() != View.VISIBLE) {
            this.border_iv.setVisibility(View.VISIBLE);
            this.scale_iv.setVisibility(View.VISIBLE);
            this.flip_iv.setVisibility(View.VISIBLE);
            this.rotate_iv.setVisibility(View.VISIBLE);
            if (this.isdeleteEnable) {
                this.delete_iv.setVisibility(View.VISIBLE);
            }
            this.main_iv.startAnimation(this.scale);
        }
    }

    public boolean getBorderVisbilty() {
        return this.isBorderVisible;
    }

    public void opecitySticker(int i) {
        try {
            this.main_iv.setAlpha(i / 100.0f);
            this.imgAlpha = i;
        } catch (Exception unused) {
        }
    }

    public int getHueProg() {
        return this.hueProg;
    }

    public void setHueProg(int i) {
        this.hueProg = i;
        if (i < 1 || i > 5) {
            this.main_iv.setColorFilter(ColorFilterGenerator.adjustHue(i, "watermark"));
        } else {
            this.main_iv.setColorFilter(0);
        }
    }

    public void setHueProgW(int i) {
        this.hueProg = i;
        this.main_iv.setColorFilter(ColorFilterGenerator.adjustHue(i, "watermark"));
    }

    public int getXRotateProg() {
        return this.xRotateProg;
    }

    public int getYRotateProg() {
        return this.yRotateProg;
    }

    public int getZRotateProg() {
        return this.zRotateProg;
    }

    public int geScaleProg() {
        return this.scaleRotateProg;
    }

    public void setStickerRotateProg(int i, int i2, int i3, int i4, int i5, int i6) {
        this.xRotateProg = i4;
        this.yRotateProg = i5;
        this.zRotateProg = i6;
        applyTransformation(i, i2, i3);
    }

    public void setScaleViewProg(int i) {
        this.scaleRotateProg = i;
        float f = i / 10.0f;
        this.main_iv.setScaleX(f);
        this.main_iv.setScaleY(f);
    }

    public String getColorType() {
        return this.colorType;
    }

    public void setColorType(String str) {
        this.colorType = str;
    }

    public int getAlphaProg() {
        return this.alphaProg;
    }

    public void setAlphaProg(int i) {
        this.alphaProg = i;
        this.main_iv.setAlpha(i / 100.0f);
        Log.e("DDDDD", this.main_iv.getAlpha() + "");
    }

    public int getColor() {
        return this.imgColor;
    }

    public void setColor(int i) {
        try {
            this.main_iv.setColorFilter(i);
            this.imgColor = i;
        } catch (Exception unused) {
        }
    }

    public void setBgDrawable(String str, boolean z) {
        this.drawableId = str;
        addStkrBitmap(z);
    }

    public void setStrPath(String str, boolean z) {
        Uri parse = Uri.parse(str);
        if (this.yRotation != 0.0f) {
            Glide.with(this.context).load(parse.toString()).diskCacheStrategy(DiskCacheStrategy.NONE).dontAnimate().override(dpToPx(this.context, 300), dpToPx(this.context, 300)).transform(new MyTransformation(this.context, true)).into(this.main_iv);
        } else {
            Glide.with(this.context).load(parse.toString()).diskCacheStrategy(DiskCacheStrategy.NONE).dontAnimate().override(dpToPx(this.context, 300), dpToPx(this.context, 300)).into(this.main_iv);
        }
        this.stkr_path = str;
        if (z) {
            this.main_iv.startAnimation(this.zoomOutScale);
        }
    }

    public void applyTransformation(int i, int i2, int i3) {
        this.main_iv.setRotationX(i);
        this.main_iv.setRotationY(i2);
        this.main_iv.setRotation(i3);
        setVisibility(View.VISIBLE);
        this.main_iv.setVisibility(View.VISIBLE);
        this.main_iv.requestLayout();
        this.main_iv.postInvalidate();
        requestLayout();
        postInvalidate();
    }

    public void setComponentInfo(ComponentInfo componentInfo) {
        this.wi = componentInfo.getWIDTH();
        this.he = componentInfo.getHEIGHT();
        this.drawableId = componentInfo.getRES_ID();
        this.resUri = componentInfo.getRES_URI();
        this.btmp = componentInfo.getBITMAP();
        this.rotation = componentInfo.getROTATION();
        this.imgColor = componentInfo.getSTC_COLOR();
        this.yRotation = componentInfo.getY_ROTATION();
        this.alphaProg = componentInfo.getSTC_OPACITY();
        this.stkr_path = componentInfo.getSTKR_PATH();
        this.colorType = componentInfo.getCOLORTYPE();
        this.hueProg = componentInfo.getSTC_HUE();
        this.lockStatus = componentInfo.getFIELD_THREE();
        this.field_two = componentInfo.getFIELD_TWO();
        this.xRotateProg = componentInfo.getXRotateProg();
        this.yRotateProg = componentInfo.getYRotateProg();
        this.zRotateProg = componentInfo.getZRotateProg();
        this.scaleRotateProg = componentInfo.getScaleProg();
        if (!this.stkr_path.equals("")) {
            setStrPath(this.stkr_path, true);
        } else if (this.drawableId.equals("0")) {
            addStkrBitmap(true);
        } else {
            setBgDrawable(this.drawableId, true);
        }
        if (this.colorType.equals("white")) {
            setColor(this.imgColor);
        } else {
            setHueProg(this.hueProg);
        }
        setRotation(this.rotation);
        setScaleViewProg(this.scaleRotateProg);
        setAlphaProg(this.alphaProg);
        if (this.field_two.equals("")) {
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(componentInfo.getPOS_X());
            setY(componentInfo.getPOS_Y());
        } else {
            String[] split = this.field_two.split(",");
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            ((LayoutParams) getLayoutParams()).leftMargin = parseInt;
            ((LayoutParams) getLayoutParams()).topMargin = parseInt2;
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(componentInfo.getPOS_X() + (parseInt * (-1)));
            setY(componentInfo.getPOS_Y() + (parseInt2 * (-1)));
        }
        if (componentInfo.getTYPE() == "SHAPE") {
            this.flip_iv.setVisibility(View.GONE);
            this.isSticker = false;
        }
        if (componentInfo.getTYPE() == "STICKER") {
            this.flip_iv.setVisibility(View.VISIBLE);
            this.isSticker = true;
        }
        if (componentInfo.getTYPE() == "WATERMARK") {
            this.flip_iv.setVisibility(View.VISIBLE);
            this.delete_iv.setVisibility(View.GONE);
            this.isSticker = true;
            this.isdeleteEnable = false;
            this.isHandleTransparency = false;
        }
        if (this.lockStatus.equals("LOCKED")) {
            this.isMultiTouchEnabled = setDefaultTouchListener(false);
        } else {
            this.isMultiTouchEnabled = setDefaultTouchListener(true);
        }
    }

    public void addStkrBitmap(boolean z) {
        if (this.drawableId.equals("0")) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            this.btmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            if (this.yRotation != 0.0f) {
                Glide.with(this.context).asBitmap().load(byteArrayOutputStream.toByteArray()).override(dpToPx(this.context, 300), dpToPx(this.context, 300)).transform(new MyTransformation(this.context, true)).into(this.main_iv);
            } else {
                Glide.with(this.context).asBitmap().load(byteArrayOutputStream.toByteArray()).override(dpToPx(this.context, 300), dpToPx(this.context, 300)).into(this.main_iv);
            }
        } else {
            getResources().getIdentifier(this.drawableId, "drawable", this.context.getPackageName());
            TouchEventListener touchEventListener = this.listener;
            if (touchEventListener != null) {
                byte[] resBytes = touchEventListener.getResBytes(this.context, this.drawableId);
                Log.e("DIVV", resBytes.length + "");
                Log.e("in_resizeable_value", "yRotation " + this.yRotation);
                if (this.yRotation != 0.0f) {
                    Glide.with(this.context).load(resBytes).dontAnimate().override(dpToPx(this.context, 300), dpToPx(this.context, 300)).transform(new MyTransformation(this.context, true)).into(this.main_iv);
                } else {
                    Log.e("in_resizeable_value", "else");
                    Glide.with(this.context).load(resBytes).dontAnimate().override(dpToPx(this.context, 300), dpToPx(this.context, 300)).into(this.main_iv);
                }
            }
        }
        if (z) {
            this.main_iv.startAnimation(this.zoomOutScale);
        }
    }

    public void optimizeScreen(float f, float f2) {
        this.screenHeight = (int) f2;
        this.screenWidth = (int) f;
    }

    public void setMainLayoutWH(float f, float f2) {
        this.widthMain = f;
        this.heightMain = f2;
    }

    public float getMainWidth() {
        return this.widthMain;
    }

    public float getMainHeight() {
        return this.heightMain;
    }

    public void incrX() {
        setX(getX() + 1.0f);
    }

    public void decX() {
        setX(getX() - 1.0f);
    }

    public void incrY() {
        setY(getY() + 1.0f);
    }

    public void decY() {
        setY(getY() - 1.0f);
    }

    public ComponentInfo getComponentInfo() {
        Bitmap bitmap = this.btmp;
        if (bitmap != null) {
            this.stkr_path = saveBitmapObject1(bitmap);
        }
        ComponentInfo componentInfo = new ComponentInfo();
        componentInfo.setPOS_X(getX());
        componentInfo.setPOS_Y(getY());
        componentInfo.setWIDTH(this.wi);
        componentInfo.setHEIGHT(this.he);
        componentInfo.setRES_ID(this.drawableId);
        componentInfo.setSTC_COLOR(this.imgColor);
        componentInfo.setRES_URI(this.resUri);
        componentInfo.setSTC_OPACITY(this.alphaProg);
        componentInfo.setCOLORTYPE(this.colorType);
        componentInfo.setBITMAP(this.btmp);
        componentInfo.setROTATION(getRotation());
        componentInfo.setY_ROTATION(this.yRotation);
        componentInfo.setXRotateProg(this.xRotateProg);
        componentInfo.setYRotateProg(this.yRotateProg);
        componentInfo.setZRotateProg(this.zRotateProg);
        componentInfo.setScaleProg(this.scaleRotateProg);
        componentInfo.setSTKR_PATH(this.stkr_path);
        componentInfo.setSTC_HUE(this.hueProg);
        componentInfo.setFIELD_ONE(this.field_one);
        componentInfo.setFIELD_TWO(this.field_two);
        componentInfo.setFIELD_THREE(this.lockStatus);
        componentInfo.setFIELD_FOUR(this.field_four);
        return componentInfo;
    }

    private String saveBitmapObject1(Bitmap bitmap) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Dynamo Stickers/category1");
        file.mkdirs();
        File file2 = new File(file, "raw1-" + System.currentTimeMillis() + ".png");
        String absolutePath = file2.getAbsolutePath();
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            return absolutePath;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("testing", "Exception" + e.getMessage());
            return "";
        }
    }

    public int dpToPx(Context context, int i) {
        context.getResources();
        return (int) (i * Resources.getSystem().getDisplayMetrics().density);
    }


    public double getLength(double d, double d2, double d3, double d4) {
        return Math.sqrt(Math.pow(d4 - d2, 2.0d) + Math.pow(d3 - d, 2.0d));
    }

    public void enableColorFilter(boolean z) {
        this.isColorFilterEnable = z;
    }

    @Override 
    public void onTouchCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchDown(view);
        }
    }

    @Override 
    public void onTouchUpCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchUp(view);
        }
    }

    @Override 
    public void onTouchMoveCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchMove(view);
        }
    }

    @Override 
    public void onCenterPosX(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onCenterX(view);
        }
    }

    @Override 
    public void onCenterPosY(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onCenterY(view);
        }
    }

    @Override 
    public void onCenterPosXY(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onCenterXY(view);
        }
    }

    @Override 
    public void onOtherPos(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onOtherXY(view);
        }
    }
}
