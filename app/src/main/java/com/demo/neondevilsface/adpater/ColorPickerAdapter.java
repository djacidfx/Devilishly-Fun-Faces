package com.demo.neondevilsface.adpater;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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


public class ColorPickerAdapter extends RecyclerView.Adapter<ColorPickerAdapter.ViewHolder> {
    int SelectPos;
    private List<Integer> colorPickerColors;
    private Context context;
    private LayoutInflater inflater;
    int mColorCode;
    private OnColorPickerClickListener onColorPickerClickListener;

    
    public interface OnColorPickerClickListener {
        void onColorPickerClickListener(int i, int i2, ViewHolder viewHolder);
    }

    public ColorPickerAdapter(Context context, int i, int i2, List<Integer> list) {
        this.colorPickerColors = list;
        this.context = context;
        this.SelectPos = i2;
        this.mColorCode = i;
        this.inflater = LayoutInflater.from(context);
    }

    @Override 
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this.inflater.inflate(R.layout.color_picker_item_list1, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.colorPickerView.setBackgroundColor(this.colorPickerColors.get(i).intValue());
        if (AdapterPositionUtils.textColorPosition == i && i != 0) {
            viewHolder.selectImg.setVisibility(View.VISIBLE);
        } else {
            viewHolder.selectImg.setVisibility(View.GONE);
        }
        Log.e("onBindViewHolder", "onBindViewHolder: " + i);
        if (i == 0) {
            viewHolder.colorPickerView1.setVisibility(View.VISIBLE);
            viewHolder.colorPickerView.setVisibility(View.GONE);
        } else {
            viewHolder.colorPickerView1.setVisibility(View.GONE);
            viewHolder.colorPickerView.setVisibility(View.VISIBLE);
        }
        viewHolder.llStickerMain.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (ColorPickerAdapter.this.onColorPickerClickListener != null) {
                    ColorPickerAdapter.this.onColorPickerClickListener.onColorPickerClickListener(i, ((Integer) ColorPickerAdapter.this.colorPickerColors.get(i)).intValue(), viewHolder);
                }
                ColorPickerAdapter.this.SelectPos = i;
                AdapterPositionUtils.textColorPosition = i;
                ColorPickerAdapter colorPickerAdapter = ColorPickerAdapter.this;
                colorPickerAdapter.mColorCode = ((Integer) colorPickerAdapter.colorPickerColors.get(i)).intValue();
                ColorPickerAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override 
    public int getItemCount() {
        return this.colorPickerColors.size();
    }

    private void buildColorPickerView(View view, int i) {
        view.setVisibility(View.VISIBLE);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.setIntrinsicHeight(20);
        shapeDrawable.setIntrinsicWidth(20);
        shapeDrawable.setBounds(new Rect(0, 0, 20, 20));
        shapeDrawable.getPaint().setColor(i);
        ShapeDrawable shapeDrawable2 = new ShapeDrawable(new OvalShape());
        shapeDrawable2.setIntrinsicHeight(5);
        shapeDrawable2.setIntrinsicWidth(5);
        shapeDrawable2.setBounds(new Rect(0, 0, 5, 5));
        shapeDrawable2.getPaint().setColor(-1);
        shapeDrawable2.setPadding(10, 10, 10, 10);
        view.setBackgroundDrawable(new LayerDrawable(new Drawable[]{shapeDrawable2, shapeDrawable}));
    }

    public void setOnColorPickerClickListener(OnColorPickerClickListener onColorPickerClickListener) {
        this.onColorPickerClickListener = onColorPickerClickListener;
    }

    
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View colorPickerView;
        public ImageView colorPickerView1;
        LinearLayout llStickerMain;
        public ImageView selectImg;

        public ViewHolder(View view) {
            super(view);
            this.colorPickerView = view.findViewById(R.id.color_picker_view);
            this.colorPickerView1 = (ImageView) view.findViewById(R.id.colorPickerView);
            this.selectImg = (ImageView) view.findViewById(R.id.color_select_view);
            this.llStickerMain = (LinearLayout) view.findViewById(R.id.llStickerMain);
        }
    }
}
