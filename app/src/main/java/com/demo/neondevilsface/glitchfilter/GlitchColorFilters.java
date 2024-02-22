package com.demo.neondevilsface.glitchfilter;

import android.content.Context;
import android.util.Log;

import com.demo.neondevilsface.R;

import java.io.InputStream;


public class GlitchColorFilters extends GpuImageToneCurveFilterModified {
    Context context;

    public GlitchColorFilters(Context context, int i) {
        this.context = context;
        setFilterAcv(i);
    }

    public void setFilterAcv(int i) {
        InputStream openRawResource;
        this.context.getResources().openRawResource(R.raw.acvmoth);
        Log.e("setFilterAcv", "setFilterAcv: " + i);
        if (i == 1) {
            setGrayScale();
        } else if (i <= 5 && i != 1) {
            Log.e("setFilterAcv", "setFilterAcv: " + i);
            if (i == 0) {
                openRawResource = this.context.getResources().openRawResource(R.raw.acvnone);
            } else if (i == 2) {
                openRawResource = this.context.getResources().openRawResource(R.raw.acvmoth);
            } else if (i == 3) {
                openRawResource = this.context.getResources().openRawResource(R.raw.acvcold);
            } else if (i == 4) {
                openRawResource = this.context.getResources().openRawResource(R.raw.acvvintage);
            } else if (i == 5) {
                openRawResource = this.context.getResources().openRawResource(R.raw.acvaurora);
            } else {
                openRawResource = this.context.getResources().openRawResource(R.raw.acvnone);
            }
            setFromCurveFileInputStream(openRawResource);
        }
        onInitialized();
    }
}
