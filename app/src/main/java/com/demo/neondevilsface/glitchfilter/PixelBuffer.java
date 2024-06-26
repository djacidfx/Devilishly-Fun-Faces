package com.demo.neondevilsface.glitchfilter;

import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.util.Log;
import java.nio.IntBuffer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;


public class PixelBuffer {
    static final boolean LIST_CONFIGS = false;
    static final String TAG = "PixelBuffer";
    Bitmap mBitmap;
    EGL10 mEGL;
    EGLConfig mEGLConfig;
    EGLConfig[] mEGLConfigs;
    EGLContext mEGLContext;
    EGLDisplay mEGLDisplay;
    EGLSurface mEGLSurface;
    GL10 mGL;
    int mHeight;
    GLSurfaceView.Renderer mRenderer;
    String mThreadOwner;
    int mWidth;

    public PixelBuffer(int i, int i2) {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        this.mEGL = egl10;
        EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        this.mEGLDisplay = eglGetDisplay;
        this.mWidth = i;
        this.mHeight = i2;
        int[] iArr = {12375, i, 12374, i2, 12344};
        this.mEGL.eglInitialize(eglGetDisplay, new int[2]);
        EGLConfig chooseConfig = chooseConfig();
        this.mEGLConfig = chooseConfig;
        this.mEGLContext = this.mEGL.eglCreateContext(this.mEGLDisplay, chooseConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
        EGLSurface eglCreatePbufferSurface = this.mEGL.eglCreatePbufferSurface(this.mEGLDisplay, this.mEGLConfig, iArr);
        this.mEGLSurface = eglCreatePbufferSurface;
        this.mEGL.eglMakeCurrent(this.mEGLDisplay, eglCreatePbufferSurface, eglCreatePbufferSurface, this.mEGLContext);
        this.mGL = (GL10) this.mEGLContext.getGL();
        this.mThreadOwner = Thread.currentThread().getName();
    }

    public void setRenderer(GLSurfaceView.Renderer renderer) {
        this.mRenderer = renderer;
        if (!Thread.currentThread().getName().equals(this.mThreadOwner)) {
            Log.e(TAG, "setRenderer: This thread does not own the OpenGL context.");
            return;
        }
        this.mRenderer.onSurfaceCreated(this.mGL, this.mEGLConfig);
        this.mRenderer.onSurfaceChanged(this.mGL, this.mWidth, this.mHeight);
    }

    public Bitmap getBitmap() {
        if (this.mRenderer == null) {
            Log.e(TAG, "getBitmap: Renderer was not set.");
            return null;
        } else if (!Thread.currentThread().getName().equals(this.mThreadOwner)) {
            Log.e(TAG, "getBitmap: This thread does not own the OpenGL context.");
            return null;
        } else {
            this.mRenderer.onDrawFrame(this.mGL);
            this.mRenderer.onDrawFrame(this.mGL);
            convertToBitmap();
            return this.mBitmap;
        }
    }

    public void destroy() {
        this.mRenderer.onDrawFrame(this.mGL);
        this.mRenderer.onDrawFrame(this.mGL);
        this.mEGL.eglMakeCurrent(this.mEGLDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
        this.mEGL.eglDestroySurface(this.mEGLDisplay, this.mEGLSurface);
        this.mEGL.eglDestroyContext(this.mEGLDisplay, this.mEGLContext);
        this.mEGL.eglTerminate(this.mEGLDisplay);
    }

    private EGLConfig chooseConfig() {
        int[] iArr = new int[1];
        int[] iArr2 = {12325, 0, 12326, 0, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12344};
        this.mEGL.eglChooseConfig(this.mEGLDisplay, iArr2, null, 0, iArr);
        int i = iArr[0];
        EGLConfig[] eGLConfigArr = new EGLConfig[i];
        this.mEGLConfigs = eGLConfigArr;
        this.mEGL.eglChooseConfig(this.mEGLDisplay, iArr2, eGLConfigArr, i, iArr);
        return this.mEGLConfigs[0];
    }

    private void listConfig() {
        EGLConfig[] eGLConfigArr;
        Log.i(TAG, "Config List {");
        for (EGLConfig eGLConfig : this.mEGLConfigs) {
            Log.i(TAG, "    <d,s,r,g,b,a> = <" + getConfigAttrib(eGLConfig, 12325) + "," + getConfigAttrib(eGLConfig, 12326) + "," + getConfigAttrib(eGLConfig, 12324) + "," + getConfigAttrib(eGLConfig, 12323) + "," + getConfigAttrib(eGLConfig, 12322) + "," + getConfigAttrib(eGLConfig, 12321) + ">");
        }
        Log.i(TAG, "}");
    }

    private int getConfigAttrib(EGLConfig eGLConfig, int i) {
        int[] iArr = new int[1];
        if (this.mEGL.eglGetConfigAttrib(this.mEGLDisplay, eGLConfig, i, iArr)) {
            return iArr[0];
        }
        return 0;
    }

    private void convertToBitmap() {
        int i = this.mWidth;
        int i2 = this.mHeight;
        int[] iArr = new int[i * i2];
        IntBuffer allocate = IntBuffer.allocate(i * i2);
        this.mGL.glReadPixels(0, 0, this.mWidth, this.mHeight, 6408, 5121, allocate);
        int[] array = allocate.array();
        int i3 = 0;
        boolean aa = true;

        while (aa) {
            int i4 = this.mHeight;
            if (i3 < i4) {
                int i5 = 0;
                boolean bb = true;


                while (bb) {
                    int i6 = this.mWidth;
                    if (i5 < i6) {
                        iArr[(((this.mHeight - i3) - 1) * i6) + i5] = array[(i6 * i3) + i5];
                        i5++;
                    }else {
                        bb = false;
                    }
                }
                i3++;
            } else {
                aa = false;
                Bitmap createBitmap = Bitmap.createBitmap(this.mWidth, i4, Bitmap.Config.ARGB_8888);
                this.mBitmap = createBitmap;
                createBitmap.copyPixelsFromBuffer(IntBuffer.wrap(iArr));
                return;
            }
        }
    }
}