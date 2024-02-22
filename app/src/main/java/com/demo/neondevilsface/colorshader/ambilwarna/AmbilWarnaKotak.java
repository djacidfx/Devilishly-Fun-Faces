package com.demo.neondevilsface.colorshader.ambilwarna;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.view.ViewCompat;


public class AmbilWarnaKotak extends View {
    final float[] color;
    Shader luar;
    Paint paint;

    public AmbilWarnaKotak(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.color = new float[]{1.0f, 1.0f, 1.0f};
    }

    public AmbilWarnaKotak(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.color = new float[]{1.0f, 1.0f, 1.0f};
    }

    @Override 
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.paint == null) {
            this.paint = new Paint();
            this.luar = new LinearGradient(0.0f, 0.0f, 0.0f, getMeasuredHeight(), -1, (int) ViewCompat.MEASURED_STATE_MASK, Shader.TileMode.CLAMP);
        }
        this.paint.setShader(new ComposeShader(this.luar, new LinearGradient(0.0f, 0.0f, getMeasuredWidth(), 0.0f, -1, Color.HSVToColor(this.color), Shader.TileMode.CLAMP), PorterDuff.Mode.MULTIPLY));
        canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), this.paint);
    }

    public void setHue(float f) {
        this.color[0] = f;
        invalidate();
    }
}
