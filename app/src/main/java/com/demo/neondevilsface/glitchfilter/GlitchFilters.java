package com.demo.neondevilsface.glitchfilter;

import android.content.Context;
import com.demo.neondevilsface.fragment.GPUImageBrightnessFilter;
import com.demo.neondevilsface.fragment.GPUImageContrastFilter;
import com.demo.neondevilsface.fragment.GPUImageSaturationFilter;


public class GlitchFilters {
    Boolean aBoolean;
    Context context;
    public GPUImageFilter glitchColorFilters;
    public GPUImageFilterGroup group;
    public GPUImageBrightnessFilter brightness = new GPUImageBrightnessFilter(0.0f);
    public GPUImageContrastFilter contrast = new GPUImageContrastFilter(1.0f);
    public GlitchRshiftFilters glitchRshiftFilters = new GlitchRshiftFilters("r");
    public GlitchMaskFilters maskFilters = null;
    public GlitchMaskFilters2 maskFilters2 = null;
    public GlitchScreenFilters overlayFilter = null;
    public GPUImageSaturationFilter saturation = new GPUImageSaturationFilter(0.5f);

    public GlitchFilters(Context context, Boolean bool) {
        this.aBoolean = true;
        this.glitchColorFilters = new GlitchColorFilters(context, 1);
        this.aBoolean = bool;
        build();
    }

    public void build() {
        this.group = new GPUImageFilterGroup();
        if (this.aBoolean.booleanValue()) {
            this.group.addFilter(this.glitchColorFilters);
            this.group.addFilter(this.glitchRshiftFilters);
            return;
        }
        this.group.addFilter(this.glitchRshiftFilters);
    }
}
