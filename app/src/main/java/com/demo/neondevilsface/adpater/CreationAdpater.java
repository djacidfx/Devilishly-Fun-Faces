package com.demo.neondevilsface.adpater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.comix.rounded.RoundedCornerImageView;
import com.demo.neondevilsface.R;
import com.demo.neondevilsface.Utility.AppUtil;
import com.demo.neondevilsface.Utility.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class CreationAdpater extends RecyclerView.Adapter<CreationAdpater.ViewHolder> {
    private static final long KiB = 1024;
    private static final long MiB = 1048576;
    private static final DecimalFormat format = new DecimalFormat("#.##");
    private static Boolean is_long_press = false;
    private OnItemClick clickListener;
    private Context context;
    private ArrayList<Image> image_list;
    private ArrayList<Image> selected_list = new ArrayList<>();

    public CreationAdpater(Context context, ArrayList<Image> arrayList) {
        this.context = context;
        this.image_list = arrayList;
    }

    @Override 
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.square_row_layout, viewGroup, false), this.context);
    }

    @Override 
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final Image image = this.image_list.get(i);
        ViewGroup.LayoutParams layoutParams = viewHolder.main_image.getLayoutParams();
        int widthScreen = (Utils.getWidthScreen(this.context) / 3) - 20;
        layoutParams.height = widthScreen;
        layoutParams.width = widthScreen;
        viewHolder.main_image.setLayoutParams(layoutParams);
        viewHolder.image_name.setText(image.getPath().substring(image.getPath().lastIndexOf("/") + 1));
        Glide.with(this.context).load(image.getPath()).into(viewHolder.main_image);
        if (is_long_press.booleanValue()) {
            viewHolder.share_delete_option.setVisibility(View.GONE);
        } else {
            viewHolder.share_delete_option.setVisibility(View.VISIBLE);
        }
        if (image.getSelected()) {
            viewHolder.select_square.setVisibility(View.VISIBLE);
        } else {
            viewHolder.select_square.setVisibility(View.GONE);
        }
        viewHolder.main_image.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Log.e("onClick___test", "onClick: " + CreationAdpater.is_long_press);
                if (CreationAdpater.this.clickListener != null) {
                    if (!CreationAdpater.is_long_press.booleanValue()) {
                        if (AppUtil.isClickable()) {
                            CreationAdpater.this.clickListener.OnItemCLICK(image.getPath());
                            return;
                        }
                        return;
                    }
                    if (!image.getSelected()) {
                        if (!CreationAdpater.this.selected_list.contains(image)) {
                            image.setSelected(true);
                            CreationAdpater.this.selected_list.add(image);
                            viewHolder.select_square.setVisibility(View.VISIBLE);
                        }
                    } else {
                        image.setSelected(false);
                        CreationAdpater.this.selected_list.remove(image);
                        viewHolder.select_square.setVisibility(View.GONE);
                    }
                    if (CreationAdpater.this.selected_list.size() == 0) {
                        Boolean unused = CreationAdpater.is_long_press = false;
                    }
                    if (CreationAdpater.this.clickListener != null) {
                        CreationAdpater.this.clickListener.SelectedList(CreationAdpater.this.selected_list);
                    }
                    CreationAdpater.this.notifyDataSetChanged();
                }
            }
        });
        viewHolder.main_image.setOnLongClickListener(new View.OnLongClickListener() { 
            @Override 
            public boolean onLongClick(View view) {
                Boolean unused = CreationAdpater.is_long_press = true;
                Log.e("onLongClick", "onLongClick: " + image.getSelected());
                image.setSelected(true);
                CreationAdpater.this.selected_list.add(image);
                viewHolder.select_square.setVisibility(View.VISIBLE);
                if (CreationAdpater.this.clickListener != null) {
                    CreationAdpater.this.clickListener.SelectedList(CreationAdpater.this.selected_list);
                }
                CreationAdpater.this.notifyDataSetChanged();
                return false;
            }
        });
        if (this.image_list.size() == 0) {
            this.clickListener.CheckEmpty(true);
        }
        viewHolder.share_delete_option.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (AppUtil.isClickable()) {
                    CreationAdpater.this.clickListener.ShareItemClick(image.getPath(), i, false, viewHolder.main_image);
                }
            }
        });
    }

    @Override 
    public int getItemCount() {
        return this.image_list.size();
    }

    public void setClickListener(OnItemClick onItemClick) {
        this.clickListener = onItemClick;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView image_name;
        RoundedCornerImageView main_image;
        LinearLayout main_layout;
        LinearLayout select_square;
        LinearLayout share_delete_option;

        public ViewHolder(View view, Context context) {
            super(view);
            this.main_image = (RoundedCornerImageView) view.findViewById(R.id.main_image);
            this.main_layout = (LinearLayout) view.findViewById(R.id.main_layout);
            this.share_delete_option = (LinearLayout) view.findViewById(R.id.share_delete_option);
            this.image_name = (TextView) view.findViewById(R.id.image_name);
            this.select_square = (LinearLayout) view.findViewById(R.id.select_square);
        }
    }

    public ArrayList<Image> getSelected_list() {
        return this.selected_list;
    }

    public void remove_all() {
        is_long_press = false;
        this.selected_list.clear();
        for (int i = 0; i < this.image_list.size(); i++) {
            if (this.image_list.get(i).getSelected()) {
                this.image_list.get(i).setSelected(false);
            }
        }
        notifyDataSetChanged();
        this.clickListener.SelectedList(this.selected_list);
    }

    public void Select_all() {
        is_long_press = false;
        this.selected_list.clear();
        for (int i = 0; i < this.image_list.size(); i++) {
            this.image_list.get(i).setSelected(true);
            this.selected_list.add(this.image_list.get(i));
        }
        notifyDataSetChanged();
        this.clickListener.SelectedList(this.selected_list);
    }
}
