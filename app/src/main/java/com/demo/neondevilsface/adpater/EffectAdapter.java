package com.demo.neondevilsface.adpater;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
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


public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.EffectAdapterViewHolder> {
    Context context;
    EffectSelect effectSelect;
    ArrayList<String> effectlist;
    int height;

    
    public interface EffectSelect {
        void effectClick(int i);
    }

    public EffectAdapter(Context context, ArrayList<String> arrayList, EffectSelect effectSelect, int i) {
        this.context = context;
        this.effectlist = arrayList;
        this.effectSelect = effectSelect;
        this.height = i;
    }

    @Override 
    public EffectAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new EffectAdapterViewHolder(LayoutInflater.from(this.context).inflate(R.layout.row_filter_view, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(EffectAdapterViewHolder effectAdapterViewHolder, final int i) {
        RequestManager with = Glide.with(this.context);
        with.load(Uri.parse("file:///android_asset/" + this.effectlist.get(i))).diskCacheStrategy(DiskCacheStrategy.NONE).into(effectAdapterViewHolder.mThumbnail);
        Log.e("onBindViewHolder", "onBindViewHolder: " + this.effectlist.get(i));

        effectAdapterViewHolder.mThumbnail.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                EffectAdapter.this.notifyItemChanged(AdapterPositionUtils.effectPosition);
                AdapterPositionUtils.effectPosition = i;
                EffectAdapter.this.notifyItemChanged(i);
                EffectAdapter.this.effectSelect.effectClick(i);
            }
        });


        if (AdapterPositionUtils.effectPosition == i) {
            effectAdapterViewHolder.ivSelected.setVisibility(View.VISIBLE);
        } else {
            effectAdapterViewHolder.ivSelected.setVisibility(View.GONE);
        }
    }

    @Override 
    public int getItemCount() {
        return this.effectlist.size();
    }

    
    public static class EffectAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSelected;
        LinearLayout llFilter;
        ImageView mThumbnail;

        public EffectAdapterViewHolder(View view) {
            super(view);
            this.mThumbnail = (ImageView) view.findViewById(R.id.imgFilterView);
            this.ivSelected = (ImageView) view.findViewById(R.id.ivSelected);
            this.llFilter = (LinearLayout) view.findViewById(R.id.llFilter);
        }
    }
}
