package com.demo.neondevilsface.glitchfilter;


public class GlitchMaskFilters extends GPUImageTwoInputFilter {
    public static final String OVERLAY_BLEND_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n varying highp vec2 textureCoordinate2;\n\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n \n void main()\n {\n     mediump vec4 base = texture2D(inputImageTexture, textureCoordinate);\n     mediump vec4 mask = texture2D(inputImageTexture2, textureCoordinate2);\n     \n     mediump float ra;\n     mediump float ga;\n     mediump float ba;\n     if(mask.r<0.1){ra=mask.r;}else{ra=base.r;}     if(mask.g<0.1){ga=mask.g;}else{ga=base.g;}     if(mask.b<0.1){ba=mask.b;}else{ba=base.b;}       gl_FragColor = vec4(ra,ga,ba,1.0);\n }";

    public GlitchMaskFilters() {
        super(OVERLAY_BLEND_FRAGMENT_SHADER);
    }
}
