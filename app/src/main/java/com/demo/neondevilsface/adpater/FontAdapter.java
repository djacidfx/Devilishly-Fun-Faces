package com.demo.neondevilsface.adpater;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.neondevilsface.R;
import com.demo.neondevilsface.Utility.AdapterPositionUtils;
import com.demo.neondevilsface.interfac.FontInterface;

import java.util.ArrayList;


public class FontAdapter extends RecyclerView.Adapter<FontAdapter.FontViewHolder> {
    public static int selectedposition;
    FontInterface fontInterface;
    private ArrayList<String> fontstylename;
    Context mContext;
    Typeface position_font;
    int final_font_ttf = 0;
    private ArrayList<Typeface> fontstyle = new ArrayList<>();

    public FontAdapter(Context context, FontInterface fontInterface, Typeface typeface, ArrayList<String> arrayList) {
        this.fontstylename = new ArrayList<>();
        this.position_font = typeface;
        this.mContext = context;
        this.fontInterface = fontInterface;
        this.fontstylename = arrayList;
    }

    @Override 
    public FontViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new FontViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.font_item, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(FontViewHolder fontViewHolder, final int i) {
        fontViewHolder.textstyleitem.setText("Abc");
        if (AdapterPositionUtils.textFontPosition == i) {
            fontViewHolder.textstyleitem.setTextColor(this.mContext.getResources().getColor(R.color.white));
            fontViewHolder.select_img.setVisibility(View.VISIBLE);
        } else {
            fontViewHolder.textstyleitem.setTextColor(this.mContext.getResources().getColor(R.color.unSelectColor));
            fontViewHolder.select_img.setVisibility(View.GONE);
        }
        if (i == 0) {
            fontViewHolder.textstyleitem.setTypeface(Typeface.DEFAULT);
        } else {
            TextView textView = fontViewHolder.textstyleitem;
            AssetManager assets = this.mContext.getAssets();
            textView.setTypeface(Typeface.createFromAsset(assets, "fonts/" + this.fontstylename.get(i)));
        }
        fontViewHolder.fontbackground.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (FontAdapter.this.fontInterface != null) {
                    FontAdapter.this.fontInterface.fontclicked((String) FontAdapter.this.fontstylename.get(i), i);
                    FontAdapter.selectedposition = i;
                    AdapterPositionUtils.textFontPosition = i;
                    FontAdapter.this.notifyDataSetChanged();
                }
            }
        });
    }

    @Override 
    public int getItemCount() {
        return this.fontstylename.size();
    }

    
    public class FontViewHolder extends RecyclerView.ViewHolder {
        private ImageView checked;
        private FrameLayout fontbackground;
        private ImageView select_img;
        private TextView textstyleitem;

        public FontViewHolder(View view) {
            super(view);
            this.textstyleitem = (TextView) this.itemView.findViewById(R.id.textstyleitem);
            this.fontbackground = (FrameLayout) this.itemView.findViewById(R.id.fontbackground);
            this.checked = (ImageView) this.itemView.findViewById(R.id.checked);
            this.select_img = (ImageView) this.itemView.findViewById(R.id.select_img);
        }
    }
}
