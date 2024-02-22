package com.demo.neondevilsface.Utility;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class RealPathUtils {
    public static String filePath;

    public static String getPath(Uri uri, Context context) {
        Uri uri2;

        Cursor cursor = null;
        if ((Build.VERSION.SDK_INT >= 19) && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {

                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                if ("primary".equalsIgnoreCase(DocumentsContract.getDocumentId(uri).split(":")[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                String documentId = DocumentsContract.getDocumentId(uri);
                if (!documentId.contains(".pdf")) {
                    documentId = documentId + ".pdf";
                }
                Log.e("file__path__", "isExternalStorageDocument: " + documentId);
                try {
                    InputStream openInputStream = context.getContentResolver().openInputStream(uri);
                    File file = new File(context.getCacheDir().getAbsolutePath() + "/" + documentId);
                    writeFile(openInputStream, file);
                    filePath = file.getAbsolutePath();
                    Log.e("file__path__", "isExternalStorageDocument: " + filePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return filePath;
            } else if (isDownloadsDocument(uri)) {
                if (Build.VERSION.SDK_INT >= 23) {
                    try {
                        Cursor query = context.getContentResolver().query(uri, new String[]{"_display_name"}, null, null, null);
                        if (query != null) {
                            try {
                                if (query.moveToFirst()) {
                                    String str = Environment.getExternalStorageDirectory().toString() + "/Download/" + query.getString(0);
                                    if (!TextUtils.isEmpty(str)) {
                                        if (query != null) {
                                            query.close();
                                        }
                                        return str;
                                    }
                                }
                            } catch (Throwable th) {
                                th = th;
                                cursor = query;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                throw th;
                            }
                        }
                        if (query != null) {
                            query.close();
                        }
                        String documentId2 = DocumentsContract.getDocumentId(uri);
                        if (!TextUtils.isEmpty(documentId2)) {
                            if (documentId2.startsWith("raw:")) {
                                return documentId2.replaceFirst("raw:", "");
                            }
                            try {
                                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse(new String[]{"content://downloads/public_downloads", "content://downloads/my_downloads"}[0]), Long.valueOf(documentId2).longValue()), null, null);
                            } catch (NumberFormatException unused) {
                                return uri.getPath().replaceFirst("^/document/raw:", "").replaceFirst("^raw:", "");
                            }
                        }
                    } catch (Throwable th2) {

                    }
                } else {
                    String documentId3 = DocumentsContract.getDocumentId(uri);
                    if (documentId3.startsWith("raw:")) {
                        return documentId3.replaceFirst("raw:", "");
                    }
                    try {
                        uri2 = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId3).longValue());
                    } catch (NumberFormatException e2) {
                        e2.printStackTrace();
                        uri2 = null;
                    }
                    if (uri2 != null) {
                        return getDataColumn(context, uri2, null, null);
                    }
                }
            } else if (isMediaDocument(uri)) {
                String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
                String str2 = split2[0];
                Log.e("getPathFromUri", "getPathFromUri_type: " + str2);
                if ("image".equals(str2)) {
                    return getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_id=?", new String[]{split2[1]});
                }
                if ("video".equals(str2)) {
                    return getDataColumn(context, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "_id=?", new String[]{split2[1]});
                }
                if ("audio".equals(str2)) {
                    return getDataColumn(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "_id=?", new String[]{split2[1]});
                }
                Log.e("file__path__", "isMediaDocument: " + uri);
                String documentId4 = DocumentsContract.getDocumentId(uri);
                if (!documentId4.contains(".pdf")) {
                    documentId4 = documentId4 + ".pdf";
                }
                Log.e("file__path__", "isMediaDocument: " + documentId4);
                try {
                    InputStream openInputStream2 = context.getContentResolver().openInputStream(uri);
                    File file2 = new File(context.getCacheDir().getAbsolutePath() + "/" + documentId4);
                    writeFile(openInputStream2, file2);
                    filePath = file2.getAbsolutePath();
                    Log.e("file__path__", "isMediaDocument: " + filePath);
                } catch (FileNotFoundException e3) {
                    e3.printStackTrace();
                }
                return filePath;
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    
    
    
    
    
    public static boolean writeFile(InputStream inputStream, File file) {
        FileOutputStream fileOutputStream = null;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Exception e) {
                e = e;
            }
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read > 0) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        fileOutputStream.close();
                        inputStream.close();
                        return true;
                    }
                }
            } catch (Exception e2) {

                fileOutputStream2 = fileOutputStream;
                if (fileOutputStream2 != null) {
                    fileOutputStream2.close();
                }
                inputStream.close();
                return true;
            } catch (Throwable th2) {
                fileOutputStream2 = fileOutputStream;
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                inputStream.close();
            }
        } catch (IOException e4) {
            e4.printStackTrace();
            return true;
        }
        return false;
    }
}
