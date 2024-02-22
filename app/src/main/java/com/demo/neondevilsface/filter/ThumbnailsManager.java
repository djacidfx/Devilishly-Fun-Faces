package com.demo.neondevilsface.filter;

import android.content.Context;
import android.graphics.Bitmap;

import com.demo.neondevilsface.R;

import java.util.ArrayList;
import java.util.List;


public final class ThumbnailsManager {
    private static List<ThumbnailItem> filterThumbs = new ArrayList(10);
    private static List<ThumbnailItem> processedThumbs = new ArrayList(10);

    private ThumbnailsManager() {
    }

    public static void addThumb(ThumbnailItem thumbnailItem) {
        filterThumbs.add(thumbnailItem);
    }

    public static List<ThumbnailItem> processThumbs(Context context) {
        ThumbnailItem thumbnailItem = new ThumbnailItem();
        for (ThumbnailItem thumbnailItem2 : filterThumbs) {
            int dimension = (int) context.getResources().getDimension(R.dimen.thumbnail_size);
            thumbnailItem2.image = Bitmap.createScaledBitmap(thumbnailItem2.image, dimension, dimension, false);
            thumbnailItem.image = thumbnailItem2.image;
            thumbnailItem2.image = thumbnailItem2.filter.processFilter(thumbnailItem2.image);
            processedThumbs.add(thumbnailItem2);
        }
        return processedThumbs;
    }

    public static void clearThumbs() {
        filterThumbs = new ArrayList();
        processedThumbs = new ArrayList();
    }
}
