package com.demo.neondevilsface.glitchfilter;


public class GPUImageNativeLibrary {
    public static native void YUVtoARBG(byte[] bArr, int i, int i2, int[] iArr);

    public static native void YUVtoRBGA(byte[] bArr, int i, int i2, int[] iArr);

    static {
        System.loadLibrary("gpuimage-library");
    }
}
