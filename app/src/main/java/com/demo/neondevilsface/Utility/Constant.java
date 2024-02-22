package com.demo.neondevilsface.Utility;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;


public class Constant {
    public static int CROP_CAMERA = 1002;
    public static int CROP_GALLERY = 1001;
    public static Bitmap CropBitmapSave = null;
    public static final int FLIP_HORIZONTAL = 2;
    public static final int FLIP_VERTICAL = 1;
    public static Uri ImageUri = null;
    public static float MaxHeight = 0.0f;
    public static float MaxWidth = 0.0f;
    public static final int ORIENTATION_180 = 180;
    public static int RC_CAMERA = 102;
    public static int RC_GALLERY = 101;
    public static int RC_MY_CREATION = 103;
    public static int RESULT_CODE_CAMERA = 202;
    public static int RESULT_CODE_GALLERY = 201;
    public static final int SELECT_PICTURE_FROM_CAMERA = 401;
    public static int blur_bitmap_height;
    public static int blur_bitmap_width;
    public static Bitmap color_bitmap;
    public static int out_bitmap_height;
    public static int out_bitmap_width;
    public static Bitmap rotrate_bitmap;
    public static File saveCropImageFile;
    public static File saveapplyImageFile;
    public static int sticker_layout_height;
    public static int sticker_layout_width;

    public static Bitmap rotateBitmap(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        switch (i) {
            case 2:
                matrix.setScale(-1.0f, 1.0f);
                break;
            case 3:
                matrix.setRotate(180.0f);
                break;
            case 4:
                matrix.setRotate(180.0f);
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 5:
                matrix.setRotate(90.0f);
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 6:
                matrix.setRotate(90.0f);
                break;
            case 7:
                matrix.setRotate(-90.0f);
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 8:
                matrix.setRotate(-90.0f);
                break;
            default:
                return bitmap;
        }
        try {
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap viewToBitmap(View view, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public static Bitmap makeTransparent(Bitmap bitmap, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        Paint paint = new Paint();
        paint.setAlpha(i);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public static Bitmap overlay123(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap2.getWidth(), bitmap2.getHeight(), bitmap.getConfig());
        Bitmap resizedBitmap = getResizedBitmap(bitmap, bitmap2.getHeight(), (int) (((float) bitmap.getWidth()) * (((float) bitmap2.getHeight()) / ((float) bitmap.getHeight()))));
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(resizedBitmap, (((float) bitmap2.getWidth()) - (((float) bitmap.getWidth()) * (((float) bitmap2.getHeight()) / ((float) bitmap.getHeight())))) / 2.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, new Matrix(), null);
        return createBitmap;
    }


    public static void getDropboxIMGSize(Uri uri) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
        int i = options.outHeight;
        int i2 = options.outWidth;
        Log.e("getDropboxIMGSize", "getDropboxIMGSize: " + options.outHeight);
        Log.e("getDropboxIMGSize", "getDropboxIMGSize: " + i2);
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap, int i, int i2) {
        float f;
        float f2;
        Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        try {
            float width = copy.getWidth();
            float height = copy.getHeight();
            if (width > height) {
                f2 = i2;
                f = height / (width / f2);
            } else {
                float f3 = i;
                float f4 = width / (height / f3);
                f = f3;
                f2 = f4;
            }
            return Bitmap.createScaledBitmap(copy, (int) f2, (int) f, true);
        } catch (Exception unused) {
            return null;
        }
    }

    public static Bitmap RotateBitmap(Bitmap bitmap, float f) {
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Uri saveToInternalStorage(Bitmap bitmap, Context context) {
        File createImageFile = createImageFile(context);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(createImageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            if (bitmap == null) {
                return null;
            }
            return getImageContentUri(context, createImageFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File saveToInternalStorageFile(Bitmap bitmap, Context context) {
        File createImageFile = createImageFile(context);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(createImageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            if (bitmap != null) {
                getImageContentUri(context, createImageFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createImageFile;
    }

    public static File createImageFile(Context context) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), context.getPackageName());
        if (file.exists() || file.mkdirs()) {
            return new File(file.getPath() + File.separator + "Img_" + System.currentTimeMillis() + ".jpg");
        }
        return null;
    }

    public static Uri getImageContentUri(Context context, File file) {
        String absolutePath = file.getAbsolutePath();
        Cursor query = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=? ", new String[]{absolutePath}, null);
        if (query != null && query.moveToFirst()) {
            @SuppressLint("Range") int i = query.getInt(query.getColumnIndex("_id"));
            query.close();
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            return Uri.withAppendedPath(uri, "" + i);
        } else if (file.exists()) {
            if (Build.VERSION.SDK_INT > 29) {
                ContentResolver contentResolver = context.getContentResolver();
                Uri contentUri = MediaStore.Images.Media.getContentUri("external_primary");
                ContentValues contentValues = new ContentValues();
                contentValues.put("_display_name", file.getName());
                contentValues.put("mime_type", "image/jpg");
                contentValues.put("relative_path", "DCIM/" + UUID.randomUUID().toString());
                contentValues.put("is_pending", (Integer) 1);
                Uri insert = contentResolver.insert(contentUri, contentValues);
                contentValues.clear();
                contentValues.put("is_pending", (Integer) 0);
                contentResolver.update(contentUri, contentValues, null, null);
                return insert;
            }
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("_data", absolutePath);
            return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues2);
        } else {
            return null;
        }
    }
}
