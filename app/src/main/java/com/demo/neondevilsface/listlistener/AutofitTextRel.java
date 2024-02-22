package com.demo.neondevilsface.listlistener;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.demo.neondevilsface.R;


public class AutofitTextRel extends RelativeLayout implements MultiTouchListener.TouchCallbackListener {
    double angle;
    private ImageView background_iv;
    int baseh;
    int basew;
    int basex;
    int basey;
    private int bgAlpha;
    private int bgColor;
    private String bgDrawable;
    private ImageView border_iv;
    private int btnmargin;
    private int btnsize;
    float cX;
    float cY;
    private Context context;
    double dAngle;
    private ImageView delete_iv;
    private ImageView edit_iv;
    private String field_four;
    private int field_one;
    private String field_two;
    private String fontName;
    private GestureDetector gd;
    private int he;
    float heightMain;
    private int imgAlpha;
    private boolean isBorderVisible;
    public boolean isMultiTouchEnabled;
    private int leftMargin;
    private int limitsize;
    private TouchEventListener listener;
    String lockStatus;
    private OnTouchListener mTouchListener1;
    private int margin;
    int margl;
    int margt;
    Paint paint;
    int posX;
    int posY;
    private int prog_20;
    private int prog_40;
    private int prog_50;
    private int prog_60;
    private int prog_80;
    private int progress;
    private OnTouchListener rTouchListener;
    private RelativeLayout rel_artv;
    private ImageView rotate_iv;
    private float rotation;
    Animation scale;
    private ImageView scale_iv;
    private int shadowColor;
    private int shadowProg;
    private String stkr_path;
    private int tAlpha;
    double tAngle;
    private int tColor;
    private String text;
    private TextRelativeDraw textRelative;
    public AutoResizeTextView text_iv;
    private int topMargin;
    private String txtGravity;
    double vAngle;
    private int wi;
    float widthMain;
    private int xRotateProg;
    private int yRotateProg;
    private int zRotateProg;
    Animation zoomInScale;
    Animation zoomOutScale;


    public interface TouchEventListener {
        void onCenterX(View view);

        void onCenterXY(View view);

        void onCenterY(View view);

        void onDelete();

        void onDoubleTap();

        void onEdit(View view, Uri uri);

        void onEditText();

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

    public AutofitTextRel setOnTouchCallbackListener(TouchEventListener touchEventListener) {
        this.listener = touchEventListener;
        this.textRelative.invalidate();
        return this;
    }

