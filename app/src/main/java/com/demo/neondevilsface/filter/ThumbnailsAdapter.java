package com.demo.neondevilsface.filter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.neondevilsface.R;
import com.demo.neondevilsface.Utility.AdapterPositionUtils;

import java.util.List;


public class ThumbnailsAdapter extends RecyclerView.Adapter<ThumbnailsAdapter.ThumbnailsViewHolder> {
    private static final String TAG = "THUMBNAILS_ADAPTER";
    private List<ThumbnailItem> dataSet;
    int height;
    private ThumbnailCallback thumbnailCallback;

    public ThumbnailsAdapter(List<ThumbnailItem> list, ThumbnailCallback thumbnailCallback, int i) {
        Log.v(TAG, "Thumbnails Adapter has " + list.size() + " items");
        this.dataSet = list;
        this.thumbnailCallback = thumbnailCallback;
        this.height = i;
    }

    @Override 
    public ThumbnailsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.v(TAG, "On Create View Holder Called");
        return new ThumbnailsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_filter_view, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(ThumbnailsViewHolder thumbnailsViewHolder, final int i) {
        final ThumbnailItem thumbnailItem = this.dataSet.get(i);
        Log.v(TAG, "On Bind View Called");
        thumbnailsViewHolder.thumbnail.setImageBitmap(thumbnailItem.image);
        thumbnailsViewHolder.thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        thumbnailsViewHolder.thumbnail.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (AdapterPositionUtils.beautyPosition != i) {
                    ThumbnailsAdapter.this.thumbnailCallback.onThumbnailClick(thumbnailItem.filter, i);
                    AdapterPositionUtils.beautyPosition = i;
                    ThumbnailsAdapter.this.notifyDataSetChanged();
                }
            }
        });
        if (AdapterPositionUtils.beautyPosition == i) {
            thumbnailsViewHolder.ivSelected.setVisibility(View.VISIBLE);
        } else {
            thumbnailsViewHolder.ivSelected.setVisibility(View.GONE);
        }
    }

    @Override 
    public int getItemCount() {
        return this.dataSet.size();
    }


    public static class ThumbnailsViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivSelected;
        LinearLayout llFilter;
        public ImageView thumbnail;

        public ThumbnailsViewHolder(View view) {
            super(view);
            this.thumbnail = (ImageView) view.findViewById(R.id.imgFilterView);
            this.ivSelected = (ImageView) view.findViewById(R.id.ivSelected);
            this.llFilter = (LinearLayout) view.findViewById(R.id.llFilter);
        }
    }
}
