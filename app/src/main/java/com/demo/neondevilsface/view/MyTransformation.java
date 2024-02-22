package com.demo.neondevilsface.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import java.security.MessageDigest;


public class MyTransformation extends BitmapTransformation {
    private boolean Is_flip;

    public String getId() {
        return "com.msl.example.helpers.MyTransformation";
    }

    @Override 
    public void updateDiskCacheKey(MessageDigest messageDigest) {
    }

    public MyTransformation(Context context, boolean z) {
        this.Is_flip = z;
    }

    @Override 
    public Bitmap transform(BitmapPool bitmapPool, Bitmap bitmap, int i, int i2) {
        return rotateBitmap(bitmap, this.Is_flip);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, boolean z) {
        Matrix matrix = new Matrix();
        if (z) {
            matrix.preScale(-1.0f, 1.0f);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return null;
    }
}
