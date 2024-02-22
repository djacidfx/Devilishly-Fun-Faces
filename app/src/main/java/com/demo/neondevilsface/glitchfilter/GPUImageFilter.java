package com.demo.neondevilsface.glitchfilter;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLES20;
import android.util.Log;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.Scanner;


public class GPUImageFilter {
    public static final String NO_FILTER_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}";
    public static final String NO_FILTER_VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}";
    private final String mFragmentShader;
    protected int mGLAttribPosition;
    protected int mGLAttribTextureCoordinate;
    protected int mGLProgId;
    protected int mGLUniformTexture;
    private boolean mIsInitialized;
    protected int mOutputHeight;
    protected int mOutputWidth;
    private final LinkedList<Runnable> mRunOnDraw;
    private final String mVertexShader;

    public void onDestroy() {
    }

    public void onDrawArraysPre() {
    }

    public void onInitialized() {
    }

    public GPUImageFilter() {
        this(NO_FILTER_VERTEX_SHADER, NO_FILTER_FRAGMENT_SHADER);
    }

    public GPUImageFilter(String str, String str2) {
        this.mRunOnDraw = new LinkedList<>();
        this.mVertexShader = str;
        this.mFragmentShader = str2;
    }

    public final void init() {
        onInit();
        this.mIsInitialized = true;
        onInitialized();
    }

    public void onInit() {
        Log.e("mVertexShader", "onInit: " + this.mFragmentShader);
        int loadProgram = OpenGlUtils.loadProgram(this.mVertexShader, this.mFragmentShader);
        this.mGLProgId = loadProgram;
        this.mGLAttribPosition = GLES20.glGetAttribLocation(loadProgram, "position");
        this.mGLUniformTexture = GLES20.glGetUniformLocation(this.mGLProgId, "inputImageTexture");
        this.mGLAttribTextureCoordinate = GLES20.glGetAttribLocation(this.mGLProgId, "inputTextureCoordinate");
        this.mIsInitialized = true;
    }

    public final void destroy() {
        this.mIsInitialized = false;
        GLES20.glDeleteProgram(this.mGLProgId);
        onDestroy();
    }

    public void onOutputSizeChanged(int i, int i2) {
        this.mOutputWidth = i;
        this.mOutputHeight = i2;
    }

    public void onDraw(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        GLES20.glUseProgram(this.mGLProgId);
        runPendingOnDrawTasks();
        if (this.mIsInitialized) {
            floatBuffer.position(0);
            GLES20.glVertexAttribPointer(this.mGLAttribPosition, 2, 5126, false, 0, (Buffer) floatBuffer);
            GLES20.glEnableVertexAttribArray(this.mGLAttribPosition);
            floatBuffer2.position(0);
            GLES20.glVertexAttribPointer(this.mGLAttribTextureCoordinate, 2, 5126, false, 0, (Buffer) floatBuffer2);
            GLES20.glEnableVertexAttribArray(this.mGLAttribTextureCoordinate);
            if (i != -1) {
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(3553, i);
                GLES20.glUniform1i(this.mGLUniformTexture, 0);
            }
            onDrawArraysPre();
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.mGLAttribPosition);
            GLES20.glDisableVertexAttribArray(this.mGLAttribTextureCoordinate);
            GLES20.glBindTexture(3553, 0);
        }
    }

    public void runPendingOnDrawTasks() {
        while (!this.mRunOnDraw.isEmpty()) {
            this.mRunOnDraw.removeFirst().run();
        }
    }

    public boolean isInitialized() {
        return this.mIsInitialized;
    }

    public int getOutputWidth() {
        return this.mOutputWidth;
    }

    public int getOutputHeight() {
        return this.mOutputHeight;
    }

    public int getProgram() {
        return this.mGLProgId;
    }

    public int getAttribPosition() {
        return this.mGLAttribPosition;
    }

    public int getAttribTextureCoordinate() {
        return this.mGLAttribTextureCoordinate;
    }

    public int getUniformTexture() {
        return this.mGLUniformTexture;
    }

    public void setInteger(final int i, final int i2) {
        Log.e("setInteger__", "setInteger: " + i);
        runOnDraw(new Runnable() { 
            @Override 
            public void run() {
                GLES20.glUniform1i(i, i2);
            }
        });
    }

    public void setFloat(final int i, final float f) {
        runOnDraw(new Runnable() { 
            @Override 
            public void run() {
                GLES20.glUniform1f(i, f);
            }
        });
    }

    public void setFloatVec2(final int i, final float[] fArr) {
        runOnDraw(new Runnable() { 
            @Override 
            public void run() {
                GLES20.glUniform2fv(i, 1, FloatBuffer.wrap(fArr));
            }
        });
    }

    public void setFloatVec3(final int i, final float[] fArr) {
        runOnDraw(new Runnable() { 
            @Override 
            public void run() {
                GLES20.glUniform3fv(i, 1, FloatBuffer.wrap(fArr));
            }
        });
    }

    public void setFloatVec4(final int i, final float[] fArr) {
        runOnDraw(new Runnable() { 
            @Override 
            public void run() {
                GLES20.glUniform4fv(i, 1, FloatBuffer.wrap(fArr));
            }
        });
    }

    public void setFloatArray(final int i, final float[] fArr) {
        runOnDraw(new Runnable() { 
            @Override 
            public void run() {
                int i2 = i;
                float[] fArr2 = fArr;
                GLES20.glUniform1fv(i2, fArr2.length, FloatBuffer.wrap(fArr2));
            }
        });
    }

    public void setPoint(final int i, final PointF pointF) {
        runOnDraw(new Runnable() { 
            @Override 
            public void run() {
                GLES20.glUniform2fv(i, 1, new float[]{pointF.x, pointF.y}, 0);
            }
        });
    }

    public void setUniformMatrix3f(final int i, final float[] fArr) {
        runOnDraw(new Runnable() { 
            @Override 
            public void run() {
                GLES20.glUniformMatrix3fv(i, 1, false, fArr, 0);
            }
        });
    }

    public void setUniformMatrix4f(final int i, final float[] fArr) {
        runOnDraw(new Runnable() { 
            @Override 
            public void run() {
                GLES20.glUniformMatrix4fv(i, 1, false, fArr, 0);
            }
        });
    }

    public void runOnDraw(Runnable runnable) {
        synchronized (this.mRunOnDraw) {
            this.mRunOnDraw.addLast(runnable);
        }
    }

    public static String loadShader(String str, Context context) {
        try {
            InputStream open = context.getAssets().open(str);
            String convertStreamToString = convertStreamToString(open);
            open.close();
            return convertStreamToString;
        } catch (Exception unused) {
            return "";
        }
    }

    public static String convertStreamToString(InputStream inputStream) {
        Scanner useDelimiter = new Scanner(inputStream).useDelimiter("\\A");
        return useDelimiter.hasNext() ? useDelimiter.next() : "";
    }
}
