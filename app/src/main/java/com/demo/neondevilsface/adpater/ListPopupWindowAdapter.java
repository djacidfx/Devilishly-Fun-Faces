package com.demo.neondevilsface.adpater;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.neondevilsface.R;

import java.util.ArrayList;
import java.util.List;


public class ListPopupWindowAdapter extends BaseAdapter {
    private OnClickDeleteButtonListener clickDeleteButtonListener;
    private LayoutInflater layoutInflater;
    private Activity mActivity;
    private List<String> mDataSource;

    
    public interface OnClickDeleteButtonListener {
        void onClickDeleteButton(int i);
    }

    @Override 
    public long getItemId(int i) {
        return 0L;
    }

    public ListPopupWindowAdapter(Activity activity, List<String> list, OnClickDeleteButtonListener onClickDeleteButtonListener) {
        this.mDataSource = new ArrayList();
        this.mActivity = activity;
        this.mDataSource = list;
        this.layoutInflater = activity.getLayoutInflater();
        this.clickDeleteButtonListener = onClickDeleteButtonListener;
    }

    @Override 
    public int getCount() {
        return this.mDataSource.size();
    }

    @Override 
    public String getItem(int i) {
        return this.mDataSource.get(i);
    }

    @Override 
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View view2;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = this.layoutInflater.inflate(R.layout.item_list_popup_window, (ViewGroup) null);
            viewHolder.tvTitle = (TextView) view2.findViewById(R.id.text_title);
            viewHolder.main_layout = (LinearLayout) view2.findViewById(R.id.main_layout);
            view2.setTag(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvTitle.setText(getItem(i));
        viewHolder.main_layout.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view3) {
                ListPopupWindowAdapter.this.clickDeleteButtonListener.onClickDeleteButton(i);
                Log.e("onClick__p", "onClick: " + i);
            }
        });
        return view2;
    }

    
    public class ViewHolder {
        LinearLayout main_layout;
        private TextView tvTitle;

        public ViewHolder() {
        }
    }
}
