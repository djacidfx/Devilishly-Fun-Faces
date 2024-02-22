package com.demo.neondevilsface.glitchfilter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.demo.neondevilsface.R;


public class GlitchDynamicOptions {
    Context context;
    DynamicOptionsCallback optionsCallback;
    public String filterType = "r";
    public float intensity = 0.0f;
    public float rgbShift = 0.032f;
    public float thickness = 5.0f;
    public int waveChoosen = 0;

    public GlitchDynamicOptions(Context context, DynamicOptionsCallback dynamicOptionsCallback) {
        this.context = context;
        this.optionsCallback = dynamicOptionsCallback;
    }

    public void prepareMenuOptions() {
        ((SeekBar) ((Activity) this.context).findViewById(R.id.art_filter_seekbar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Log.e("onProgressChanged", "onProgressChanged: " + i);
                float f = (float) (i + (-100));
                GlitchDynamicOptions.this.rgbShift = f / 1000.0f;
                Log.e("onProgressChanged", "onProgressChanged: " + i);
                Log.e("onProgressChanged", "rgbShift: " + GlitchDynamicOptions.this.rgbShift);
                if (GlitchDynamicOptions.this.filterType.equalsIgnoreCase("glitch")) {
                    if (i > 100) {
                        GlitchDynamicOptions.this.rgbShift = f;
                    } else {
                        GlitchDynamicOptions.this.rgbShift = i;
                    }
                    Log.e("rgbShift___", "onProgressChanged: " + GlitchDynamicOptions.this.rgbShift);
                    GlitchDynamicOptions.this.optionsCallback.onDynamicOptionsChanged(GlitchDynamicOptions.this.filterType, GlitchDynamicOptions.this.waveChoosen, 0.0f, GlitchDynamicOptions.this.rgbShift, GlitchDynamicOptions.this.thickness);
                    return;
                }
                GlitchDynamicOptions.this.applyChange();
            }
        });
        ((SeekBar) ((Activity) this.context).findViewById(R.id.art_seekbar)).setVisibility(View.GONE);
        int i = 0;
        ((SeekBar) ((Activity) this.context).findViewById(R.id.art_filter_seekbar)).setVisibility(View.VISIBLE);
        final String[] strArr = {"r", "g", "b", "rr", "gg", "bb", "glitch"};
        while (i < 7) {
            Resources resources = this.context.getResources();
            StringBuilder sb = new StringBuilder();
            sb.append("shift");
            int i2 = i + 1;
            sb.append(i2);
            View findViewById = ((Activity) this.context).findViewById(resources.getIdentifier(sb.toString(), "id", this.context.getPackageName()));
            Log.e("prepareMenuOptions", "prepareMenuOptions: " + ((Activity) this.context).findViewById(resources.getIdentifier(sb.toString(), "id", this.context.getPackageName())));
            int finalI = i;
            findViewById.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    GlitchDynamicOptions.this.filterType = strArr[finalI];
                    Log.e("Onglitch_filter", "onClick: " + GlitchDynamicOptions.this.filterType);
                    GlitchDynamicOptions.this.applyChange();
                    if (GlitchDynamicOptions.this.filterType.equalsIgnoreCase("glitch")) {
                        new GlitchFilters(GlitchDynamicOptions.this.context, false);
                        return;
                    }
                    new GlitchFilters(GlitchDynamicOptions.this.context, true);
                    GlitchDynamicOptions.this.rgbShift = 0.015f;
                }
            });
            i = i2;
        }
    }

    public void applyChange() {
        if (this.filterType.equalsIgnoreCase("glitch")) {
            this.optionsCallback.onDynamicOptionsChanged(this.filterType, this.waveChoosen, 0.0f, 10.0f, this.thickness);
        } else {
            this.optionsCallback.onDynamicOptionsChanged(this.filterType, this.waveChoosen, this.rgbShift, this.intensity, this.thickness);
        }
    }
}
