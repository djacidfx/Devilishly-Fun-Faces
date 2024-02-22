package com.demo.neondevilsface.glitchfilter;

import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import com.demo.neondevilsface.Utility.Constant;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;


public class GPUImage {
    public final Context mContext;
    private Bitmap mCurrentBitmap;
    public GPUImageFilter mFilter;
    private GLSurfaceView mGlSurfaceView;
    public final GPUImageRenderer mRenderer;
    public ScaleType mScaleType = ScaleType.CENTER_CROP;

    
    public interface OnPictureSavedListener {
        void onPictureSaved(Uri uri);
    }

    
    public interface ResponseListener<T> {
        void response(T t);
    }

    
    public enum ScaleType {
        CENTER_INSIDE,
        CENTER_CROP,
        FITXY
    }

    public GPUImage(Context context) {
        if (!supportsOpenGLES2(context)) {
            throw new IllegalStateException("OpenGL ES 2.0 is not supported on this phone.");
        }
        this.mContext = context;
        this.mFilter = new GPUImageFilter();
        this.mRenderer = new GPUImageRenderer(this.mFilter);
    }

    private boolean supportsOpenGLES2(Context context) {
        return ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion >= 131072;
    }

    public void setGLSurfaceView(GLSurfaceView gLSurfaceView) {
        this.mGlSurfaceView = gLSurfaceView;
        gLSurfaceView.setEGLContextClientVersion(2);
        this.mGlSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        this.mGlSurfaceView.getHolder().setFormat(1);
        this.mGlSurfaceView.setRenderer(this.mRenderer);
        this.mGlSurfaceView.setRenderMode(0);
        this.mGlSurfaceView.requestRender();
    }

    public void requestRender() {
        GLSurfaceView gLSurfaceView = this.mGlSurfaceView;
        if (gLSurfaceView != null) {
            gLSurfaceView.requestRender();
        }
    }

    public void setUpCamera(Camera camera) {
        setUpCamera(camera, 0, false, false);
    }

    public void setUpCamera(Camera camera, int i, boolean z, boolean z2) {
        this.mGlSurfaceView.setRenderMode(1);
        if (Build.VERSION.SDK_INT > 10) {
            setUpCameraGingerbread(camera);
        } else {
            camera.setPreviewCallback(this.mRenderer);
            camera.startPreview();
        }
        Rotation rotation = Rotation.NORMAL;
        if (i == 90) {
            rotation = Rotation.ROTATION_90;
        } else if (i == 180) {
            rotation = Rotation.ROTATION_180;
        } else if (i == 270) {
            rotation = Rotation.ROTATION_270;
        }
        this.mRenderer.setRotationCamera(rotation, z, z2);
    }

    private void setUpCameraGingerbread(Camera camera) {
        this.mRenderer.setUpSurfaceTexture(camera);
    }

    public void setFilter(GPUImageFilter gPUImageFilter) {
        this.mFilter = gPUImageFilter;
        this.mRenderer.setFilter(gPUImageFilter);
        requestRender();
    }

    public void setImage(Bitmap bitmap) {
        this.mCurrentBitmap = bitmap;
        this.mRenderer.setImageBitmap(bitmap, false);
        requestRender();
    }

    public void setScaleType(ScaleType scaleType) {
        this.mScaleType = scaleType;
        this.mRenderer.setScaleType(scaleType);
        this.mRenderer.deleteImage();
        this.mCurrentBitmap = null;
        requestRender();
    }

    public void setRotation(Rotation rotation) {
        this.mRenderer.setRotation(rotation);
    }

    public void setRotation(Rotation rotation, boolean z, boolean z2) {
        this.mRenderer.setRotation(rotation, z, z2);
    }

    public void deleteImage() {
        this.mRenderer.deleteImage();
        this.mCurrentBitmap = null;
        requestRender();
    }

    public void setImage(Uri uri) {
        new LoadImageUriTask(this, uri).execute(new Void[0]);
    }