    public AutofitTextRel(Context context) {
        super(context);
        this.angle = 0.0d;
        this.bgAlpha = 255;
        this.bgColor = 0;
        this.bgDrawable = "0";
        this.cX = 0.0f;
        this.cY = 0.0f;
        this.dAngle = 0.0d;
        this.field_four = "";
        this.field_one = 0;
        this.field_two = "0,0";
        this.fontName = "";
        this.gd = null;
        this.heightMain = 0.0f;
        this.imgAlpha = 100;
        this.isBorderVisible = false;
        this.isMultiTouchEnabled = true;
        this.leftMargin = 0;
        this.listener = null;
        this.lockStatus = "UNLOCKED";
        this.mTouchListener1 = new OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) view.getParent();
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                LayoutParams layoutParams = (LayoutParams) AutofitTextRel.this.getLayoutParams();
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleDown(AutofitTextRel.this);
                    }
                    AutofitTextRel.this.invalidate();
                    AutofitTextRel.this.basex = rawX;
                    AutofitTextRel.this.basey = rawY;
                    AutofitTextRel autofitTextRel2 = AutofitTextRel.this;
                    autofitTextRel2.basew = autofitTextRel2.getWidth();
                    AutofitTextRel autofitTextRel3 = AutofitTextRel.this;
                    autofitTextRel3.baseh = autofitTextRel3.getHeight();
                    AutofitTextRel.this.getLocationOnScreen(new int[2]);
                    AutofitTextRel.this.margl = layoutParams.leftMargin;
                    AutofitTextRel.this.margt = layoutParams.topMargin;
                } else if (action == 1) {
                    AutofitTextRel autofitTextRel4 = AutofitTextRel.this;
                    autofitTextRel4.wi = autofitTextRel4.getLayoutParams().width;
                    AutofitTextRel autofitTextRel5 = AutofitTextRel.this;
                    autofitTextRel5.he = autofitTextRel5.getLayoutParams().height;
                    AutofitTextRel autofitTextRel6 = AutofitTextRel.this;
                    autofitTextRel6.field_two = String.valueOf(AutofitTextRel.this.leftMargin) + "," + String.valueOf(AutofitTextRel.this.topMargin);
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleUp(AutofitTextRel.this);
                    }
                } else if (action == 2) {
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleMove(AutofitTextRel.this);
                    }
                    float degrees = (float) Math.toDegrees(Math.atan2(rawY - AutofitTextRel.this.basey, rawX - AutofitTextRel.this.basex));
                    if (degrees < 0.0f) {
                        degrees += 360.0f;
                    }
                    int i = rawX - AutofitTextRel.this.basex;
                    int i2 = rawY - AutofitTextRel.this.basey;
                    int i3 = i2 * i2;
                    int sqrt = (int) (Math.sqrt((i * i) + i3) * Math.cos(Math.toRadians(degrees - AutofitTextRel.this.getRotation())));
                    int sqrt2 = (int) (Math.sqrt((sqrt * sqrt) + i3) * Math.sin(Math.toRadians(degrees - AutofitTextRel.this.getRotation())));
                    int i4 = (sqrt * 2) + AutofitTextRel.this.basew;
                    int i5 = (sqrt2 * 2) + AutofitTextRel.this.baseh;
                    if (i4 > AutofitTextRel.this.limitsize && i4 < AutofitTextRel.this.widthMain) {
                        layoutParams.width = i4;
                        layoutParams.leftMargin = AutofitTextRel.this.margl - sqrt;
                    }
                    if (i5 > AutofitTextRel.this.limitsize && i5 < AutofitTextRel.this.heightMain) {
                        layoutParams.height = i5;
                        layoutParams.topMargin = AutofitTextRel.this.margt - sqrt2;
                    }
                    AutofitTextRel.this.setLayoutParams(layoutParams);
                    if (!AutofitTextRel.this.bgDrawable.equals("0")) {
                        AutofitTextRel autofitTextRel7 = AutofitTextRel.this;
                        autofitTextRel7.wi = autofitTextRel7.getLayoutParams().width;
                        AutofitTextRel autofitTextRel8 = AutofitTextRel.this;
                        autofitTextRel8.he = autofitTextRel8.getLayoutParams().height;
                        AutofitTextRel autofitTextRel9 = AutofitTextRel.this;
                        autofitTextRel9.setBgDrawable(autofitTextRel9.bgDrawable);
                    }
                }
                return true;
            }
        };
        this.prog_20 = 0;
        this.prog_40 = 0;
        this.prog_50 = 0;
        this.prog_60 = 0;
        this.progress = 0;
        this.rTouchListener = new OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) view.getParent();
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onRotateDown(AutofitTextRel.this);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    AutofitTextRel.this.cX = rect.exactCenterX();
                    AutofitTextRel.this.cY = rect.exactCenterY();
                    AutofitTextRel.this.vAngle = ((View) view.getParent()).getRotation();
                    AutofitTextRel autofitTextRel2 = AutofitTextRel.this;
                    autofitTextRel2.tAngle = (Math.atan2(autofitTextRel2.cY - motionEvent.getRawY(), AutofitTextRel.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                    AutofitTextRel autofitTextRel3 = AutofitTextRel.this;
                    autofitTextRel3.dAngle = autofitTextRel3.vAngle - AutofitTextRel.this.tAngle;
                } else if (action != 1) {
                    if (action == 2) {
                        if (autofitTextRel != null) {
                            autofitTextRel.requestDisallowInterceptTouchEvent(true);
                        }
                        if (AutofitTextRel.this.listener != null) {
                            AutofitTextRel.this.listener.onRotateMove(AutofitTextRel.this);
                        }
                        AutofitTextRel autofitTextRel4 = AutofitTextRel.this;
                        autofitTextRel4.angle = (Math.atan2(autofitTextRel4.cY - motionEvent.getRawY(), AutofitTextRel.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                        float f = (float) (AutofitTextRel.this.angle + AutofitTextRel.this.dAngle);
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
                } else if (AutofitTextRel.this.listener != null) {
                    AutofitTextRel.this.listener.onRotateUp(AutofitTextRel.this);
                }
                return true;
            }
        };
        this.shadowColor = 0;
        this.shadowProg = 0;
        this.stkr_path = "";
        this.tAlpha = 100;
        this.tAngle = 0.0d;
        this.tColor = Color.parseColor("#000000");
        this.text = "";
        this.topMargin = 0;
        this.txtGravity = "C";
        this.vAngle = 0.0d;
        this.widthMain = 0.0f;
        this.xRotateProg = 0;
        this.yRotateProg = 0;
        this.zRotateProg = 0;
        init(context);
        invalidate();
    }

    public AutofitTextRel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.angle = 0.0d;
        this.bgAlpha = 255;
        this.bgColor = 0;
        this.bgDrawable = "0";
        this.cX = 0.0f;
        this.cY = 0.0f;
        this.dAngle = 0.0d;
        this.field_four = "";
        this.field_one = 0;
        this.field_two = "0,0";
        this.fontName = "";
        this.gd = null;
        this.heightMain = 0.0f;
        this.imgAlpha = 100;
        this.isBorderVisible = false;
        this.isMultiTouchEnabled = true;
        this.leftMargin = 0;
        this.listener = null;
        this.lockStatus = "UNLOCKED";
        this.mTouchListener1 = new OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) view.getParent();
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                LayoutParams layoutParams = (LayoutParams) AutofitTextRel.this.getLayoutParams();
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleDown(AutofitTextRel.this);
                    }
                    AutofitTextRel.this.invalidate();
                    AutofitTextRel.this.basex = rawX;
                    AutofitTextRel.this.basey = rawY;
                    AutofitTextRel autofitTextRel2 = AutofitTextRel.this;
                    autofitTextRel2.basew = autofitTextRel2.getWidth();
                    AutofitTextRel autofitTextRel3 = AutofitTextRel.this;
                    autofitTextRel3.baseh = autofitTextRel3.getHeight();
                    AutofitTextRel.this.getLocationOnScreen(new int[2]);
                    AutofitTextRel.this.margl = layoutParams.leftMargin;
                    AutofitTextRel.this.margt = layoutParams.topMargin;
                } else if (action == 1) {
                    AutofitTextRel autofitTextRel4 = AutofitTextRel.this;
                    autofitTextRel4.wi = autofitTextRel4.getLayoutParams().width;
                    AutofitTextRel autofitTextRel5 = AutofitTextRel.this;
                    autofitTextRel5.he = autofitTextRel5.getLayoutParams().height;
                    AutofitTextRel autofitTextRel6 = AutofitTextRel.this;
                    autofitTextRel6.field_two = String.valueOf(AutofitTextRel.this.leftMargin) + "," + String.valueOf(AutofitTextRel.this.topMargin);
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleUp(AutofitTextRel.this);
                    }
                } else if (action == 2) {
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleMove(AutofitTextRel.this);
                    }
                    float degrees = (float) Math.toDegrees(Math.atan2(rawY - AutofitTextRel.this.basey, rawX - AutofitTextRel.this.basex));
                    if (degrees < 0.0f) {
                        degrees += 360.0f;
                    }
                    int i = rawX - AutofitTextRel.this.basex;
                    int i2 = rawY - AutofitTextRel.this.basey;
                    int i3 = i2 * i2;
                    int sqrt = (int) (Math.sqrt((i * i) + i3) * Math.cos(Math.toRadians(degrees - AutofitTextRel.this.getRotation())));
                    int sqrt2 = (int) (Math.sqrt((sqrt * sqrt) + i3) * Math.sin(Math.toRadians(degrees - AutofitTextRel.this.getRotation())));
                    int i4 = (sqrt * 2) + AutofitTextRel.this.basew;
                    int i5 = (sqrt2 * 2) + AutofitTextRel.this.baseh;
                    if (i4 > AutofitTextRel.this.limitsize && i4 < AutofitTextRel.this.widthMain) {
                        layoutParams.width = i4;
                        layoutParams.leftMargin = AutofitTextRel.this.margl - sqrt;
                    }
                    if (i5 > AutofitTextRel.this.limitsize && i5 < AutofitTextRel.this.heightMain) {
                        layoutParams.height = i5;
                        layoutParams.topMargin = AutofitTextRel.this.margt - sqrt2;
                    }
                    AutofitTextRel.this.setLayoutParams(layoutParams);
                    if (!AutofitTextRel.this.bgDrawable.equals("0")) {
                        AutofitTextRel autofitTextRel7 = AutofitTextRel.this;
                        autofitTextRel7.wi = autofitTextRel7.getLayoutParams().width;
                        AutofitTextRel autofitTextRel8 = AutofitTextRel.this;
                        autofitTextRel8.he = autofitTextRel8.getLayoutParams().height;
                        AutofitTextRel autofitTextRel9 = AutofitTextRel.this;
                        autofitTextRel9.setBgDrawable(autofitTextRel9.bgDrawable);
                    }
                }
                return true;
            }
        };
        this.prog_20 = 0;
        this.prog_40 = 0;
        this.prog_50 = 0;
        this.prog_60 = 0;
        this.progress = 0;
        this.rTouchListener = new OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) view.getParent();
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onRotateDown(AutofitTextRel.this);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    AutofitTextRel.this.cX = rect.exactCenterX();
                    AutofitTextRel.this.cY = rect.exactCenterY();
                    AutofitTextRel.this.vAngle = ((View) view.getParent()).getRotation();
                    AutofitTextRel autofitTextRel2 = AutofitTextRel.this;
                    autofitTextRel2.tAngle = (Math.atan2(autofitTextRel2.cY - motionEvent.getRawY(), AutofitTextRel.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                    AutofitTextRel autofitTextRel3 = AutofitTextRel.this;
                    autofitTextRel3.dAngle = autofitTextRel3.vAngle - AutofitTextRel.this.tAngle;
                } else if (action != 1) {
                    if (action == 2) {
                        if (autofitTextRel != null) {
                            autofitTextRel.requestDisallowInterceptTouchEvent(true);
                        }
                        if (AutofitTextRel.this.listener != null) {
                            AutofitTextRel.this.listener.onRotateMove(AutofitTextRel.this);
                        }
                        AutofitTextRel autofitTextRel4 = AutofitTextRel.this;
                        autofitTextRel4.angle = (Math.atan2(autofitTextRel4.cY - motionEvent.getRawY(), AutofitTextRel.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                        float f = (float) (AutofitTextRel.this.angle + AutofitTextRel.this.dAngle);
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
                } else if (AutofitTextRel.this.listener != null) {
                    AutofitTextRel.this.listener.onRotateUp(AutofitTextRel.this);
                }
                return true;
            }
        };
        this.shadowColor = 0;
        this.shadowProg = 0;
        this.stkr_path = "";
        this.tAlpha = 100;
        this.tAngle = 0.0d;
        this.tColor = Color.parseColor("#000000");
        this.text = "";
        this.topMargin = 0;
        this.txtGravity = "C";
        this.vAngle = 0.0d;
        this.widthMain = 0.0f;
        this.xRotateProg = 0;
        this.yRotateProg = 0;
        this.zRotateProg = 0;
        init(context);
    }

    public AutofitTextRel(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.angle = 0.0d;
        this.bgAlpha = 255;
        this.bgColor = 0;
        this.bgDrawable = "0";
        this.cX = 0.0f;
        this.cY = 0.0f;
        this.dAngle = 0.0d;
        this.field_four = "";
        this.field_one = 0;
        this.field_two = "0,0";
        this.fontName = "";
        this.gd = null;
        this.heightMain = 0.0f;
        this.imgAlpha = 100;
        this.isBorderVisible = false;
        this.isMultiTouchEnabled = true;
        this.leftMargin = 0;
        this.listener = null;
        this.lockStatus = "UNLOCKED";
        this.mTouchListener1 = new OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) view.getParent();
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                LayoutParams layoutParams = (LayoutParams) AutofitTextRel.this.getLayoutParams();
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleDown(AutofitTextRel.this);
                    }
                    AutofitTextRel.this.invalidate();
                    AutofitTextRel.this.basex = rawX;
                    AutofitTextRel.this.basey = rawY;
                    AutofitTextRel autofitTextRel2 = AutofitTextRel.this;
                    autofitTextRel2.basew = autofitTextRel2.getWidth();
                    AutofitTextRel autofitTextRel3 = AutofitTextRel.this;
                    autofitTextRel3.baseh = autofitTextRel3.getHeight();
                    AutofitTextRel.this.getLocationOnScreen(new int[2]);
                    AutofitTextRel.this.margl = layoutParams.leftMargin;
                    AutofitTextRel.this.margt = layoutParams.topMargin;
                } else if (action == 1) {
                    AutofitTextRel autofitTextRel4 = AutofitTextRel.this;
                    autofitTextRel4.wi = autofitTextRel4.getLayoutParams().width;
                    AutofitTextRel autofitTextRel5 = AutofitTextRel.this;
                    autofitTextRel5.he = autofitTextRel5.getLayoutParams().height;
                    AutofitTextRel autofitTextRel6 = AutofitTextRel.this;
                    autofitTextRel6.field_two = String.valueOf(AutofitTextRel.this.leftMargin) + "," + String.valueOf(AutofitTextRel.this.topMargin);
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleUp(AutofitTextRel.this);
                    }
                } else if (action == 2) {
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleMove(AutofitTextRel.this);
                    }
                    float degrees = (float) Math.toDegrees(Math.atan2(rawY - AutofitTextRel.this.basey, rawX - AutofitTextRel.this.basex));
                    if (degrees < 0.0f) {
                        degrees += 360.0f;
                    }
                    int i2 = rawX - AutofitTextRel.this.basex;
                    int i22 = rawY - AutofitTextRel.this.basey;
                    int i3 = i22 * i22;
                    int sqrt = (int) (Math.sqrt((i2 * i2) + i3) * Math.cos(Math.toRadians(degrees - AutofitTextRel.this.getRotation())));
                    int sqrt2 = (int) (Math.sqrt((sqrt * sqrt) + i3) * Math.sin(Math.toRadians(degrees - AutofitTextRel.this.getRotation())));
                    int i4 = (sqrt * 2) + AutofitTextRel.this.basew;
                    int i5 = (sqrt2 * 2) + AutofitTextRel.this.baseh;
                    if (i4 > AutofitTextRel.this.limitsize && i4 < AutofitTextRel.this.widthMain) {
                        layoutParams.width = i4;
                        layoutParams.leftMargin = AutofitTextRel.this.margl - sqrt;
                    }
                    if (i5 > AutofitTextRel.this.limitsize && i5 < AutofitTextRel.this.heightMain) {
                        layoutParams.height = i5;
                        layoutParams.topMargin = AutofitTextRel.this.margt - sqrt2;
                    }
                    AutofitTextRel.this.setLayoutParams(layoutParams);
                    if (!AutofitTextRel.this.bgDrawable.equals("0")) {
                        AutofitTextRel autofitTextRel7 = AutofitTextRel.this;
                        autofitTextRel7.wi = autofitTextRel7.getLayoutParams().width;
                        AutofitTextRel autofitTextRel8 = AutofitTextRel.this;
                        autofitTextRel8.he = autofitTextRel8.getLayoutParams().height;
                        AutofitTextRel autofitTextRel9 = AutofitTextRel.this;
                        autofitTextRel9.setBgDrawable(autofitTextRel9.bgDrawable);
                    }
                }
                return true;
            }
        };
        this.prog_20 = 0;
        this.prog_40 = 0;
        this.prog_50 = 0;
        this.prog_60 = 0;
        this.progress = 0;
        this.rTouchListener = new OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) view.getParent();
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onRotateDown(AutofitTextRel.this);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    AutofitTextRel.this.cX = rect.exactCenterX();
                    AutofitTextRel.this.cY = rect.exactCenterY();
                    AutofitTextRel.this.vAngle = ((View) view.getParent()).getRotation();
                    AutofitTextRel autofitTextRel2 = AutofitTextRel.this;
                    autofitTextRel2.tAngle = (Math.atan2(autofitTextRel2.cY - motionEvent.getRawY(), AutofitTextRel.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                    AutofitTextRel autofitTextRel3 = AutofitTextRel.this;
                    autofitTextRel3.dAngle = autofitTextRel3.vAngle - AutofitTextRel.this.tAngle;
                } else if (action != 1) {
                    if (action == 2) {
                        if (autofitTextRel != null) {
                            autofitTextRel.requestDisallowInterceptTouchEvent(true);
                        }
                        if (AutofitTextRel.this.listener != null) {
                            AutofitTextRel.this.listener.onRotateMove(AutofitTextRel.this);
                        }
                        AutofitTextRel autofitTextRel4 = AutofitTextRel.this;
                        autofitTextRel4.angle = (Math.atan2(autofitTextRel4.cY - motionEvent.getRawY(), AutofitTextRel.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                        float f = (float) (AutofitTextRel.this.angle + AutofitTextRel.this.dAngle);
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
                } else if (AutofitTextRel.this.listener != null) {
                    AutofitTextRel.this.listener.onRotateUp(AutofitTextRel.this);
                }
                return true;
            }
        };
        this.shadowColor = 0;
        this.shadowProg = 0;
        this.stkr_path = "";
        this.tAlpha = 100;
        this.tAngle = 0.0d;
        this.tColor = Color.parseColor("#000000");
        this.text = "";
        this.topMargin = 0;
        this.txtGravity = "C";
        this.vAngle = 0.0d;
        this.widthMain = 0.0f;
        this.xRotateProg = 0;
        this.yRotateProg = 0;
        this.zRotateProg = 0;
        init(context);
    }

    public void init(Context context) {
        Paint paint = new Paint();
        this.paint = paint;
        paint.setColor(0);
        this.context = context;
        this.scale_iv = new ImageView(this.context);
        this.border_iv = new ImageView(this.context);
        this.background_iv = new ImageView(this.context);
        this.delete_iv = new ImageView(this.context);
        this.rotate_iv = new ImageView(this.context);
        this.edit_iv = new ImageView(this.context);
        this.text_iv = new AutoResizeTextView(this.context);
        this.rel_artv = new RelativeLayout(this.context);
        this.textRelative = new TextRelativeDraw(this.context, this.rel_artv);
        this.margin = dpToPx(this.context, 25);
        this.btnmargin = dpToPx(this.context, 12);
        this.btnsize = dpToPx(this.context, 25);
        this.limitsize = dpToPx(this.context, 55);
        this.wi = dpToPx(this.context, 300);
        this.he = dpToPx(this.context, 300);
        this.scale_iv.setImageResource(R.drawable.sticker_scale);
        this.background_iv.setImageResource(0);
        this.rotate_iv.setImageResource(R.drawable.rotate);
        this.delete_iv.setImageResource(R.drawable.sticker_delete1);
        this.edit_iv.setImageResource(R.drawable.ic_edit_with_bg);
        int i = this.btnsize;
        LayoutParams layoutParams = new LayoutParams(i, i);
        layoutParams.addRule(10);
        layoutParams.addRule(11);
        this.edit_iv.setLayoutParams(layoutParams);
        LayoutParams layoutParams2 = new LayoutParams(this.wi, this.he);
        LayoutParams layoutParams3 = new LayoutParams(-1, -1);
        int i2 = this.btnsize;
        LayoutParams layoutParams4 = new LayoutParams(i2, i2);
        layoutParams4.addRule(12);
        layoutParams4.addRule(11);
        LayoutParams layoutParams5 = new LayoutParams(-1, -1);
        layoutParams5.setMargins(0, 0, 0, 0);
        layoutParams5.addRule(17);
        LayoutParams layoutParams6 = new LayoutParams(-1, -1);
        int i3 = this.margin;
        layoutParams6.setMargins(i3, i3, i3, i3);
        layoutParams6.addRule(17);
        LayoutParams layoutParams7 = new LayoutParams(-1, -1);
        int i4 = this.margin;
        layoutParams7.setMargins(i4, i4, i4, i4);
        layoutParams7.addRule(17);
        int i5 = this.btnsize;
        LayoutParams layoutParams8 = new LayoutParams(i5, i5);
        layoutParams8.addRule(10);
        layoutParams8.addRule(9);
        LayoutParams layoutParams9 = new LayoutParams(-1, -1);
        this.border_iv.setImageResource(R.drawable.sticker_border_gray_bg);
        int i6 = this.btnmargin;
        layoutParams9.setMargins(i6, i6, i6, i6);
        setLayoutParams(layoutParams2);
        int i7 = this.btnsize;
        LayoutParams layoutParams10 = new LayoutParams(i7, i7);
        layoutParams10.addRule(12);
        layoutParams10.addRule(9);
        this.background_iv.setLayoutParams(layoutParams3);
        this.background_iv.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(this.background_iv);
        this.text_iv.setText(this.text);
        this.text_iv.setTextColor(this.tColor);
        this.text_iv.setTextSize(1000.0f);
        this.text_iv.setLayoutParams(layoutParams5);
        this.text_iv.setPadding(0, 0, 0, 0);
        this.text_iv.setGravity(17);
        this.text_iv.setMinTextSize(5.0f);
        this.rel_artv.setLayoutParams(layoutParams6);
        this.rel_artv.addView(this.text_iv);
        addView(this.rel_artv);
        this.textRelative.setLayoutParams(layoutParams7);
        addView(this.textRelative);
        this.rel_artv.setVisibility(View.INVISIBLE);
        addView(this.border_iv);
        this.border_iv.setLayoutParams(layoutParams9);
        this.border_iv.setTag("border_iv");
        addView(this.rotate_iv);
        this.rotate_iv.setLayoutParams(layoutParams10);
        this.rotate_iv.setOnTouchListener(this.rTouchListener);
        addView(this.delete_iv);
        this.delete_iv.setLayoutParams(layoutParams8);
        this.delete_iv.setOnClickListener(new OnClickListener() { 
            @Override 
            public void onClick(View view) {
                final ViewGroup viewGroup = (ViewGroup) AutofitTextRel.this.getParent();
                AutofitTextRel.this.zoomInScale.setAnimationListener(new Animation.AnimationListener() { 
                    @Override 
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override 
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override 
                    public void onAnimationEnd(Animation animation) {
                        viewGroup.removeView(AutofitTextRel.this);
                    }
                });
                AutofitTextRel.this.background_iv.startAnimation(AutofitTextRel.this.zoomInScale);
                AutofitTextRel.this.textRelative.startAnimation(AutofitTextRel.this.zoomInScale);
                AutofitTextRel.this.setBorderVisibility(false);
                if (AutofitTextRel.this.listener != null) {
                    AutofitTextRel.this.listener.onDelete();
                }
            }
        });
        this.edit_iv.setOnClickListener(new OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AutofitTextRel.this.setBorderVisibility(false);
                if (AutofitTextRel.this.listener != null) {
                    AutofitTextRel.this.listener.onEditText();
                }
            }
        });
        addView(this.scale_iv);
        addView(this.edit_iv);
        this.scale_iv.setLayoutParams(layoutParams4);
        this.scale_iv.setTag("scale_iv");
        this.scale_iv.setOnTouchListener(this.mTouchListener1);
        this.rotation = getRotation();
        this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_anim);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_out);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_in);
        initGD();
        this.isMultiTouchEnabled = setDefaultTouchListener(true);
    }

    public boolean setDefaultTouchListener(boolean z) {
        if (z) {
            this.lockStatus = "UNLOCKED";
            setOnTouchListener(new MultiTouchListener(this.context).enableRotation(true).setOnTouchCallbackListener(this).setGestureListener(this.gd));
            return true;
        }
        this.lockStatus = "LOCKED";
        setOnTouchListener(null);
        return false;
    }



    class AnonymousClass5 implements Runnable {
        AnonymousClass5() {
        }

        @Override 
        public void run() {
            AutofitTextRel.this.text_iv.requestLayout();
            AutofitTextRel.this.text_iv.postInvalidate();
            AutofitTextRel.this.rel_artv.post(new Runnable() { 
                @Override 
                public void run() {
                    AutofitTextRel.this.rel_artv.requestLayout();
                    AutofitTextRel.this.rel_artv.postInvalidate();
                    AutofitTextRel.this.post(new Runnable() { 
                        @Override 
                        public void run() {
                            AutofitTextRel.this.requestLayout();
                            AutofitTextRel.this.postInvalidate();
                        }
                    });
                }
            });
        }
    }

    public void refreshText() {
        this.text_iv.post(new AnonymousClass5());
    }

    public void setBorderVisibility(boolean z) {
        this.isBorderVisible = z;
        if (!z) {
            this.border_iv.setVisibility(View.GONE);
            this.scale_iv.setVisibility(View.GONE);
            this.delete_iv.setVisibility(View.GONE);
            this.rotate_iv.setVisibility(View.GONE);
            this.edit_iv.setVisibility(View.GONE);
            setBackgroundResource(0);
        } else if (this.border_iv.getVisibility() != 0) {
            this.border_iv.setVisibility(View.VISIBLE);
            this.scale_iv.setVisibility(View.VISIBLE);
            this.delete_iv.setVisibility(View.VISIBLE);
            this.rotate_iv.setVisibility(View.VISIBLE);
            this.edit_iv.setVisibility(View.VISIBLE);
            this.textRelative.startAnimation(this.scale);
            this.textRelative.invalidate();
        }
    }

    public boolean getBorderVisibility() {
        return this.isBorderVisible;
    }

    public void setText(String str) {
        this.text = str;
        this.text_iv.setText(str);
        this.textRelative.post(new Runnable() { 
            @Override 
            public void run() {
                AutofitTextRel.this.textRelative.startAnimation(AutofitTextRel.this.zoomOutScale);
            }
        });
    }

    public String getText() {
        return this.text_iv.getText().toString();
    }

    public void setTextFont(String str) {
        Typeface typeface;
        try {
            if (!str.equals("default") && !str.equals("")) {
                typeface = Typeface.createFromAsset(this.context.getAssets(), str);
                this.text_iv.setTypeface(typeface);
                this.fontName = str;
                this.textRelative.invalidate();
            }
            typeface = Typeface.DEFAULT;
            this.text_iv.setTypeface(typeface);
            this.fontName = str;
            this.textRelative.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTextColor() {
        return this.tColor;
    }

    public int getTextAlpha() {
        return this.tAlpha;
    }

    public int getBgAlpha() {
        return this.bgAlpha;
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setTextColor(int i) {
        this.text_iv.setTextColor(i);
        this.tColor = i;
        this.textRelative.invalidate();
    }

    public void setTextAlpha(int i) {
        this.text_iv.setAlpha(i / 100.0f);
        this.tAlpha = i;
        this.textRelative.invalidate();
    }

    public void setTextShadowColor(int i) {
        this.shadowColor = i;
        this.text_iv.setShadowLayer(this.shadowProg, 0.0f, 0.0f, i);
        this.textRelative.invalidate();
    }

    public int getTextShadowColor() {
        return this.shadowColor;
    }

    public void setTextShadowProg(int i) {
        this.shadowProg = i;
        this.text_iv.setShadowLayer(i, 0.0f, 0.0f, this.shadowColor);
        this.textRelative.invalidate();
    }

    public int getTextShadowProg() {
        return this.shadowProg;
    }

    public void setBgDrawable(String str) {
        this.bgDrawable = str;
        this.bgColor = 0;
        this.background_iv.setImageBitmap(getTiledBitmap(this.context, getResources().getIdentifier(str, "drawable", this.context.getPackageName()), this.wi, this.he));
        this.background_iv.setBackgroundColor(this.bgColor);
    }

    public String getBgDrawable() {
        return this.bgDrawable;
    }

    public void setBgColor(int i) {
        this.bgDrawable = "0";
        this.bgColor = i;
        this.background_iv.setImageBitmap(null);
        this.background_iv.setBackgroundColor(i);
    }

    public int getBgColor() {
        return this.bgColor;
    }

    public void setBgAlpha(int i) {
        this.background_iv.setAlpha(i / 255.0f);
        this.bgAlpha = i;
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

    public void setTextGravity(String str) {
        this.txtGravity = str;
        if (str == null) {
            this.text_iv.setGravity(17);
        } else if (str.equals("L")) {
            this.text_iv.setGravity(19);
        } else if (str.equals("R")) {
            this.text_iv.setGravity(21);
        } else {
            this.text_iv.setGravity(17);
        }
        this.textRelative.invalidate();
    }

    public String getTextGravity() {
        return this.txtGravity;
    }

    public void setTextRotateProg(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.xRotateProg = i4;
        this.yRotateProg = i5;
        this.zRotateProg = i6;
        this.progress = i7;
        applyTransformation(i, i2, i3, i7);
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

    public int getCurveRotateProg() {
        return this.progress;
    }

    public void setTextCurveRotateProg(int i) {
        this.progress = i;
        this.prog_60 = (i * 60) / 100;
        this.prog_40 = (i * 40) / 100;
        this.prog_50 = (i * 50) / 100;
        this.prog_80 = (i * 80) / 100;
        this.prog_20 = (i * 20) / 100;
        this.textRelative.setTextCurveRotateProg(i);
        this.textRelative.invalidate();
    }

    public void applyTransformation(int i, int i2, int i3, int i4) {
        this.textRelative.setRotationX(i);
        this.textRelative.setRotationY(i2);
        this.textRelative.setTextCurveRotateProg(i4);
        setVisibility(0);
        this.textRelative.setVisibility(View.VISIBLE);
        this.rel_artv.requestLayout();
        this.rel_artv.postInvalidate();
        this.textRelative.requestLayout();
        this.textRelative.postInvalidate();
        this.textRelative.invalidate();
        requestLayout();
        postInvalidate();
    }

    public TextInfo getTextInfo() {
        TextInfo textInfo = new TextInfo();
        textInfo.setPOS_X(getX());
        textInfo.setPOS_Y(getY());
        textInfo.setWIDTH(this.wi);
        textInfo.setHEIGHT(this.he);
        textInfo.setTEXT(this.text);
        textInfo.setFONT_NAME(this.fontName);
        textInfo.setTEXT_COLOR(this.tColor);
        textInfo.setTEXT_ALPHA(this.tAlpha);
        textInfo.setSHADOW_COLOR(this.shadowColor);
        textInfo.setSHADOW_PROG(this.shadowProg);
        textInfo.setBG_COLOR(this.bgColor);
        textInfo.setBG_DRAWABLE(this.bgDrawable);
        textInfo.setBG_ALPHA(this.bgAlpha);
        textInfo.setROTATION(getRotation());
        textInfo.setXRotateProg(this.xRotateProg);
        textInfo.setYRotateProg(this.yRotateProg);
        textInfo.setZRotateProg(this.zRotateProg);
        textInfo.setCurveRotateProg(this.progress);
        textInfo.setFIELD_ONE(this.field_one);
        textInfo.setFIELD_TWO(this.field_two);
        textInfo.setFIELD_THREE(this.lockStatus);
        textInfo.setFIELD_FOUR(this.field_four);
        textInfo.setTEXT_GRAVITY(this.txtGravity);
        return textInfo;
    }

    public void setTextInfo(TextInfo textInfo, boolean z) {
        this.wi = textInfo.getWIDTH();
        this.he = textInfo.getHEIGHT();
        this.text = textInfo.getTEXT();
        this.fontName = textInfo.getFONT_NAME();
        this.tColor = textInfo.getTEXT_COLOR();
        this.tAlpha = textInfo.getTEXT_ALPHA();
        this.shadowColor = textInfo.getSHADOW_COLOR();
        this.shadowProg = textInfo.getSHADOW_PROG();
        this.bgColor = textInfo.getBG_COLOR();
        this.bgDrawable = textInfo.getBG_DRAWABLE();
        this.bgAlpha = textInfo.getBG_ALPHA();
        this.rotation = textInfo.getROTATION();
        this.field_two = textInfo.getFIELD_TWO();
        this.txtGravity = textInfo.getTEXT_GRAVITY();
        this.lockStatus = textInfo.getFIELD_THREE();
        this.xRotateProg = textInfo.getXRotateProg();
        this.yRotateProg = textInfo.getYRotateProg();
        this.zRotateProg = textInfo.getZRotateProg();
        this.progress = textInfo.getCurveRotateProg();
        int i = this.bgColor;
        if (i != 0) {
            setBgColor(i);
        } else {
            this.background_iv.setBackgroundColor(0);
        }
        if (this.bgDrawable.equals("0")) {
            this.background_iv.setImageBitmap(null);
        } else {
            setBgDrawable(this.bgDrawable);
        }
        setBgAlpha(this.bgAlpha);
        setText(this.text);
        setTextFont(this.fontName);
        setTextColor(this.tColor);
        setTextAlpha(this.tAlpha);
        setTextShadowColor(this.shadowColor);
        setTextShadowProg(this.shadowProg);
        int i2 = this.progress;
        if (i2 == 250) {
            applyTransformation(45 - this.xRotateProg, 45 - this.yRotateProg, 180 - this.zRotateProg, 0);
        } else {
            applyTransformation(45 - this.xRotateProg, 45 - this.yRotateProg, 180 - this.zRotateProg, i2);
        }
        setRotation(textInfo.getROTATION());
        setTextGravity(this.txtGravity);
        if (this.field_two.equals("")) {
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(textInfo.getPOS_X());
            setY(textInfo.getPOS_Y());
        } else {
            String[] split = this.field_two.split(",");
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            ((LayoutParams) getLayoutParams()).leftMargin = parseInt;
            ((LayoutParams) getLayoutParams()).topMargin = parseInt2;
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(textInfo.getPOS_X() + (parseInt * (-1)));
            setY(textInfo.getPOS_Y() + (parseInt2 * (-1)));
        }
        if (this.lockStatus.equals("LOCKED")) {
            this.isMultiTouchEnabled = setDefaultTouchListener(false);
        } else {
            this.isMultiTouchEnabled = setDefaultTouchListener(true);
        }
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

    public int dpToPx(Context context, int i) {
        context.getResources();
        return (int) (i * Resources.getSystem().getDisplayMetrics().density);
    }

    private Bitmap getTiledBitmap(Context context, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), i, new BitmapFactory.Options()), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    private void initGD() {
        this.gd = new GestureDetector(this.context, new GestureDetector.SimpleOnGestureListener() { 
            @Override 
            public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                return true;
            }

            @Override 
            public boolean onDown(MotionEvent motionEvent) {
                return true;
            }

            @Override 
            public boolean onDoubleTap(MotionEvent motionEvent) {
                if (AutofitTextRel.this.listener != null) {
                    AutofitTextRel.this.listener.onDoubleTap();
                    return true;
                }
                return true;
            }

            @Override 
            public void onLongPress(MotionEvent motionEvent) {
                super.onLongPress(motionEvent);
            }
        });
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
