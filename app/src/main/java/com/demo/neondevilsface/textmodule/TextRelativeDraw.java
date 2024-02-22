package com.demo.neondevilsface.textmodule;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import java.util.ArrayList;


public class TextRelativeDraw extends View {
    private Context context;
    int progress;
    RelativeLayout rel_artv;
    int x1;
    int x10;
    int x11;
    int x12;
    int x13;
    int x14;
    int x15;
    int x16;
    int x17;
    int x18;
    int x19;
    int x2;
    int x20;
    int x21;
    int x22;
    int x23;
    int x24;
    int x25;
    int x26;
    int x27;
    int x28;
    int x29;
    int x3;
    int x30;
    int x31;
    int x32;
    int x33;
    int x34;
    int x35;
    int x36;
    int x37;
    int x38;
    int x39;
    int x4;
    int x40;
    int x41;
    int x42;
    int x43;
    int x44;
    int x45;
    int x46;
    int x47;
    int x48;
    int x49;
    int x5;
    int x50;
    int x51;
    int x52;
    int x53;
    int x54;
    int x55;
    int x56;
    int x57;
    int x58;
    int x59;
    int x6;
    int x60;
    int x61;
    int x62;
    int x63;
    int x64;
    int x65;
    int x66;
    int x67;
    int x68;
    int x69;
    int x7;
    int x70;
    int x71;
    int x72;
    int x73;
    int x74;
    int x75;
    int x76;
    int x77;
    int x78;
    int x79;
    int x8;
    int x80;
    int x81;
    int x9;
    int y1;
    int y10;
    int y11;
    int y12;
    int y13;
    int y14;
    int y15;
    int y16;
    int y17;
    int y18;
    int y19;
    int y2;
    int y20;
    int y21;
    int y22;
    int y23;
    int y24;
    int y25;
    int y26;
    int y27;
    int y28;
    int y29;
    int y3;
    int y30;
    int y31;
    int y32;
    int y33;
    int y34;
    int y35;
    int y36;
    int y37;
    int y38;
    int y39;
    int y4;
    int y40;
    int y41;
    int y42;
    int y43;
    int y44;
    int y45;
    int y46;
    int y47;
    int y48;
    int y49;
    int y5;
    int y50;
    int y51;
    int y52;
    int y53;
    int y54;
    int y55;
    int y56;
    int y57;
    int y58;
    int y59;
    int y6;
    int y60;
    int y61;
    int y62;
    int y63;
    int y64;
    int y65;
    int y66;
    int y67;
    int y68;
    int y69;
    int y7;
    int y70;
    int y71;
    int y72;
    int y73;
    int y74;
    int y75;
    int y76;
    int y77;
    int y78;
    int y79;
    int y8;
    int y80;
    int y81;
    int y9;

    public TextRelativeDraw(Context context, RelativeLayout relativeLayout) {
        super(context);
        this.progress = 0;
        this.context = context;
        this.rel_artv = relativeLayout;
        init(context);
    }

    public void init(Context context) {
        this.context = context;
    }

    public void setTextCurveRotateProg(int i) {
        this.progress = i;
        invalidate();
    }

