package com.demo.neondevilsface.glitchfilter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.Queue;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class GPUImageRenderer implements GLSurfaceView.Renderer, Camera.PreviewCallback {
    static final float[] CUBE = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
    public static final int NO_IMAGE = -1;
    public int mAddedPadding;
    public GPUImageFilter mFilter;
    private boolean mFlipHorizontal;
    private boolean mFlipVertical;
    private final FloatBuffer mGLCubeBuffer;
    public IntBuffer mGLRgbBuffer;
    private final FloatBuffer mGLTextureBuffer;
    public int mImageHeight;
    public int mImageWidth;
    public int mOutputHeight;
    public int mOutputWidth;
    private Rotation mRotation;
    public int mGLTextureId = -1;
    private GPUImage.ScaleType mScaleType = GPUImage.ScaleType.CENTER_CROP;
    public final Object mSurfaceChangedWaiter = new Object();
    public SurfaceTexture mSurfaceTexture = null;
    private final Queue<Runnable> mRunOnDraw = new LinkedList();
    private final Queue<Runnable> mRunOnDrawEnd = new LinkedList();

    private float addDistance(float f, float f2) {
        return f == 0.0f ? f2 : 1.0f - f2;
    }

    public GPUImageRenderer(GPUImageFilter gPUImageFilter) {
        this.mFilter = gPUImageFilter;
        float[] fArr = CUBE;
        FloatBuffer asFloatBuffer = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mGLCubeBuffer = asFloatBuffer;
        asFloatBuffer.put(fArr).position(0);
        this.mGLTextureBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        setRotation(Rotation.NORMAL, false, false);
    }

    @Override 
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDisable(2929);
        this.mFilter.init();
    }

    @Override 
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        this.mOutputWidth = i;
        this.mOutputHeight = i2;
        GLES20.glViewport(0, 0, i, i2);
        GLES20.glUseProgram(this.mFilter.getProgram());
        this.mFilter.onOutputSizeChanged(i, i2);
        adjustImageScaling();
        synchronized (this.mSurfaceChangedWaiter) {
            this.mSurfaceChangedWaiter.notifyAll();
        }
    }

    @Override 
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(16640);
        runAll(this.mRunOnDraw);
        this.mFilter.onDraw(this.mGLTextureId, this.mGLCubeBuffer, this.mGLTextureBuffer);
        runAll(this.mRunOnDrawEnd);
        SurfaceTexture surfaceTexture = this.mSurfaceTexture;
        if (surfaceTexture != null) {
            surfaceTexture.updateTexImage();
        }
    }

    private void runAll(Queue<Runnable> queue) {
        synchronized (queue) {
            while (!queue.isEmpty()) {
                queue.poll().run();
            }
        }
    }

    @Override 
    public void onPreviewFrame(final byte[] bArr, final Camera camera) {
        final Camera.Size previewSize = camera.getParameters().getPreviewSize();
        if (this.mGLRgbBuffer == null) {
            this.mGLRgbBuffer = IntBuffer.allocate(previewSize.width * previewSize.height);
        }
        if (this.mRunOnDraw.isEmpty()) {
            runOnDraw(new Runnable() { 
                @Override 
                public void run() {
                    GPUImageNativeLibrary.YUVtoRBGA(bArr, previewSize.width, previewSize.height, GPUImageRenderer.this.mGLRgbBuffer.array());
                    GPUImageRenderer gPUImageRenderer = GPUImageRenderer.this;
                    gPUImageRenderer.mGLTextureId = OpenGlUtils.loadTexture(gPUImageRenderer.mGLRgbBuffer, previewSize, GPUImageRenderer.this.mGLTextureId);
                    camera.addCallbackBuffer(bArr);
                    if (GPUImageRenderer.this.mImageWidth != previewSize.width) {
                        GPUImageRenderer.this.mImageWidth = previewSize.width;
                        GPUImageRenderer.this.mImageHeight = previewSize.height;
                        GPUImageRenderer.this.adjustImageScaling();
                    }
                }
            });
        }
    }

    public void setUpSurfaceTexture(final Camera camera) {
        runOnDraw(new Runnable() { 
            @Override 
            public void run() {
                int[] iArr = new int[1];
                GLES20.glGenTextures(1, iArr, 0);
                GPUImageRenderer.this.mSurfaceTexture = new SurfaceTexture(iArr[0]);
                try {
                    camera.setPreviewTexture(GPUImageRenderer.this.mSurfaceTexture);
                    camera.setPreviewCallback(GPUImageRenderer.this);
                    camera.startPreview();
                } catch (IOException unused) {
                }
            }
        });
    }

    public void setFilter(final GPUImageFilter gPUImageFilter) {
        runOnDraw(new Runnable() { 
            @Override 
            public void run() {
                GPUImageFilter gPUImageFilter2 = GPUImageRenderer.this.mFilter;
                GPUImageRenderer.this.mFilter = gPUImageFilter;
                if (gPUImageFilter2 != null) {
                    gPUImageFilter2.destroy();
                }
                GPUImageRenderer.this.mFilter.init();
                GLES20.glUseProgram(GPUImageRenderer.this.mFilter.getProgram());
                GPUImageRenderer.this.mFilter.onOutputSizeChanged(GPUImageRenderer.this.mOutputWidth, GPUImageRenderer.this.mOutputHeight);
            }
        });
    }

    public void deleteImage() {
        runOnDraw(new Runnable() { 
            @Override 
            public void run() {
                GLES20.glDeleteTextures(1, new int[]{GPUImageRenderer.this.mGLTextureId}, 0);
                GPUImageRenderer.this.mGLTextureId = -1;
            }
        });
    }

    public void setImageBitmap(Bitmap bitmap) {
        setImageBitmap(bitmap, true);
    }

    public void setImageBitmap(final Bitmap bitmap, final boolean z) {
        if (bitmap == null) {
            return;
        }
        runOnDraw(new Runnable() { 
            @Override 
            public void run() {
                Bitmap bitmap2 = null;
                if (bitmap.getWidth() % 2 == 1) {
                    Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth() + 1, bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                    createBitmap.setDensity(bitmap.getDensity());
                    Canvas canvas = new Canvas(createBitmap);
                    canvas.drawARGB(0, 0, 0, 0);
                    canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                    GPUImageRenderer.this.mAddedPadding = 1;
                    bitmap2 = createBitmap;
                } else {
                    GPUImageRenderer.this.mAddedPadding = 0;
                }
                GPUImageRenderer gPUImageRenderer = GPUImageRenderer.this;
                gPUImageRenderer.mGLTextureId = OpenGlUtils.loadTexture(bitmap2 != null ? bitmap2 : bitmap, gPUImageRenderer.mGLTextureId, z);
                if (bitmap2 != null) {
                    bitmap2.recycle();
                }
                GPUImageRenderer.this.mImageWidth = bitmap.getWidth();
                GPUImageRenderer.this.mImageHeight = bitmap.getHeight();
                GPUImageRenderer.this.adjustImageScaling();
            }
        });
    }

    public void setScaleType(GPUImage.ScaleType scaleType) {
        this.mScaleType = scaleType;
    }

    public int getFrameWidth() {
        return this.mOutputWidth;
    }

    public int getFrameHeight() {
        return this.mOutputHeight;
    }

    public void adjustImageScaling() {
        float f = this.mOutputWidth;
        float f2 = this.mOutputHeight;
        if (this.mRotation == Rotation.ROTATION_270 || this.mRotation == Rotation.ROTATION_90) {
            f = this.mOutputHeight;
            f2 = this.mOutputWidth;
        }
        float max = Math.max(f / this.mImageWidth, f2 / this.mImageHeight);
        float round = Math.round(this.mImageWidth * max) / f;
        float round2 = Math.round(this.mImageHeight * max) / f2;
        float[] fArr = CUBE;
        float[] rotation = TextureRotationUtil.getRotation(this.mRotation, this.mFlipHorizontal, this.mFlipVertical);
        if (this.mScaleType == GPUImage.ScaleType.CENTER_CROP) {
            float f3 = (1.0f - (1.0f / round)) / 2.0f;
            float f4 = (1.0f - (1.0f / round2)) / 2.0f;
            rotation = new float[]{addDistance(rotation[0], f3), addDistance(rotation[1], f4), addDistance(rotation[2], f3), addDistance(rotation[3], f4), addDistance(rotation[4], f3), addDistance(rotation[5], f4), addDistance(rotation[6], f3), addDistance(rotation[7], f4)};
        } else {
            fArr = new float[]{fArr[0] / round2, fArr[1] / round, fArr[2] / round2, fArr[3] / round, fArr[4] / round2, fArr[5] / round, fArr[6] / round2, fArr[7] / round};
        }
        this.mGLCubeBuffer.clear();
        this.mGLCubeBuffer.put(fArr).position(0);
        this.mGLTextureBuffer.clear();
        this.mGLTextureBuffer.put(rotation).position(0);
    }

    public void setRotationCamera(Rotation rotation, boolean z, boolean z2) {
        setRotation(rotation, z2, z);
    }

    public void setRotation(Rotation rotation) {
        this.mRotation = rotation;
        adjustImageScaling();
    }

    public void setRotation(Rotation rotation, boolean z, boolean z2) {
        this.mFlipHorizontal = z;
        this.mFlipVertical = z2;
        setRotation(rotation);
    }

    public Rotation getRotation() {
        return this.mRotation;
    }

    public boolean isFlippedHorizontally() {
        return this.mFlipHorizontal;
    }

    public boolean isFlippedVertically() {
        return this.mFlipVertical;
    }

    public void runOnDraw(Runnable runnable) {
        synchronized (this.mRunOnDraw) {
            this.mRunOnDraw.add(runnable);
        }
    }

    public void runOnDrawEnd(Runnable runnable) {
        synchronized (this.mRunOnDrawEnd) {
            this.mRunOnDrawEnd.add(runnable);
        }
    }
}
