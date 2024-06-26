package com.demo.neondevilsface.fragment;

import android.opengl.GLES20;

import com.demo.neondevilsface.glitchfilter.GPUImageFilter;


public class GPUImageBrightnessFilter extends GPUImageFilter {
    public static final String BRIGHTNESS_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform lowp float brightness;\n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     gl_FragColor = vec4((textureColor.rgb + vec3(brightness)), textureColor.w);\n }";
    private float mBrightness;
    private int mBrightnessLocation;

    public GPUImageBrightnessFilter() {
        this(0.0f);
    }

    public GPUImageBrightnessFilter(float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, BRIGHTNESS_FRAGMENT_SHADER);
        this.mBrightness = f;
    }

    @Override 
    public void onInit() {
        super.onInit();
        this.mBrightnessLocation = GLES20.glGetUniformLocation(getProgram(), "brightness");
    }

    @Override 
    public void onInitialized() {
        super.onInitialized();
        setBrightness(this.mBrightness);
    }

    public void setBrightness(float f) {
        this.mBrightness = f;
        setFloat(this.mBrightnessLocation, f);
    }
}