    public void setImage(File file) {
        new LoadImageFileTask(this, file).execute(new Void[0]);
    }

    private String getPath(Uri uri) {
        String str = null;
        Cursor query = this.mContext.getContentResolver().query(uri, new String[]{"_data"}, str, null, str);
        String string = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow("_data")) : null;
        query.close();
        return string;
    }

    public Bitmap getBitmapWithFilterApplied() {
        return getBitmapWithFilterApplied(this.mCurrentBitmap);
    }

    public Bitmap getBitmapWithFilterApplied(Bitmap bitmap) {


        Log.e("getBitmapWithF","vvvvvvv");
        if (this.mGlSurfaceView != null) {
            this.mRenderer.deleteImage();
            this.mRenderer.runOnDraw(new Runnable() { 
                @Override 
                public void run() {
                    synchronized (GPUImage.this.mFilter) {
                        GPUImage.this.mFilter.destroy();
                        GPUImage.this.mFilter.notify();
                    }
                }
            });
            synchronized (this.mFilter) {
                requestRender();
                try {
                    this.mFilter.wait();
                } catch (InterruptedException unused) {
                }
            }
        }
        GPUImageRenderer gPUImageRenderer = new GPUImageRenderer(this.mFilter);
        gPUImageRenderer.setRotation(Rotation.NORMAL, this.mRenderer.isFlippedHorizontally(), this.mRenderer.isFlippedVertically());
        gPUImageRenderer.setScaleType(this.mScaleType);
        PixelBuffer pixelBuffer = new PixelBuffer(bitmap.getWidth(), bitmap.getHeight());
        pixelBuffer.setRenderer(gPUImageRenderer);
        gPUImageRenderer.setImageBitmap(bitmap, false);
        Bitmap bitmap2 = pixelBuffer.getBitmap();
        this.mFilter.destroy();
        gPUImageRenderer.deleteImage();
        pixelBuffer.destroy();
        this.mRenderer.setFilter(this.mFilter);
        Bitmap bitmap3 = this.mCurrentBitmap;
        if (bitmap3 != null) {
            this.mRenderer.setImageBitmap(bitmap3, false);
        }
        requestRender();
        return bitmap2;
    }

    public static void getBitmapForMultipleFilters(Bitmap bitmap, List<GPUImageFilter> list, ResponseListener<Bitmap> responseListener) {
        if (list.isEmpty()) {
            return;
        }
        GPUImageRenderer gPUImageRenderer = new GPUImageRenderer(list.get(0));
        gPUImageRenderer.setImageBitmap(bitmap, false);
        PixelBuffer pixelBuffer = new PixelBuffer(bitmap.getWidth(), bitmap.getHeight());
        pixelBuffer.setRenderer(gPUImageRenderer);
        for (GPUImageFilter gPUImageFilter : list) {
            gPUImageRenderer.setFilter(gPUImageFilter);
            responseListener.response(pixelBuffer.getBitmap());
            gPUImageFilter.destroy();
        }
        gPUImageRenderer.deleteImage();
        pixelBuffer.destroy();
    }

    @Deprecated
    public void saveToPictures(String str, String str2, OnPictureSavedListener onPictureSavedListener) {
        saveToPictures(this.mCurrentBitmap, str, str2, onPictureSavedListener);
    }

    @Deprecated
    public void saveToPictures(Bitmap bitmap, String str, String str2, OnPictureSavedListener onPictureSavedListener) {
        new SaveTask(bitmap, str, str2, onPictureSavedListener).execute(new Void[0]);
    }

    public void runOnGLThread(Runnable runnable) {
        this.mRenderer.runOnDrawEnd(runnable);
    }

    public int getOutputWidth() {
        GPUImageRenderer gPUImageRenderer = this.mRenderer;
        if (gPUImageRenderer != null && gPUImageRenderer.getFrameWidth() != 0) {
            return this.mRenderer.getFrameWidth();
        }
        Bitmap bitmap = this.mCurrentBitmap;
        if (bitmap != null) {
            return bitmap.getWidth();
        }
        return ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }

    public int getOutputHeight() {
        GPUImageRenderer gPUImageRenderer = this.mRenderer;
        if (gPUImageRenderer != null && gPUImageRenderer.getFrameHeight() != 0) {
            return this.mRenderer.getFrameHeight();
        }
        Bitmap bitmap = this.mCurrentBitmap;
        if (bitmap != null) {
            return bitmap.getHeight();
        }
        return ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
    }

    
    @Deprecated
    
    public class SaveTask extends AsyncTask<Void, Void, Void> {
        private final Bitmap mBitmap;
        private final String mFileName;
        private final String mFolderName;
        public final Handler mHandler = new Handler();
        public final OnPictureSavedListener mListener;

        public SaveTask(Bitmap bitmap, String str, String str2, OnPictureSavedListener onPictureSavedListener) {
            this.mBitmap = bitmap;
            this.mFolderName = str;
            this.mFileName = str2;
            this.mListener = onPictureSavedListener;
        }

        @Override 
        public Void doInBackground(Void... voidArr) {
            saveImage(this.mFolderName, this.mFileName, GPUImage.this.getBitmapWithFilterApplied(this.mBitmap));
            return null;
        }

        private void saveImage(String str, String str2, Bitmap bitmap) {
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File file = new File(externalStoragePublicDirectory, str + "/" + str2);
            try {
                file.getParentFile().mkdirs();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, new FileOutputStream(file));
                MediaScannerConnection.scanFile(GPUImage.this.mContext, new String[]{file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() { 
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

    
    private class LoadImageUriTask extends LoadImageTask {
        private final Uri mUri;

        public LoadImageUriTask(GPUImage gPUImage, Uri uri) {
            super(gPUImage);
            this.mUri = uri;
        }

        @Override 
        public Bitmap decode(BitmapFactory.Options options) {
            try {
                if (!this.mUri.getScheme().startsWith("http") && !this.mUri.getScheme().startsWith("https")) {
                    return BitmapFactory.decodeStream(GPUImage.this.mContext.getContentResolver().openInputStream(this.mUri), null, options);
                }
                return BitmapFactory.decodeStream(new URL(this.mUri.toString()).openStream(), null, options);
            } catch (Exception unused) {
                return null;
            }
        }

        @Override 
        public int getImageOrientation() throws IOException {
            String str = null;
            Cursor query = GPUImage.this.mContext.getContentResolver().query(this.mUri, new String[]{"orientation"}, str, null, str);
            if (query == null || query.getCount() != 1) {
                return 0;
            }
            query.moveToFirst();
            int i = query.getInt(0);
            query.close();
            return i;
        }
    }

    
    private class LoadImageFileTask extends LoadImageTask {
        private final File mImageFile;

        public LoadImageFileTask(GPUImage gPUImage, File file) {
            super(gPUImage);
            this.mImageFile = file;
        }

        @Override 
        public Bitmap decode(BitmapFactory.Options options) {
            return BitmapFactory.decodeFile(this.mImageFile.getAbsolutePath(), options);
        }

        @Override 
        public int getImageOrientation() throws IOException {
            int attributeInt = new ExifInterface(this.mImageFile.getAbsolutePath()).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 1) {
                return 0;
            }
            if (attributeInt == 3) {
                return Constant.ORIENTATION_180;
            }
            if (attributeInt != 6) {
                return attributeInt != 8 ? 0 : 270;
            }
            return 90;
        }
    }

    
    private abstract class LoadImageTask extends AsyncTask<Void, Void, Bitmap> {
        private final GPUImage mGPUImage;
        private int mOutputHeight;
        private int mOutputWidth;

        public abstract Bitmap decode(BitmapFactory.Options options);

        public abstract int getImageOrientation() throws IOException;

        public LoadImageTask(GPUImage gPUImage) {
            this.mGPUImage = gPUImage;
        }

        @Override 
        public Bitmap doInBackground(Void... voidArr) {
            if (GPUImage.this.mRenderer != null && GPUImage.this.mRenderer.getFrameWidth() == 0) {
                try {
                    synchronized (GPUImage.this.mRenderer.mSurfaceChangedWaiter) {
                        GPUImage.this.mRenderer.mSurfaceChangedWaiter.wait(3000L);
                    }
                } catch (InterruptedException unused) {
                }
            }
            this.mOutputWidth = GPUImage.this.getOutputWidth();
            this.mOutputHeight = GPUImage.this.getOutputHeight();
            return loadResizedImage();
        }

        @Override 
        public void onPostExecute(Bitmap bitmap) {
            super.onPostExecute( bitmap);
            this.mGPUImage.deleteImage();
            this.mGPUImage.setImage(bitmap);
        }

        private Bitmap loadResizedImage() {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            decode(options);
            int i = 1;
            while (true) {
                if (!checkSize(options.outWidth / i > this.mOutputWidth, options.outHeight / i > this.mOutputHeight)) {
                    break;
                }
                i++;
            }
            int i2 = i - 1;
            if (i2 < 1) {
                i2 = 1;
            }
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = i2;
            options2.inPreferredConfig = Bitmap.Config.RGB_565;
            options2.inPurgeable = true;
            options2.inTempStorage = new byte[32768];
            Bitmap decode = decode(options2);
            if (decode == null) {
                return null;
            }
            return scaleBitmap(rotateImage(decode));
        }

        private Bitmap scaleBitmap(Bitmap bitmap) {
            int[] scaleSize = getScaleSize(bitmap.getWidth(), bitmap.getHeight());
            Log.e("scaleBitmap__1", "scaleBitmap: " + scaleSize[0]);
            Log.e("scaleBitmap__1", "scaleBitmap: " + scaleSize[1]);
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, scaleSize[0], scaleSize[1], true);
            if (createScaledBitmap != bitmap) {
                bitmap.recycle();
                System.gc();
                bitmap = createScaledBitmap;
            }
            if (GPUImage.this.mScaleType == ScaleType.CENTER_CROP) {
                int i = scaleSize[0] - this.mOutputWidth;
                int i2 = scaleSize[1] - this.mOutputHeight;
                Bitmap createBitmap = Bitmap.createBitmap(bitmap, i / 2, i2 / 2, scaleSize[0] - i, scaleSize[1] - i2);
                return createBitmap != bitmap ? createBitmap : bitmap;
            }
            return bitmap;
        }

        private int[] getScaleSize(int i, int i2) {
            float f;
            float f2;
            float f3 = i;
            float f4 = f3 / this.mOutputWidth;
            float f5 = i2;
            float f6 = f5 / this.mOutputHeight;
            if (GPUImage.this.mScaleType == ScaleType.CENTER_CROP ? f4 > f6 : f4 < f6) {
                f = this.mOutputHeight;
                f2 = (f / f5) * f3;
            } else {
                float f7 = this.mOutputWidth;
                float f8 = (f7 / f3) * f5;
                f2 = f7;
                f = f8;
            }
            return new int[]{Math.round(f2), Math.round(f)};
        }

        private boolean checkSize(boolean z, boolean z2) {
            if (GPUImage.this.mScaleType == ScaleType.CENTER_CROP) {
                if (!z || !z2) {
                    return false;
                }
            } else if (!z && !z2) {
                return false;
            }
            return true;
        }

        private Bitmap rotateImage(Bitmap bitmap) {
            if (bitmap == null) {
                return null;
            }
            try {
                int imageOrientation = getImageOrientation();
                if (imageOrientation == 0) {
                    return bitmap;
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(imageOrientation);
                return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            } catch (IOException unused) {
                return bitmap;
            }
        }
    }
}
