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
import com.demo.neondevilsface.interfac.SetStickerListener;

import java.util.ArrayList;


public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.MyViewHolder> {
    ArrayList<String> StickerList;
    Context context;
    SetStickerListener setStickerListener;

    public StickerAdapter(ArrayList<String> arrayList, SetStickerListener setStickerListener, Context context) {
        this.StickerList = arrayList;
        this.setStickerListener = setStickerListener;
        this.context = context;
    }

    @Override 
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sticker_single_view, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        try {
            RequestManager with = Glide.with(this.context);
            with.load(Uri.parse("file:///android_asset/" + this.StickerList.get(i))).diskCacheStrategy(DiskCacheStrategy.NONE).into(myViewHolder.img_sticker);
            myViewHolder.main_sticker_lay.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    StickerAdapter.this.setStickerListener.SelectSticker(StickerAdapter.this.StickerList.get(i), "file:///android_asset/" + StickerAdapter.this.StickerList.get(i));
                }
            });
        } catch (OutOfMemoryError unused) {
        }
    }

    @Override 
    public int getItemCount() {
        return this.StickerList.size();
    }

    
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_sticker;
        LinearLayout main_sticker_lay;

        public MyViewHolder(View view) {
            super(view);
            this.main_sticker_lay = (LinearLayout) view.findViewById(R.id.main_sticker_lay);
            this.img_sticker = (ImageView) view.findViewById(R.id.img_sticker);
        }
    }
}
