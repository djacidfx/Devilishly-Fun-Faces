package com.demo.neondevilsface.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.demo.neondevilsface.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class Utils {
    public static final String TEMP_FILE_NAME = "temp.jpg";
    public static final String TEMP_FILE_NAME2 = "temp2.jpg";
    public static String applicationname;
    public static Bitmap bits;
    public static Bitmap bitsShape;
    public static byte[] bytearray;
    public static String camera;
    public static int height;
    public static String pth;
    public static Uri selectedImageUri;
    public static int width;
    public static ArrayList<String> listImages = new ArrayList<>();
    public static Bitmap tempbitmap = null;

    public static int getWidthScreen(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static File saveBitmapImage(Context context, Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + context.getResources().getString(R.string.app_name) + File.separator + "TempFolder", TEMP_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static int dpToPx(Context context, int i) {
        return Math.round(i * context.getResources().getDisplayMetrics().density);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static File createImageFile(Context context) throws IOException {
        return File.createTempFile("temp", ".jpg", context.getExternalFilesDir(Environment.getExternalStorageState()));
    }

    public static void deleteTempImageFile(Context context) {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + context.getResources().getString(R.string.app_name) + File.separator + "TempFolder");
        if (file.exists()) {
            if (file.isDirectory()) {
                for (String str : file.list()) {
                    new File(file, str).delete();
                }
            }
            file.delete();
        }
    }
}