    @Override 
    public void onDraw(Canvas canvas) {
        if (this.rel_artv != null) {
            Paint paint = new Paint();
            paint.setColor(-16711936);
            Bitmap createBitmap = Bitmap.createBitmap(this.rel_artv.getWidth(), this.rel_artv.getHeight(), Bitmap.Config.ARGB_8888);
            this.rel_artv.draw(new Canvas(createBitmap));
            int width = createBitmap.getWidth();
            int height = createBitmap.getHeight();
            int i = width / 8;
            int i2 = height / 8;
            this.x1 = 0;
            this.y1 = 0;
            int i3 = (i * 1) + 0;
            this.x2 = i3;
            this.y2 = 0;
            int i4 = (i * 2) + 0;
            this.x3 = i4;
            this.y3 = 0;
            int i5 = (i * 3) + 0;
            this.x4 = i5;
            this.y4 = 0;
            int i6 = (i * 4) + 0;
            this.x5 = i6;
            this.y5 = 0;
            int i7 = (i * 5) + 0;
            this.x6 = i7;
            this.y6 = 0;
            int i8 = (i * 6) + 0;
            this.x7 = i8;
            this.y7 = 0;
            int i9 = (i * 7) + 0;
            this.x8 = i9;
            this.y8 = 0;
            int i10 = width + 0;
            this.x9 = i10;
            this.y9 = 0;
            this.x10 = 0;
            int i11 = i2 + 0;
            this.y10 = i11;
            this.x11 = i3;
            this.y11 = i11;
            this.x12 = i4;
            this.y12 = i11;
            this.x13 = i5;
            this.y13 = i11;
            this.x14 = i6;
            this.y14 = i11;
            this.x15 = i7;
            this.y15 = i11;
            this.x16 = i8;
            this.y16 = i11;
            this.x17 = i9;
            this.y17 = i11;
            this.x18 = i10;
            this.y18 = i11;
            this.x19 = 0;
            int i12 = (i2 * 2) + 0;
            this.y19 = i12;
            this.x20 = i3;
            this.y20 = i12;
            this.x21 = i4;
            this.y21 = i12;
            this.x22 = i5;
            this.y22 = i12;
            this.x23 = i6;
            this.y23 = i12;
            this.x24 = i7;
            this.y24 = i12;
            this.x25 = i8;
            this.y25 = i12;
            this.x26 = i9;
            this.y26 = i12;
            this.x27 = i10;
            this.y27 = i12;
            this.x28 = 0;
            int i13 = (i2 * 3) + 0;
            this.y28 = i13;
            this.x29 = i3;
            this.y29 = i13;
            this.x30 = i4;
            this.y30 = i13;
            this.x31 = i5;
            this.y31 = i13;
            this.x32 = i6;
            this.y32 = i13;
            this.x33 = i7;
            this.y33 = i13;
            this.x34 = i8;
            this.y34 = i13;
            this.x35 = i9;
            this.y35 = i13;
            this.x36 = i10;
            this.y36 = i13;
            this.x37 = 0;
            int i14 = (i2 * 4) + 0;
            this.y37 = i14;
            this.x38 = i3;
            this.y38 = i14;
            this.x39 = i4;
            this.y39 = i14;
            this.x40 = i5;
            this.y40 = i14;
            this.x41 = i6;
            this.y41 = i14;
            this.x42 = i7;
            this.y42 = i14;
            this.x43 = i8;
            this.y43 = i14;
            this.x44 = i9;
            this.y44 = i14;
            this.x45 = i10;
            this.y45 = i14;
            this.x46 = 0;
            int i15 = (i2 * 5) + 0;
            this.y46 = i15;
            this.x47 = i3;
            this.y47 = i15;
            this.x48 = i4;
            this.y48 = i15;
            this.x49 = i5;
            this.y49 = i15;
            this.x50 = i6;
            this.y50 = i15;
            this.x51 = i7;
            this.y51 = i15;
            this.x52 = i8;
            this.y52 = i15;
            this.x53 = i9;
            this.y53 = i15;
            this.x54 = i10;
            this.y54 = i15;
            this.x55 = 0;
            int i16 = (i2 * 6) + 0;
            this.y55 = i16;
            this.x56 = i3;
            this.y56 = i16;
            this.x57 = i4;
            this.y57 = i16;
            this.x58 = i5;
            this.y58 = i16;
            this.x59 = i6;
            this.y59 = i16;
            this.x60 = i7;
            this.y60 = i16;
            this.x61 = i8;
            this.y61 = i16;
            this.x62 = i9;
            this.y62 = i16;
            this.x63 = i10;
            this.y63 = i16;
            this.x64 = 0;
            int i17 = (i2 * 7) + 0;
            this.y64 = i17;
            this.x65 = i3;
            this.y65 = i17;
            this.x66 = i4;
            this.y66 = i17;
            this.x67 = i5;
            this.y67 = i17;
            this.x68 = i6;
            this.y68 = i17;
            this.x69 = i7;
            this.y69 = i17;
            this.x70 = i8;
            this.y70 = i17;
            this.x71 = i9;
            this.y71 = i17;
            this.x72 = i10;
            this.y72 = i17;
            this.x73 = 0;
            int i18 = height + 0;
            this.y73 = i18;
            this.x74 = i3;
            this.y74 = i18;
            this.x75 = i4;
            this.y75 = i18;
            this.x76 = i5;
            this.y76 = i18;
            this.x77 = i6;
            this.y77 = i18;
            this.x78 = i7;
            this.y78 = i18;
            this.x79 = i8;
            this.y79 = i18;
            this.x80 = i9;
            this.y80 = i18;
            this.x81 = i10;
            this.y81 = i18;
            Path[] pathArr = {mPath(this.x1, this.y1, this.x9, this.y9, this.progress, canvas, paint), mPath(this.x10, this.y10, this.x18, this.y18, this.progress, canvas, paint), mPath(this.x19, this.y19, this.x27, this.y27, this.progress, canvas, paint), mPath(this.x28, this.y28, this.x36, this.y36, this.progress, canvas, paint), mPath(this.x37, this.y37, this.x45, this.y45, this.progress, canvas, paint), mPath(this.x46, this.y46, this.x54, this.y54, this.progress, canvas, paint), mPath(this.x55, this.y55, this.x63, this.y63, this.progress, canvas, paint), mPath(this.x64, this.y64, this.x72, this.y72, this.progress, canvas, paint), mPath(this.x73, this.y73, this.x81, this.y81, this.progress, canvas, paint)};
            ArrayList arrayList = new ArrayList();
            for (int i19 = 0; i19 < 9; i19++) {
                PathMeasure pathMeasure = new PathMeasure(pathArr[i19], false);
                float[] fArr = {0.0f, 0.0f};
                for (float f = 0.0f; f <= 1.0f; f += 0.125f) {
                    pathMeasure.getPosTan(pathMeasure.getLength() * f, fArr, null);
                    arrayList.add(Float.valueOf(fArr[0]));
                    arrayList.add(Float.valueOf(fArr[1]));
                }
            }
            int size = arrayList.size();
            float[] fArr2 = new float[size];
            for (int i20 = 0; i20 < size; i20++) {
                fArr2[i20] = ((Float) arrayList.get(i20)).floatValue();
            }
            canvas.drawBitmapMesh(createBitmap, 8, 8, fArr2, 0, null, 0, null);
            return;
        }
        Log.e("not show", "visiblity");
    }

