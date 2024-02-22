package com.demo.neondevilsface.glitchfilter;

import android.opengl.GLES20;
import android.util.Log;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;


public class GlitchRshiftFilters extends GPUImageFilter {
    public static final String RGB_FRAGMENT_SHADER = "  varying highp vec2 textureCoordinate;\n  \n  uniform sampler2D inputImageTexture;\n  uniform highp float wave[80];  uniform highp int rgbChoose;\n  uniform highp float rShift;\n  uniform highp float intensity;\n  uniform highp float thickness;\n  \n  void main()\n  {\n       highp vec2 rgbcord=vec2(rShift,0.00);       highp vec2 wavecord = vec2(0.00,0.00);       for(int i=0;i<80;i+=4){           if(textureCoordinate.y>wave[i]/100.0 && textureCoordinate.y<(wave[i]+(wave[i+1]*thickness))/100.0){               rgbcord = vec2(rShift+wave[i+2]/100.0,0);               wavecord = vec2(wave[i+2]/100.0,0);               break;           }       }       highp vec2 rgbPos = textureCoordinate - rgbcord;       highp vec2 rgbPos2 = textureCoordinate + rgbcord;       highp vec2 wavePos = textureCoordinate - wavecord;       highp vec4 tc1 = texture2D(inputImageTexture, rgbPos);       highp vec4 tc2 = texture2D(inputImageTexture, rgbPos2);       highp vec4 tc3 = texture2D(inputImageTexture, wavePos);       if(rgbChoose==1)gl_FragColor = vec4(tc1.r, tc3.g, tc3.b, 1.0);       else if(rgbChoose==2)gl_FragColor = vec4(tc3.r, tc1.g, tc3.b, 1.0);       else if(rgbChoose==3)gl_FragColor = vec4(tc3.r, tc3.g, tc1.b, 1.0);       else if(rgbChoose==4)gl_FragColor = vec4(tc3.r, tc1.g, tc2.b, 1.0);       else if(rgbChoose==5)gl_FragColor = vec4(tc1.r, tc3.g, tc2.b, 1.0);       else if(rgbChoose==6)gl_FragColor = vec4(tc1.r, tc2.g, tc3.b, 1.0);       else gl_FragColor = vec4(tc1.r, tc2.g, tc2.b, 1.0);  }\n";
    String filterType;
    private float intensity;
    private int intensityLoc;
    private boolean mIsInitialized;
    private float rShift;
    private int rShiftLoc;
    private int rgbChoose;
    private int rgbChooseLoc;
    private float thickness;
    private int thicknessLoc;
    private int waveLoc;
    private int waveUsed;
    private List<float[]> waves;

    public GlitchRshiftFilters(String str) {
        super(NO_FILTER_VERTEX_SHADER, RGB_FRAGMENT_SHADER);
        this.filterType = "r";
        this.intensity = 0.5f;
        this.mIsInitialized = false;
        this.rShift = 0.015f;
        this.thickness = 5.0f;
        this.waveUsed = 0;
        createWave();
        this.filterType = str;
    }

    @Override 
    public void onInit() {
        super.onInit();
        this.rgbChooseLoc = GLES20.glGetUniformLocation(getProgram(), "rgbChoose");
        this.rShiftLoc = GLES20.glGetUniformLocation(getProgram(), "rShift");
        this.intensityLoc = GLES20.glGetUniformLocation(getProgram(), "intensity");
        this.thicknessLoc = GLES20.glGetUniformLocation(getProgram(), "thickness");
        this.waveLoc = GLES20.glGetUniformLocation(getProgram(), "wave");
        this.mIsInitialized = true;
        if (this.filterType.equals("r")) {
            setInteger(this.rgbChooseLoc, 1);
        } else if (this.filterType.equals("g")) {
            setInteger(this.rgbChooseLoc, 2);
        } else if (this.filterType.equals("b")) {
            setInteger(this.rgbChooseLoc, 3);
        }
        setFloat(this.rShiftLoc, this.rShift);
        setFloat(this.intensityLoc, this.intensity);
        setFloat(this.thicknessLoc, this.thickness);
        setFloatArray(this.waveLoc, this.waves.get(50));
    }

    public void changeParam(String str, int i, float f, float f2, float f3) {
        this.filterType = str;
        this.waveUsed = i;
        this.rShift = f;
        this.intensity = f2;
        this.thickness = f3;
    }

    @Override 
    public void onDraw(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        float[] fArr = this.waves.get(this.waveUsed);
        float[] fArr2 = new float[80];
        for (int i2 = 0; i2 < fArr.length; i2 += 4) {
            int i3 = i2 + 3;
            if (fArr[i3] < this.intensity) {
                fArr2[i2] = fArr[i2];
                int i4 = i2 + 1;
                fArr2[i4] = fArr[i4];
                int i5 = i2 + 2;
                fArr2[i5] = fArr[i5];
                fArr2[i3] = fArr[i3];
            }
        }
        setFloatArray(this.waveLoc, fArr2);
        setFloat(this.rShiftLoc, this.rShift);
        setFloat(this.intensityLoc, this.intensity);
        setFloat(this.thicknessLoc, this.thickness);
        if (this.filterType.equals("r")) {
            setInteger(this.rgbChooseLoc, 1);
        } else if (this.filterType.equals("g")) {
            setInteger(this.rgbChooseLoc, 2);
        } else if (this.filterType.equals("b")) {
            setInteger(this.rgbChooseLoc, 3);
        } else if (this.filterType.equals("rr")) {
            setInteger(this.rgbChooseLoc, 4);
        } else if (this.filterType.equals("gg")) {
            setInteger(this.rgbChooseLoc, 5);
        } else if (this.filterType.equals("bb")) {
            setInteger(this.rgbChooseLoc, 6);
        } else {
            Log.e("filterType__", "onDraw: " + this.filterType);
            setInteger(this.rgbChooseLoc, 1);
        }
        super.onDraw(i, floatBuffer, floatBuffer2);
    }

    public void createWave() {
        double[][][] dArr;
        this.waves = new ArrayList();
        for (double[][] dArr2 : Seed.seedArray) {
            float[] fArr = new float[80];
            for (int i = 0; i < 20; i++) {
                for (int i2 = 0; i2 < 4; i2++) {
                    if (i >= dArr2.length) {
                        fArr[(i * 4) + i2] = 0.0f;
                    } else {
                        fArr[(i * 4) + i2] = (float) dArr2[i][i2];
                    }
                }
            }
            this.waves.add(fArr);
        }
    }
}
