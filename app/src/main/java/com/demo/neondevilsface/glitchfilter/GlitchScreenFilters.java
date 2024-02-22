package com.demo.neondevilsface.glitchfilter;

import android.opengl.GLES20;
import java.nio.FloatBuffer;


public class GlitchScreenFilters extends GPUImageTwoInputFilter {
    public static final String SCREEN_BLEND_FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n varying highp vec2 textureCoordinate2;\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n uniform highp float intensity;\n  \n  void main()\n  {\n     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     mediump vec4 textureColor2 = texture2D(inputImageTexture2, textureCoordinate2);\n     mediump vec4 whiteColor = vec4(1.0);\n     mediump vec4 screened = whiteColor - ((whiteColor - textureColor2) * (whiteColor - textureColor));\n     gl_FragColor = vec4(mix(textureColor.rgb, screened.rgb, intensity), 1.0);\n  }\n";
    public float intensity;
    private int intensityLoc;

    public GlitchScreenFilters() {
        super(SCREEN_BLEND_FRAGMENT_SHADER);
        this.intensity = 0.8f;
    }

    @Override 
    public void onInit() {
        super.onInit();
        int glGetUniformLocation = GLES20.glGetUniformLocation(getProgram(), "intensity");
        this.intensityLoc = glGetUniformLocation;
        setFloat(glGetUniformLocation, this.intensity);
    }

    public void changeParam(float f) {
        this.intensity = f;
    }

    @Override 
    public void onDraw(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        setFloat(this.intensityLoc, this.intensity);
        super.onDraw(i, floatBuffer, floatBuffer2);
    }
}
