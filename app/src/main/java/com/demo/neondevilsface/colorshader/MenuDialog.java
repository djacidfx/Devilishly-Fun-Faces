package com.demo.neondevilsface.colorshader;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.demo.neondevilsface.R;


public class MenuDialog extends Dialog {
    Context mContext;

    public MenuDialog(Context context) {
        super(context);
        this.mContext = context;
        requestWindowFeature(1);
    }

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((LinearLayout) LayoutInflater.from(this.mContext).inflate(R.layout.menu_dialog, (ViewGroup) null));
    }
}
