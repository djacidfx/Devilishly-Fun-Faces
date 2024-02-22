package com.demo.neondevilsface.adpater;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.comix.rounded.RoundedCornerImageView;
import com.demo.neondevilsface.R;
import com.demo.neondevilsface.interfac.ArtFilterItemClick;

import java.util.ArrayList;
import java.util.List;


public class ArtFilterAdpater extends RecyclerView.Adapter<ArtFilterAdpater.MyViewHolder> {
    ArrayList<Bitmap> ArtFilterList;
    List<String> ArtFilterNameList;
    ArtFilterItemClick artFilterItemClick;
    List<String> artfilternameListName;
    Context context;
    public int selected = 1;

    public ArtFilterAdpater(Context context, ArrayList<Bitmap> arrayList, List<String> list, List<String> list2) {
        this.ArtFilterList = arrayList;
        this.ArtFilterNameList = list;
        this.context = context;
        this.artfilternameListName = list2;
    }

    @Override 
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_art_filter, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        Glide.with(this.context).load(this.ArtFilterList.get(i)).dontTransform().into(myViewHolder.art_filter_item);
        TextView textView = myViewHolder.art_filter_name;
        textView.setText("" + this.artfilternameListName.get(i));
        if (i == this.selected) {
            myViewHolder.img_selected.setVisibility(View.VISIBLE);
            myViewHolder.art_filter_name.setSelected(true);
        } else {
            myViewHolder.img_selected.setVisibility(View.INVISIBLE);
            myViewHolder.art_filter_name.setSelected(false);
        }
        myViewHolder.item_art_filter.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (ArtFilterAdpater.this.artFilterItemClick != null) {
                    ArtFilterAdpater.this.selected = i;
                    ArtFilterAdpater.this.artFilterItemClick.ArtFilterClick(ArtFilterAdpater.this.ArtFilterNameList.get(i), i);
                    ArtFilterAdpater.this.notifyDataSetChanged();
                }
            }
        });
    }

    @Override 
    public int getItemCount() {
        return this.ArtFilterList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedCornerImageView art_filter_item;
        TextView art_filter_name;
        ImageView img_selected;
        LinearLayout item_art_filter;

        public MyViewHolder(View view) {
            super(view);
            this.art_filter_name = (TextView) view.findViewById(R.id.art_filter_name);
            this.item_art_filter = (LinearLayout) view.findViewById(R.id.item_art_filter);
            this.art_filter_item = (RoundedCornerImageView) view.findViewById(R.id.art_filter_item);
            this.img_selected = (ImageView) view.findViewById(R.id.img_selected);
        }
    }

    public void setClickListener(ArtFilterItemClick artFilterItemClick) {
        this.artFilterItemClick = artFilterItemClick;
    }
}
