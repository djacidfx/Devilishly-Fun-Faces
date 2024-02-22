package com.demo.neondevilsface.glitchfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import androidx.core.view.ViewCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.IntBuffer;
import java.util.concurrent.Semaphore;


public class GPUImageView extends FrameLayout {
    private GPUImageFilter mFilter;
    public Size mForceSize;
    public GLSurfaceView mGLSurfaceView;
    private GPUImage mGPUImage;
    private float mRatio;

    
    public interface OnPictureSavedListener {
        void onPictureSaved(Uri uri);
    }

    public GPUImageView(Context context) {
        super(context);
        this.mForceSize = null;
        this.mRatio = 0.0f;
        init(context, null);
    }

    public GPUImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mForceSize = null;
        this.mRatio = 0.0f;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        GPUImageGLSurfaceView gPUImageGLSurfaceView = new GPUImageGLSurfaceView(context, attributeSet);
        this.mGLSurfaceView = gPUImageGLSurfaceView;
        addView(gPUImageGLSurfaceView);
        GPUImage gPUImage = new GPUImage(getContext());
        this.mGPUImage = gPUImage;
        gPUImage.setGLSurfaceView(this.mGLSurfaceView);
    }

    @Override 
    public void onMeasure(int i, int i2) {
        if (this.mRatio != 0.0f) {
            float size = MeasureSpec.getSize(i);
            float size2 = MeasureSpec.getSize(i2);
            float f = this.mRatio;
            if (size / f < size2) {
                Math.round(size / f);
                return;
            } else {
                Math.round(size2 * f);
                return;
            }
        }
        super.onMeasure(i, i2);
    }

    public GPUImage getGPUImage() {
        return this.mGPUImage;
    }

    public void setRatio(float f) {
        this.mRatio = f;
        this.mGLSurfaceView.requestLayout();
        this.mGPUImage.deleteImage();
    }

    public void setScaleType(GPUImage.ScaleType scaleType) {
        this.mGPUImage.setScaleType(scaleType);
    }

    public void setRotation(Rotation rotation) {
        this.mGPUImage.setRotation(rotation);
        requestRender();
    }

    public void setFilter(GPUImageFilter gPUImageFilter) {
        this.mFilter = gPUImageFilter;
        this.mGPUImage.setFilter(gPUImageFilter);
        requestRender();
    }

    public GPUImageFilter getFilter() {
        return this.mFilter;
    }

    public void setImage(Bitmap bitmap) {
        this.mGPUImage.setImage(bitmap);
    }

    public void setImage(Uri uri) {
        this.mGPUImage.setImage(uri);
    }

    public void setImage(File file) {
        this.mGPUImage.setImage(file);
    }

    public void requestRender() {
        this.mGLSurfaceView.requestRender();
    }

    public void saveToPictures(String str, String str2, OnPictureSavedListener onPictureSavedListener) {
        new SaveTask(this, this, str, str2, onPictureSavedListener).execute(new Void[0]);
    }

    public void saveToPictures(String str, String str2, int i, int i2, OnPictureSavedListener onPictureSavedListener) {
        new SaveTask(str, str2, i, i2, onPictureSavedListener).execute(new Void[0]);
    }

    public Bitmap capture(int i, int i2) throws InterruptedException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Do not call this method from the UI thread!");
        }
        this.mForceSize = new Size(i, i2);
        final Semaphore semaphore = new Semaphore(0);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { 
            @Override 
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    GPUImageView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    GPUImageView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                semaphore.release();
            }
        });
        post(new Runnable() { 
            @Override 
            public void run() {
                GPUImageView gPUImageView = GPUImageView.this;
                GPUImageView gPUImageView2 = GPUImageView.this;
                gPUImageView.addView(new LoadingView(gPUImageView2.getContext()));
                GPUImageView.this.mGLSurfaceView.requestLayout();
            }
        });
        semaphore.acquire();
        this.mGPUImage.runOnGLThread(new Runnable() { 
            @Override 
            public void run() {
                semaphore.release();
            }
        });
        requestRender();
        semaphore.acquire();
        Bitmap capture = capture();
        this.mForceSize = null;
        post(new Runnable() { 
            @Override 
            public void run() {
                GPUImageView.this.mGLSurfaceView.requestLayout();
            }
        });
        requestRender();
        postDelayed(new Runnable() { 
            @Override 
            public void run() {
                GPUImageView.this.removeViewAt(1);
            }
        }, 300L);
        return capture;
    }

    public Bitmap capture() throws InterruptedException {
        final Semaphore semaphore = new Semaphore(0);
        final int measuredWidth = this.mGLSurfaceView.getMeasuredWidth();
        final int measuredHeight = this.mGLSurfaceView.getMeasuredHeight();
        final int[] iArr = new int[measuredWidth * measuredHeight];
        this.mGPUImage.runOnGLThread(new Runnable() { 
            @Override 
            public void run() {
                IntBuffer allocate = IntBuffer.allocate(measuredWidth * measuredHeight);
                GLES20.glReadPixels(0, 0, measuredWidth, measuredHeight, 6408, 5121, allocate);
                int[] array = allocate.array();
                for (int i = 0; i < measuredHeight; i++) {
                    for (int i2 = 0; i2 < i; i2++) {
                        iArr[(((i2 - i) - 1) * i) + i2] = array[(i * i) + i2];
                    }
                }
                semaphore.release();
            }
        });
        requestRender();
        semaphore.acquire();
        Bitmap createBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(IntBuffer.wrap(iArr));
        return createBitmap;
    }

    public void onPause() {
        this.mGLSurfaceView.onPause();
    }

    public void onResume() {
        this.mGLSurfaceView.onResume();
    }

    
    public static class Size {
        int height;
        int width;

        public Size(int i, int i2) {
            this.width = i;
            this.height = i2;
        }
    }

    
    
    public class GPUImageGLSurfaceView extends GLSurfaceView {
        public GPUImageGLSurfaceView(Context context) {
            super(context);
        }

        public GPUImageGLSurfaceView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override 
        public void onMeasure(int i, int i2) {
            if (GPUImageView.this.mForceSize != null) {
                return;
            }
            super.onMeasure(i, i2);
        }
    }

    
    private class LoadingView extends FrameLayout {
        public LoadingView(Context context) {
            super(context);
            init();
        }

        public LoadingView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            init();
        }

        public LoadingView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            init();
        }

        private void init() {
            ProgressBar progressBar = new ProgressBar(getContext());
            progressBar.setLayoutParams(new LayoutParams(-2, -2, 17));
            addView(progressBar);
            setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        }
    }

    
    private class SaveTask extends AsyncTask<Void, Void, Void> {
        private final String mFileName;
        private final String mFolderName;
        public final Handler mHandler;
        private final int mHeight;
        public final OnPictureSavedListener mListener;
        private final int mWidth;

        public SaveTask(GPUImageView gPUImageView, GPUImageView gPUImageView2, String str, String str2, OnPictureSavedListener onPictureSavedListener) {
            this(str, str2, 0, 0, onPictureSavedListener);
        }

        public SaveTask(String str, String str2, int i, int i2, OnPictureSavedListener onPictureSavedListener) {
            this.mFolderName = str;
            this.mFileName = str2;
            this.mWidth = i;
            this.mHeight = i2;
            this.mListener = onPictureSavedListener;
            this.mHandler = new Handler();
        }

        @Override 
        public Void doInBackground(Void... voidArr) {
            try {
                String str = this.mFolderName;
                String str2 = this.mFileName;
                int i = this.mWidth;
                saveImage(str, str2, i != 0 ? GPUImageView.this.capture(i, this.mHeight) : GPUImageView.this.capture());
            } catch (InterruptedException unused) {
            }
            return null;
        }

        private void saveImage(String str, String str2, Bitmap bitmap) {
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File file = new File(externalStoragePublicDirectory, str + "/" + str2);
            try {
                file.getParentFile().mkdirs();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, new FileOutputStream(file));
                MediaScannerConnection.scanFile(GPUImageView.this.getContext(), new String[]{file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { 
                    @Override 
                    public void onScanCompleted(String str3, final Uri uri) {
                        if (SaveTask.this.mListener != null) {
                            SaveTask.this.mHandler.post(new Runnable() { 
                                @Override 
                                public void run() {
                                    SaveTask.this.mListener.onPictureSaved(uri);
                                }
                            });
                        }
                    }
                });
            } catch (FileNotFoundException unused) {
            }
        }
    }
}
