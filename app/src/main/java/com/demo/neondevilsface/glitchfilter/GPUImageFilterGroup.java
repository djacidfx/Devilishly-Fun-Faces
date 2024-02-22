package com.demo.neondevilsface.glitchfilter;

import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;


public class GPUImageFilterGroup extends GPUImageFilter {
    protected List<GPUImageFilter> mFilters;
    private int[] mFrameBufferTextures;
    private int[] mFrameBuffers;
    private final FloatBuffer mGLCubeBuffer;
    private final FloatBuffer mGLTextureBuffer;
    private final FloatBuffer mGLTextureFlipBuffer;
    protected List<GPUImageFilter> mMergedFilters;

    public GPUImageFilterGroup() {
        this(null);
    }

    public GPUImageFilterGroup(List<GPUImageFilter> list) {
        this.mFilters = list;
        if (list == null) {
            this.mFilters = new ArrayList();
        } else {
            updateMergedFilters();
        }
        FloatBuffer asFloatBuffer = ByteBuffer.allocateDirect(GPUImageRenderer.CUBE.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mGLCubeBuffer = asFloatBuffer;
        asFloatBuffer.put(GPUImageRenderer.CUBE).position(0);
        FloatBuffer asFloatBuffer2 = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mGLTextureBuffer = asFloatBuffer2;
        asFloatBuffer2.put(TextureRotationUtil.TEXTURE_NO_ROTATION).position(0);
        float[] rotation = TextureRotationUtil.getRotation(Rotation.NORMAL, false, true);
        FloatBuffer asFloatBuffer3 = ByteBuffer.allocateDirect(rotation.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mGLTextureFlipBuffer = asFloatBuffer3;
        asFloatBuffer3.put(rotation).position(0);
    }

    public void addFilter(GPUImageFilter gPUImageFilter) {
        if (gPUImageFilter != null) {
            this.mFilters.add(gPUImageFilter);
            updateMergedFilters();
        }
    }

    @Override 
    public void onInit() {
        super.onInit();
        for (GPUImageFilter gPUImageFilter : this.mFilters) {
            gPUImageFilter.init();
        }
    }

    @Override 
    public void onDestroy() {
        destroyFramebuffers();
        for (GPUImageFilter gPUImageFilter : this.mFilters) {
            gPUImageFilter.destroy();
        }
        super.onDestroy();
    }

    private void destroyFramebuffers() {
        int[] iArr = this.mFrameBufferTextures;
        if (iArr != null) {
            GLES20.glDeleteTextures(iArr.length, iArr, 0);
            this.mFrameBufferTextures = null;
        }
        int[] iArr2 = this.mFrameBuffers;
        if (iArr2 != null) {
            GLES20.glDeleteFramebuffers(iArr2.length, iArr2, 0);
            this.mFrameBuffers = null;
        }
    }

    @Override 
    public void onOutputSizeChanged(int i, int i2) {
        super.onOutputSizeChanged(i, i2);
        if (this.mFrameBuffers != null) {
            destroyFramebuffers();
        }
        int size = this.mFilters.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mFilters.get(i3).onOutputSizeChanged(i, i2);
        }
        List<GPUImageFilter> list = this.mMergedFilters;
        if (list == null || list.size() <= 0) {
            return;
        }
        int i4 = 1;
        int size2 = this.mMergedFilters.size() - 1;
        this.mFrameBuffers = new int[size2];
        this.mFrameBufferTextures = new int[size2];
        int i5 = 0;
        while (i5 < size2) {
            GLES20.glGenFramebuffers(i4, this.mFrameBuffers, i5);
            GLES20.glGenTextures(i4, this.mFrameBufferTextures, i5);
            GLES20.glBindTexture(3553, this.mFrameBufferTextures[i5]);
            GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, null);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLES20.glBindFramebuffer(36160, this.mFrameBuffers[i5]);
            GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.mFrameBufferTextures[i5], 0);
            GLES20.glBindTexture(3553, 0);
            GLES20.glBindFramebuffer(36160, 0);
            i5++;
            i4 = 1;
        }
    }

    @Override 
    public void onDraw(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        List<GPUImageFilter> list;
        runPendingOnDrawTasks();
        if (!isInitialized() || this.mFrameBuffers == null || this.mFrameBufferTextures == null || (list = this.mMergedFilters) == null) {
            return;
        }
        int size = list.size();
        int i2 = 0;
        while (i2 < size) {
            GPUImageFilter gPUImageFilter = this.mMergedFilters.get(i2);
            int i3 = size - 1;
            boolean z = i2 < i3;
            if (z) {
                GLES20.glBindFramebuffer(36160, this.mFrameBuffers[i2]);
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            }
            if (i2 == 0) {
                gPUImageFilter.onDraw(i, floatBuffer, floatBuffer2);
            } else if (i2 == i3) {
                gPUImageFilter.onDraw(i, this.mGLCubeBuffer, size % 2 == 0 ? this.mGLTextureFlipBuffer : this.mGLTextureBuffer);
            } else {
                gPUImageFilter.onDraw(i, this.mGLCubeBuffer, this.mGLTextureBuffer);
            }
            if (z) {
                GLES20.glBindFramebuffer(36160, 0);
                i = this.mFrameBufferTextures[i2];
            }
            i2++;
        }
    }

    public List<GPUImageFilter> getFilters() {
        return this.mFilters;
    }

    public List<GPUImageFilter> getMergedFilters() {
        return this.mMergedFilters;
    }

    public void updateMergedFilters() {
        if (this.mFilters != null) {
            List<GPUImageFilter> list = this.mMergedFilters;
            if (list == null) {
                this.mMergedFilters = new ArrayList();
            } else {
                list.clear();
            }
            for (GPUImageFilter gPUImageFilter : this.mFilters) {
                if (gPUImageFilter instanceof GPUImageFilterGroup) {
                    GPUImageFilterGroup gPUImageFilterGroup = (GPUImageFilterGroup) gPUImageFilter;
                    gPUImageFilterGroup.updateMergedFilters();
                    List<GPUImageFilter> mergedFilters = gPUImageFilterGroup.getMergedFilters();
                    if (mergedFilters != null && !mergedFilters.isEmpty()) {
                        this.mMergedFilters.addAll(mergedFilters);
                    }
                } else {
                    this.mMergedFilters.add(gPUImageFilter);
                }
            }
        }
    }
}
