package com.demo.neondevilsface.colorshader.ambilwarna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.demo.neondevilsface.R;

import java.util.ArrayList;
import java.util.List;


public class ColorHistoryArrayAdapter extends ArrayAdapter<Integer> {
    private List<Integer> colorList;
    Context context;

    public ColorHistoryArrayAdapter(Context context, int i, List<Integer> list) {
        super(context, i);
        this.colorList = new ArrayList();
        this.context = context;
        this.colorList = list;
    }

    @Override 
    public int getCount() {
        return this.colorList.size();
    }

    @Override 
    public Integer getItem(int i) {
        return this.colorList.get(i);
    }

    @Override 
    public View getView(int i, View view, ViewGroup viewGroup) {
        int intValue = getCount() > 0 ? getItem((getCount() - i) - 1).intValue() : 0;
        if (view == null) {
            view = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.color_history_list_item, viewGroup, false);
        }
        ((ColorHistoryView) view.findViewById(R.id.color_history_view)).setColor(intValue);
        return view;
    }
}
