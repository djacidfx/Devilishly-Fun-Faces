package com.demo.neondevilsface.textmodule;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import com.demo.neondevilsface.Utility.Constant;


public class AutoResizeTextView extends AppCompatTextView {
    private static final int NO_LINE_LIMIT = -1;
    private final RectF _availableSpaceRect;
    private boolean _initialized;
    private int _maxLines;
    private float _maxTextSize;
    private float _minTextSize;
    private TextPaint _paint;
    private final SizeTester _sizeTester;
    private float _spacingAdd;
    private float _spacingMult;
    private int _widthLimit;
    boolean drawTextBol;
    Camera mCamera;
    Path path;
    private int startAngle;
    private int sweepAngle;
    Paint tPaint;

    
    
    public interface SizeTester {
        int onTestSize(int i, RectF rectF);
    }

    public boolean isValidWordWrap(char c, char c2) {
        return c == ' ' || c == '-';
    }

    public AutoResizeTextView(Context context) {
        this(context, null, 16842884);
    }

    public AutoResizeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public AutoResizeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this._availableSpaceRect = new RectF();
        this._spacingMult = 1.0f;
        this._spacingAdd = 0.0f;
        this._initialized = false;
        this.sweepAngle = 1;
        this.startAngle = 1;
        this.drawTextBol = false;
        Paint paint = new Paint(1);
        this.tPaint = paint;
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.tPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.tPaint.setTextSize(55.0f);
        this._minTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12.0f, getResources().getDisplayMetrics());
        this._maxTextSize = getTextSize();
        this._paint = new TextPaint(getPaint());
        if (this._maxLines == 0) {
            this._maxLines = -1;
        }
        this._sizeTester = new SizeTester() { 
            final RectF textRect = new RectF();

            @Override 
            public int onTestSize(int i2, RectF rectF) {
                String charSequence;
                AutoResizeTextView.this._paint.setTextSize(i2);
                TransformationMethod transformationMethod = AutoResizeTextView.this.getTransformationMethod();
                if (transformationMethod != null) {
                    charSequence = transformationMethod.getTransformation(AutoResizeTextView.this.getText(), AutoResizeTextView.this).toString();
                } else {
                    charSequence = AutoResizeTextView.this.getText().toString();
                }
                if (AutoResizeTextView.this.getMaxLines() == 1) {
                    this.textRect.bottom = AutoResizeTextView.this._paint.getFontSpacing();
                    this.textRect.right = AutoResizeTextView.this._paint.measureText(charSequence);
                } else {
                    StaticLayout staticLayout = new StaticLayout(charSequence, AutoResizeTextView.this._paint, AutoResizeTextView.this._widthLimit, Layout.Alignment.ALIGN_CENTER, AutoResizeTextView.this._spacingMult, AutoResizeTextView.this._spacingAdd, true);
                    if (AutoResizeTextView.this.getMaxLines() != -1 && staticLayout.getLineCount() > AutoResizeTextView.this.getMaxLines()) {
                        return 1;
                    }
                    this.textRect.bottom = staticLayout.getHeight();
                    int lineCount = staticLayout.getLineCount();
                    int i3 = -1;
                    for (int i4 = 0; i4 < lineCount; i4++) {
                        int lineEnd = staticLayout.getLineEnd(i4);
                        if (i4 < lineCount - 1 && lineEnd > 0 && !AutoResizeTextView.this.isValidWordWrap(charSequence.charAt(lineEnd - 1), charSequence.charAt(lineEnd))) {
                            return 1;
                        }
                        if (i3 < staticLayout.getLineRight(i4) - staticLayout.getLineLeft(i4)) {
                            i3 = ((int) staticLayout.getLineRight(i4)) - ((int) staticLayout.getLineLeft(i4));
                        }
                    }
                    this.textRect.right = i3;
                }
                this.textRect.offsetTo(0.0f, 0.0f);
                return rectF.contains(this.textRect) ? -1 : 1;
            }
        };
        this._initialized = true;
    }

    @Override 
    public void setAllCaps(boolean z) {
        super.setAllCaps(z);
        adjustTextSize();
    }

    @Override 
    public void setTypeface(Typeface typeface) {
        super.setTypeface(typeface);
        adjustTextSize();
    }

    @Override 
    public void setTextSize(float f) {
        this._maxTextSize = f;
        adjustTextSize();
    }

    @Override 
    public void setMaxLines(int i) {
        super.setMaxLines(i);
        this._maxLines = i;
        adjustTextSize();
    }

    @Override 
    public int getMaxLines() {
        return this._maxLines;
    }

    @Override 
    public void setSingleLine() {
        super.setSingleLine();
        this._maxLines = 1;
        adjustTextSize();
    }

    @Override 
    public void setSingleLine(boolean z) {
        super.setSingleLine(z);
        if (z) {
            this._maxLines = 1;
        } else {
            this._maxLines = -1;
        }
        adjustTextSize();
    }

    @Override 
    public void setLines(int i) {
        super.setLines(i);
        this._maxLines = i;
        adjustTextSize();
    }

    @Override 
    public void setTextSize(int i, float f) {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        this._maxTextSize = TypedValue.applyDimension(i, f, resources.getDisplayMetrics());
        adjustTextSize();
    }

    @Override 
    public void setLineSpacing(float f, float f2) {
        super.setLineSpacing(f, f2);
        this._spacingMult = f2;
        this._spacingAdd = f;
    }

    public void setMinTextSize(float f) {
        this._minTextSize = f;
        adjustTextSize();
    }

    private void adjustTextSize() {
        if (this._initialized) {
            int i = (int) this._minTextSize;
            int measuredHeight = (getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop();
            int measuredWidth = (getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight();
            this._widthLimit = measuredWidth;
            if (measuredWidth > 0) {
                this._paint = new TextPaint(getPaint());
                this._availableSpaceRect.right = this._widthLimit;
                this._availableSpaceRect.bottom = measuredHeight;
                superSetTextSize(i);
            }
        }
    }

    private void superSetTextSize(int i) {
        super.setTextSize(0, binarySearch(i, (int) this._maxTextSize, this._sizeTester, this._availableSpaceRect));
    }

    private int binarySearch(int i, int i2, SizeTester sizeTester, RectF rectF) {
        int i3 = i2 - 1;
        int i4 = i;
        while (i <= i3) {
            int i5 = (i + i3) >>> 1;
            int onTestSize = sizeTester.onTestSize(i5, rectF);
            if (onTestSize < 0) {
                int i6 = i5 + 1;
                i4 = i;
                i = i6;
            } else if (onTestSize <= 0) {
                return i5;
            } else {
                i4 = i5 - 1;
                i3 = i4;
            }
        }
        return i4;
    }

    @Override 
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        adjustTextSize();
    }

    @Override 
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i == i3 && i2 == i4) {
            return;
        }
        adjustTextSize();
    }

    public static int dpToPx(Context context, int i) {
        context.getResources();
        return (int) (i * Resources.getSystem().getDisplayMetrics().density);
    }

    private void drawTextView(Canvas canvas) {
        this.sweepAngle = Constant.ORIENTATION_180;
        this.startAngle = 270 - (Constant.ORIENTATION_180 / 2);
        this.path = new Path();
        Paint paint = new Paint();
        paint.setColor(-16711936);
        String charSequence = getText().toString();
        getPaint().measureText(charSequence);
        RectF rectF = new RectF(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight());
        this.path.addArc(rectF, this.startAngle, this.sweepAngle);
        canvas.drawOval(rectF, paint);
        canvas.drawTextOnPath(charSequence, this.path, 0.0f, 0.0f, this.tPaint);
    }

    public void setCurvingAngle(int i, float f, float f2, String str) {
        this.drawTextBol = true;
        invalidate();
    }
}