    public int getClosestResampleSize(int i, int i2, int i3) {
        int max = Math.max(i, i2);
        int i4 = 1;
        while (true) {
            if (i4 >= Integer.MAX_VALUE) {
                break;
            } else if (i4 * i3 > max) {
                i4--;
                break;
            } else {
                i4++;
            }
        }
        if (i4 > 0) {
            return i4;
        }
        return 1;
    }

    public Path mPath(int i, int i2, int i3, int i4, int i5, Canvas canvas, Paint paint) {
        int i6 = ((i3 - i) / 2) + i;
        int i7 = ((i4 - i2) / 2) + i2;

        Path path = new Path();
        double radians = Math.toRadians((Math.atan2(i7 - i2, i6 - i) * 57.29577951308232d) - 90.0d);
        double d = i5;
        float f = i;
        float f2 = i2;
        path.moveTo(f, f2);
        path.cubicTo(f, f2, (float) (((i3 - i) / 2) + i + (Math.cos(radians) * d)), (float) (((i4 - i2) / 2) + i2 + (d * Math.sin(radians))), i3, i4);
        return path;
    }

    public int dpToPx(Context context, int i) {
        context.getResources();
        return (int) (i * Resources.getSystem().getDisplayMetrics().density);
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int i, int i2) {
        float f;
        float f2 = i;
        float f3 = i2;
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        Log.i("testings", f2 + "  " + f3 + "  and  " + width + "  " + height);
        float f4 = width / height;
        float f5 = height / width;
        if (width > f2) {
            f = f5 * f2;
            Log.i("testings", "if (wd > wr) " + f2 + "  " + f);
            if (f > f3) {
                f2 = f3 * f4;
                Log.i("testings", "  if (he > hr) " + f2 + "  " + f3);
            } else {
                Log.i("testings", " in else " + f2 + "  " + f);
                f3 = f;
            }
        } else if (height > f3) {
            float f6 = f4 * f3;
            Log.i("testings", "  if (he > hr) " + f6 + "  " + f3);
            if (f6 > f2) {
                f3 = f2 * f5;
            } else {
                Log.i("testings", " in else " + f6 + "  " + f3);
                f2 = f6;
            }
        } else if (f4 > 0.75f) {
            f3 = f2 * f5;
            Log.i("testings", " if (rat1 > .75f) ");
        } else if (f5 > 1.5f) {
            f2 = f3 * f4;
            Log.i("testings", " if (rat2 > 1.5f) ");
        } else {
            f = f5 * f2;
            Log.i("testings", " in else ");
            if (f > f3) {
                f2 = f3 * f4;
                Log.i("testings", "  if (he > hr) " + f2 + "  " + f3);
            } else {
                Log.i("testings", " in else " + f2 + "  " + f);
                f3 = f;
            }
        }
        return Bitmap.createScaledBitmap(bitmap, (int) f2, (int) f3, false);
    }
}
