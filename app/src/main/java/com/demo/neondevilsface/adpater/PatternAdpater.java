package com.demo.neondevilsface.adpater;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.neondevilsface.R;
import com.demo.neondevilsface.Utility.AdapterPositionUtils;

import java.util.ArrayList;


public class PatternAdpater extends RecyclerView.Adapter<PatternAdpater.PatternAdapterViewHolder> {
    Context context;
    ArrayList<String> effectlist;
    int height;
    PatterenSelect patterenSelect;

    
    public interface PatterenSelect {
        void patternclick(int i);
    }

    public PatternAdpater(Context context, ArrayList<String> arrayList, PatterenSelect patterenSelect, int i) {
        this.context = context;
        this.effectlist = arrayList;
        this.patterenSelect = patterenSelect;
        this.height = i;
    }

    @Override 
    public PatternAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PatternAdapterViewHolder(LayoutInflater.from(this.context).inflate(R.layout.row_filter_view, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(PatternAdapterViewHolder patternAdapterViewHolder, final int i) {
        RequestManager with = Glide.with(this.context);
        with.load(Uri.parse("file:///android_asset/" + this.effectlist.get(i))).diskCacheStrategy(DiskCacheStrategy.NONE).into(patternAdapterViewHolder.mThumbnail);











        patternAdapterViewHolder.mThumbnail.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                PatternAdpater.this.notifyItemChanged(AdapterPositionUtils.patternPosition);
                AdapterPositionUtils.patternPosition = i;
                PatternAdpater.this.notifyItemChanged(i);
                PatternAdpater.this.patterenSelect.patternclick(i);
            }
        });
        if (AdapterPositionUtils.patternPosition == i) {
            patternAdapterViewHolder.ivSelected.setVisibility(View.VISIBLE);
        } else {
            patternAdapterViewHolder.ivSelected.setVisibility(View.GONE);
        }
    }

    @Override 
    public int getItemCount() {
        return this.effectlist.size();
    }

    
    public static class PatternAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSelected;
        LinearLayout llFilter;
        ImageView mThumbnail;

        public PatternAdapterViewHolder(View view) {
            super(view);
            this.mThumbnail = (ImageView) view.findViewById(R.id.imgFilterView);
            this.ivSelected = (ImageView) view.findViewById(R.id.ivSelected);
            this.llFilter = (LinearLayout) view.findViewById(R.id.llFilter);
        }
    }
}
