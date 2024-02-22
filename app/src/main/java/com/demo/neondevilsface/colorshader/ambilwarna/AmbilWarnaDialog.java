package com.demo.neondevilsface.colorshader.ambilwarna;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.ExifInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.demo.neondevilsface.R;

import java.util.List;


public class AmbilWarnaDialog {
    ColorHistoryArrayAdapter colorAdapter;
    List<Integer> colorHistoryStack;
    ListView colorListView;
    final float[] currentColorHsv;
    public int currentColorRgb;
    final AlertDialog dialog;
    final OnAmbilWarnaListener listener;
    final ViewGroup viewContainer;
    final ImageView viewCursor;
    final View viewHue;
    final View viewNewColor;
    final View viewOldColor;
    final AmbilWarnaKotak viewSatVal;
    final ImageView viewTarget;
    public boolean colorChoosen = false;
    public boolean fromStack = false;

    
    public interface OnAmbilWarnaListener {
        void onCancel(AmbilWarnaDialog ambilWarnaDialog);

        void onOk(AmbilWarnaDialog ambilWarnaDialog, int i, boolean z);
    }

    public AmbilWarnaDialog(Context context, int i, OnAmbilWarnaListener onAmbilWarnaListener, List<Integer> list) {
        float[] fArr = new float[3];
        this.currentColorHsv = fArr;
        this.colorHistoryStack = list;
        this.listener = onAmbilWarnaListener;
        Color.colorToHSV(i, fArr);
        int HSVToColor = Color.HSVToColor(fArr);
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        Log.e("Color1", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(HSVToColor);
        Log.e("Color2", sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(fArr[0]);
        Log.e("Hue", sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append(fArr[1]);
        Log.e(ExifInterface.TAG_SATURATION, sb4.toString());
        StringBuilder sb5 = new StringBuilder();
        sb5.append(fArr[2]);
        Log.e("Value", sb5.toString());
        final View inflate = LayoutInflater.from(context).inflate(R.layout.ambilwarna_dialog, (ViewGroup) null);
        View findViewById = inflate.findViewById(R.id.ambilwarna_viewHue);
        this.viewHue = findViewById;
        AmbilWarnaKotak ambilWarnaKotak = (AmbilWarnaKotak) inflate.findViewById(R.id.ambilwarna_viewSatBri);
        this.viewSatVal = ambilWarnaKotak;
        this.viewCursor = (ImageView) inflate.findViewById(R.id.ambilwarna_cursor);
        View findViewById2 = inflate.findViewById(R.id.ambilwarna_warnaLama);
        this.viewOldColor = findViewById2;
        View findViewById3 = inflate.findViewById(R.id.ambilwarna_warnaBaru);
        this.viewNewColor = findViewById3;
        this.viewTarget = (ImageView) inflate.findViewById(R.id.ambilwarna_target);
        this.viewContainer = (ViewGroup) inflate.findViewById(R.id.ambilwarna_viewContainer);
        ambilWarnaKotak.setHue(getHue());
        findViewById2.setBackgroundColor(i);
        findViewById3.setBackgroundColor(i);
        findViewById.setOnTouchListener(new View.OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 2 || motionEvent.getAction() == 0 || motionEvent.getAction() == 1) {
                    float y = motionEvent.getY();
                    if (y < 0.0f) {
                        y = 0.0f;
                    }
                    if (y > AmbilWarnaDialog.this.viewHue.getMeasuredHeight()) {
                        y = AmbilWarnaDialog.this.viewHue.getMeasuredHeight() - 0.001f;
                    }
                    float measuredHeight = 360.0f - ((360.0f / AmbilWarnaDialog.this.viewHue.getMeasuredHeight()) * y);
                    AmbilWarnaDialog.this.setHue(measuredHeight != 360.0f ? measuredHeight : 0.0f);
                    AmbilWarnaDialog.this.viewSatVal.setHue(AmbilWarnaDialog.this.getHue());
                    AmbilWarnaDialog.this.moveCursor();
                    AmbilWarnaDialog.this.viewNewColor.setBackgroundColor(AmbilWarnaDialog.this.getColor());
                    AmbilWarnaDialog.this.colorChoosen = true;
                    AmbilWarnaDialog.this.fromStack = false;
                    return true;
                }
                return false;
            }
        });
        ambilWarnaKotak.setOnTouchListener(new View.OnTouchListener() { 
            @Override 
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == 2 || motionEvent.getAction() == 0 || motionEvent.getAction() == 1) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    if (x < 0.0f) {
                        x = 0.0f;
                    }
                    if (x > AmbilWarnaDialog.this.viewSatVal.getMeasuredWidth()) {
                        x = AmbilWarnaDialog.this.viewSatVal.getMeasuredWidth();
                    }
                    if (y < 0.0f) {
                        y = 0.0f;
                    }
                    if (y > AmbilWarnaDialog.this.viewSatVal.getMeasuredHeight()) {
                        y = AmbilWarnaDialog.this.viewSatVal.getMeasuredHeight();
                    }
                    AmbilWarnaDialog.this.setSat((1.0f / viewSatVal.getMeasuredWidth()) * x);
                    AmbilWarnaDialog.this.setVal(1.0f - ((1.0f / viewSatVal.getMeasuredHeight()) * y));
                    AmbilWarnaDialog.this.colorChoosen = true;
                    AmbilWarnaDialog.this.fromStack = false;
                    AmbilWarnaDialog.this.moveTarget();
                    AmbilWarnaDialog.this.viewNewColor.setBackgroundColor(AmbilWarnaDialog.this.getColor());
                    return true;
                }
                return false;
            }
        });
        AlertDialog create = new AlertDialog.Builder(context).setPositiveButton("ok", new DialogInterface.OnClickListener() { 
            @Override 
            public void onClick(DialogInterface dialogInterface, int i2) {
                if (AmbilWarnaDialog.this.listener != null) {
                    int color = AmbilWarnaDialog.this.getColor();
                    if (AmbilWarnaDialog.this.fromStack) {
                        color = AmbilWarnaDialog.this.currentColorRgb;
                    }
                    OnAmbilWarnaListener onAmbilWarnaListener2 = AmbilWarnaDialog.this.listener;
                    AmbilWarnaDialog ambilWarnaDialog = AmbilWarnaDialog.this;
                    onAmbilWarnaListener2.onOk(ambilWarnaDialog, color, ambilWarnaDialog.colorChoosen);
                }
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() { 
            @Override 
            public void onClick(DialogInterface dialogInterface, int i2) {
                if (AmbilWarnaDialog.this.listener != null) {
                    AmbilWarnaDialog.this.listener.onCancel(AmbilWarnaDialog.this);
                }
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { 
            @Override 
            public void onCancel(DialogInterface dialogInterface) {
                if (AmbilWarnaDialog.this.listener != null) {
                    AmbilWarnaDialog.this.listener.onCancel(AmbilWarnaDialog.this);
                }
            }
        }).create();
        this.dialog = create;
        create.setView(inflate, 0, 0, 0, 0);
        inflate.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { 
            @Override 
            public void onGlobalLayout() {
                AmbilWarnaDialog.this.moveCursor();
                AmbilWarnaDialog.this.moveTarget();
                inflate.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        ListView listView = (ListView) inflate.findViewById(R.id.ambilwarna_miyaw);
        this.colorListView = listView;
        listView.setCacheColorHint(0);
        this.colorListView.setAdapter((ListAdapter) this.colorAdapter);
        this.colorListView.setDividerHeight(0);
        this.colorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { 
            @Override 
            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
                if (AmbilWarnaDialog.this.colorHistoryStack.size() > 0) {
                    int intValue = AmbilWarnaDialog.this.colorHistoryStack.get((AmbilWarnaDialog.this.colorHistoryStack.size() - i2) - 1).intValue();
                    float[] fArr2 = new float[3];
                    Color.colorToHSV(intValue, fArr2);
                    AmbilWarnaDialog.this.setHue(fArr2[0]);
                    Log.e("hsv", " " + fArr2);
                    AmbilWarnaDialog.this.setSat(fArr2[1]);
                    AmbilWarnaDialog.this.setVal(fArr2[2]);
                    AmbilWarnaDialog.this.viewSatVal.setHue(AmbilWarnaDialog.this.getHue());
                    AmbilWarnaDialog.this.currentColorRgb = intValue;
                    AmbilWarnaDialog.this.moveCursor();
                    AmbilWarnaDialog.this.moveTarget();
                    AmbilWarnaDialog.this.viewNewColor.setBackgroundColor(intValue);
                    AmbilWarnaDialog.this.colorChoosen = true;
                    AmbilWarnaDialog.this.fromStack = true;
                }
            }
        });
    }

    public void moveCursor() {
        float measuredHeight = this.viewHue.getMeasuredHeight() - ((getHue() * this.viewHue.getMeasuredHeight()) / 360.0f);
        if (measuredHeight == this.viewHue.getMeasuredHeight()) {
            measuredHeight = 0.0f;
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.viewCursor.getLayoutParams();
        layoutParams.leftMargin = (int) ((this.viewHue.getLeft() - Math.floor(this.viewCursor.getMeasuredWidth() / 2)) - this.viewContainer.getPaddingLeft());
        layoutParams.topMargin = (int) (((this.viewHue.getTop() + measuredHeight) - Math.floor(this.viewCursor.getMeasuredHeight() / 2)) - this.viewContainer.getPaddingTop());
        this.viewCursor.setLayoutParams(layoutParams);
    }

    public void moveTarget() {
        float sat = getSat() * this.viewSatVal.getMeasuredWidth();
        float val = (1.0f - getVal()) * this.viewSatVal.getMeasuredHeight();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.viewTarget.getLayoutParams();
        layoutParams.leftMargin = (int) (((this.viewSatVal.getLeft() + sat) - Math.floor(this.viewTarget.getMeasuredWidth() / 2)) - this.viewContainer.getPaddingLeft());
        layoutParams.topMargin = (int) (((this.viewSatVal.getTop() + val) - Math.floor(this.viewTarget.getMeasuredHeight() / 2)) - this.viewContainer.getPaddingTop());
        this.viewTarget.setLayoutParams(layoutParams);
    }

    public int getColor() {
        return Color.HSVToColor(this.currentColorHsv);
    }

    public float getHue() {
        return this.currentColorHsv[0];
    }

    private float getSat() {
        return this.currentColorHsv[1];
    }

    private float getVal() {
        return this.currentColorHsv[2];
    }

    public void setHue(float f) {
        this.currentColorHsv[0] = f;
    }

    public void setSat(float f) {
        this.currentColorHsv[1] = f;
    }

    public void setVal(float f) {
        this.currentColorHsv[2] = f;
    }

    public void show() {
        this.dialog.show();
    }

    public AlertDialog getDialog() {
        return this.dialog;
    }
}
