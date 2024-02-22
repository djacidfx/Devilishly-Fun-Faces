package com.demo.neondevilsface.glitchfilter;


public class GPUImageEmbossFilter extends GPUImage3x3ConvolutionFilter {
    private float intensity;

    public GPUImageEmbossFilter() {
        this(1.0f);
    }

    public GPUImageEmbossFilter(float f) {
        this.intensity = f;
    }

    @Override 
    public void onInit() {
        super.onInit();
    }

    @Override 
    public void onInitialized() {
        super.onInitialized();
        setIntensity(this.intensity);
    }

    public void setIntensity(float f) {
        this.intensity = f;
        float f2 = -f;
        setConvolutionKernel(new float[]{(-2.0f) * f, f2, 0.0f, f2, 1.0f, f, 0.0f, f, f * 2.0f});
    }

    public float getIntensity() {
        return this.intensity;
    }
}
