package com.demo.neondevilsface.glitchfilter;

import android.graphics.Point;
import android.graphics.PointF;
import android.opengl.GLES20;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;


public class GpuImageToneCurveFilterModified extends GPUImageFilter {
    public static final String TONE_CURVE_FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform sampler2D toneCurveTexture;\n uniform highp int filter;\n void main()\n {\n       if(filter==0){           lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n           lowp float redCurveValue = texture2D(toneCurveTexture, vec2(textureColor.r, 0.0)).r;\n           lowp float greenCurveValue = texture2D(toneCurveTexture, vec2(textureColor.g, 0.0)).g;\n           lowp float blueCurveValue = texture2D(toneCurveTexture, vec2(textureColor.b, 0.0)).b;\n           gl_FragColor = vec4(redCurveValue, greenCurveValue, blueCurveValue, textureColor.a);\n       }else if(filter==1){           lowp vec4 tc = texture2D(inputImageTexture, textureCoordinate);\n           tc.r = (tc.r+tc.g+tc.b)/3.0;\n           gl_FragColor = vec4(tc.r,tc.r,tc.r,tc.a);       }else{           lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);           gl_FragColor = vec4(textureColor.r,textureColor.g,textureColor.b,textureColor.a);       }\n }";
    private int filterLoc;
    public int filterType;
    private PointF[] mBlueControlPoints;
    public ArrayList<Float> mBlueCurve;
    private PointF[] mGreenControlPoints;
    public ArrayList<Float> mGreenCurve;
    private PointF[] mRedControlPoints;
    public ArrayList<Float> mRedCurve;
    private PointF[] mRgbCompositeControlPoints;
    public ArrayList<Float> mRgbCompositeCurve;
    public int[] mToneCurveTexture;
    private int mToneCurveTextureUniformLocation;

    public GpuImageToneCurveFilterModified() {
        super(NO_FILTER_VERTEX_SHADER, TONE_CURVE_FRAGMENT_SHADER);
        this.filterType = 0;
        this.mToneCurveTexture = new int[]{-1};
        PointF[] pointFArr = {new PointF(0.0f, 0.0f), new PointF(0.5f, 0.5f), new PointF(1.0f, 1.0f)};
        this.mRgbCompositeControlPoints = pointFArr;
        this.mRedControlPoints = pointFArr;
        this.mGreenControlPoints = pointFArr;
        this.mBlueControlPoints = pointFArr;
    }

    @Override 
    public void onInit() {
        super.onInit();
        this.mToneCurveTextureUniformLocation = GLES20.glGetUniformLocation(getProgram(), "toneCurveTexture");
        GLES20.glActiveTexture(33987);
        GLES20.glGenTextures(1, this.mToneCurveTexture, 0);
        GLES20.glBindTexture(3553, this.mToneCurveTexture[0]);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        int glGetUniformLocation = GLES20.glGetUniformLocation(getProgram(), "filter");
        this.filterLoc = glGetUniformLocation;
        setInteger(glGetUniformLocation, 0);
    }

    @Override 
    public void onInitialized() {
        super.onInitialized();
        setInteger(this.filterLoc, this.filterType);
        setRgbCompositeControlPoints(this.mRgbCompositeControlPoints);
        setRedControlPoints(this.mRedControlPoints);
        setGreenControlPoints(this.mGreenControlPoints);
        setBlueControlPoints(this.mBlueControlPoints);
    }

    @Override 
    public void onDrawArraysPre() {
        if (this.mToneCurveTexture[0] != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, this.mToneCurveTexture[0]);
            GLES20.glUniform1i(this.mToneCurveTextureUniformLocation, 3);
        }
    }

    public void setGrayScale() {
        this.filterType = 1;
    }

    public void setFromCurveFileInputStream(InputStream inputStream) {
        this.filterType = 0;
        try {
            readShort(inputStream);
            short readShort = readShort(inputStream);
            ArrayList arrayList = new ArrayList(readShort);
            for (int i = 0; i < readShort; i++) {
                int readShort2 = readShort(inputStream);
                PointF[] pointFArr = new PointF[readShort2];
                for (int i2 = 0; i2 < readShort2; i2++) {
                    pointFArr[i2] = new PointF(readShort(inputStream) * 0.003921569f, readShort(inputStream) * 0.003921569f);
                }
                arrayList.add(pointFArr);
            }
            inputStream.close();
            this.mRgbCompositeControlPoints = (PointF[]) arrayList.get(0);
            this.mRedControlPoints = (PointF[]) arrayList.get(1);
            this.mGreenControlPoints = (PointF[]) arrayList.get(2);
            this.mBlueControlPoints = (PointF[]) arrayList.get(3);
        } catch (IOException unused) {
        }
    }

    private short readShort(InputStream inputStream) throws IOException {
        return (short) ((inputStream.read() << 8) | inputStream.read());
    }

    public void setRgbCompositeControlPoints(PointF[] pointFArr) {
        this.mRgbCompositeControlPoints = pointFArr;
        this.mRgbCompositeCurve = createSplineCurve(pointFArr);
        updateToneCurveTexture();
    }

    public void setRedControlPoints(PointF[] pointFArr) {
        this.mRedControlPoints = pointFArr;
        this.mRedCurve = createSplineCurve(pointFArr);
        updateToneCurveTexture();
    }

    public void setGreenControlPoints(PointF[] pointFArr) {
        this.mGreenControlPoints = pointFArr;
        this.mGreenCurve = createSplineCurve(pointFArr);
        updateToneCurveTexture();
    }

    public void setBlueControlPoints(PointF[] pointFArr) {
        this.mBlueControlPoints = pointFArr;
        this.mBlueCurve = createSplineCurve(pointFArr);
        updateToneCurveTexture();
    }

    private void updateToneCurveTexture() {
        runOnDraw(new Runnable() { 
            @Override 
            public void run() {
                GLES20.glActiveTexture(33987);
                GLES20.glBindTexture(3553, GpuImageToneCurveFilterModified.this.mToneCurveTexture[0]);
                if (GpuImageToneCurveFilterModified.this.mRedCurve.size() < 256 || GpuImageToneCurveFilterModified.this.mGreenCurve.size() < 256 || GpuImageToneCurveFilterModified.this.mBlueCurve.size() < 256 || GpuImageToneCurveFilterModified.this.mRgbCompositeCurve.size() < 256) {
                    return;
                }
                byte[] bArr = new byte[1024];
                for (int i = 0; i < 256; i++) {
                    int i2 = i * 4;
                    float f = i;
                    bArr[i2 + 2] = (byte) (((int) Math.min(Math.max(GpuImageToneCurveFilterModified.this.mBlueCurve.get(i).floatValue() + f + GpuImageToneCurveFilterModified.this.mRgbCompositeCurve.get(i).floatValue(), 0.0f), 255.0f)) & 255);
                    bArr[i2 + 1] = (byte) (((int) Math.min(Math.max(GpuImageToneCurveFilterModified.this.mGreenCurve.get(i).floatValue() + f + GpuImageToneCurveFilterModified.this.mRgbCompositeCurve.get(i).floatValue(), 0.0f), 255.0f)) & 255);
                    bArr[i2] = (byte) (((int) Math.min(Math.max(f + GpuImageToneCurveFilterModified.this.mRedCurve.get(i).floatValue() + GpuImageToneCurveFilterModified.this.mRgbCompositeCurve.get(i).floatValue(), 0.0f), 255.0f)) & 255);
                    bArr[i2 + 3] = -1;
                }
                GLES20.glTexImage2D(3553, 0, 6408, 256, 1, 0, 6408, 5121, ByteBuffer.wrap(bArr));
            }
        });
    }

    private ArrayList<Float> createSplineCurve(PointF[] pointFArr) {
        if (pointFArr == null || pointFArr.length <= 0) {
            return null;
        }
        PointF[] pointFArr2 = (PointF[]) pointFArr.clone();
        Arrays.sort(pointFArr2, new Comparator<PointF>() { 
            @Override 
            public int compare(PointF pointF, PointF pointF2) {
                if (pointF.x < pointF2.x) {
                    return -1;
                }
                return pointF.x > pointF2.x ? 1 : 0;
            }
        });
        Point[] pointArr = new Point[pointFArr2.length];
        for (int i = 0; i < pointFArr.length; i++) {
            PointF pointF = pointFArr2[i];
            pointArr[i] = new Point((int) (pointF.x * 255.0f), (int) (pointF.y * 255.0f));
        }
        ArrayList<Point> createSplineCurve2 = createSplineCurve2(pointArr);
        Point point = createSplineCurve2.get(0);
        if (point.x > 0) {
            for (int i2 = point.x; i2 >= 0; i2--) {
                createSplineCurve2.add(0, new Point(i2, 0));
            }
        }
        Point point2 = createSplineCurve2.get(createSplineCurve2.size() - 1);
        if (point2.x < 255) {
            int i3 = point2.x;
            while (true) {
                i3++;
                if (i3 > 255) {
                    break;
                }
                createSplineCurve2.add(new Point(i3, 255));
            }
        }
        ArrayList<Float> arrayList = new ArrayList<>(createSplineCurve2.size());
        Iterator<Point> it = createSplineCurve2.iterator();
        while (it.hasNext()) {
            Point next = it.next();
            Point point3 = new Point(next.x, next.x);
            float sqrt = (float) Math.sqrt(Math.pow(point3.x - next.x, 2.0d) + Math.pow(point3.y - next.y, 2.0d));
            if (point3.y > next.y) {
                sqrt = -sqrt;
            }
            arrayList.add(Float.valueOf(sqrt));
        }
        return arrayList;
    }

    private ArrayList<Point> createSplineCurve2(Point[] pointArr) {
        ArrayList<Double> createSecondDerivative = createSecondDerivative(pointArr);
        int size = createSecondDerivative.size();
        if (size < 1) {
            return null;
        }
        double[] dArr = new double[size];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            dArr[i2] = createSecondDerivative.get(i2).doubleValue();
        }
        ArrayList<Point> arrayList = new ArrayList<>(size + 1);
        Point[] pointArr2 = pointArr;
        while (i < size - 1) {
            Point point = pointArr2[i];
            int i3 = i + 1;
            Point point2 = pointArr2[i3];
            int i4 = point.x;
            while (i4 < point2.x) {
                double d = (i4 - point.x) / (point2.x - point.x);
                double d2 = 1.0d - d;
                double[] dArr2 = dArr;
                double d3 = point2.x - point.x;
                Point point3 = point;
                ArrayList<Point> arrayList2 = arrayList;
                double d4 = (point.y * d2) + (point2.y * d) + (((d3 * d3) / 6.0d) * (((((d2 * d2) * d2) - d2) * dArr2[i]) + ((((d * d) * d) - d) * dArr2[i3])));
                double d5 = 255.0d;
                if (d4 <= 255.0d) {
                    if (d4 < 0.0d) {
                        d4 = 0.0d;
                    }
                    d5 = d4;
                }
                arrayList2.add(new Point(i4, (int) Math.round(d5)));
                i4++;
                pointArr2 = pointArr;
                arrayList = arrayList2;
                dArr = dArr2;
                point = point3;
            }
            i = i3;
        }
        ArrayList<Point> arrayList3 = arrayList;
        if (arrayList3.size() == 255) {
            arrayList3.add(pointArr[pointArr.length - 1]);
        }
        return arrayList3;
    }

    private ArrayList<Double> createSecondDerivative(Point[] pointArr) {
        int i;
        int length = pointArr.length;
        if (length <= 1) {
            return null;
        }
        int i2 = 0;
        double[][] dArr = (double[][]) Array.newInstance(Double.TYPE, length, 3);
        double[] dArr2 = new double[length];
        double d = 1.0d;
        dArr[0][1] = 1.0d;
        double d2 = 0.0d;
        dArr[0][0] = 0.0d;
        dArr[0][2] = 0.0d;
        int i3 = 1;
        while (true) {
            i = length - 1;
            if (i3 >= i) {
                break;
            }
            Point point = pointArr[i3 - 1];
            Point point2 = pointArr[i3];
            int i4 = i3 + 1;
            Point point3 = pointArr[i4];
            dArr[i3][i2] = (point2.x - point.x) / 6.0d;
            dArr[i3][1] = (point3.x - point.x) / 3.0d;
            dArr[i3][2] = (point3.x - point2.x) / 6.0d;
            dArr2[i3] = ((point3.y - point2.y) / (point3.x - point2.x)) - ((point2.y - point.y) / (point2.x - point.x));
            i3 = i4;
            i2 = 0;
            d = 1.0d;
            d2 = 0.0d;
        }
        dArr2[i2] = d2;
        dArr2[i] = d2;
        dArr[i][1] = d;
        dArr[i][i2] = d2;
        dArr[i][2] = d2;
        for (int i5 = 1; i5 < length; i5++) {
            int i6 = i5 - 1;
            double d3 = dArr[i5][i2] / dArr[i6][1];
            double[] dArr3 = dArr[i5];
            dArr3[1] = dArr3[1] - (dArr[i6][2] * d3);
            dArr[i5][i2] = d2;
            dArr2[i5] = dArr2[i5] - (d3 * dArr2[i6]);
        }
        for (int i7 = length - 2; i7 >= 0; i7--) {
            int i8 = i7 + 1;
            double d4 = dArr[i7][2] / dArr[i8][1];
            double[] dArr4 = dArr[i7];
            dArr4[1] = dArr4[1] - (dArr[i8][i2] * d4);
            dArr[i7][2] = d2;
            dArr2[i7] = dArr2[i7] - (d4 * dArr2[i8]);
        }
        ArrayList<Double> arrayList = new ArrayList<>(length);
        while (i2 < length) {
            arrayList.add(Double.valueOf(dArr2[i2] / dArr[i2][1]));
            i2++;
        }
        return arrayList;
    }
}
