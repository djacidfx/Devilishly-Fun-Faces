package com.demo.neondevilsface.adpater;

import android.view.View;
import java.util.ArrayList;


public interface OnItemClick {
    void CheckEmpty(Boolean bool);

    void OnItemCLICK(String str);

    void SelectedList(ArrayList<Image> arrayList);

    void ShareItemClick(String str, int i, Boolean bool, View view);
}
