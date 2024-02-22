package com.demo.neondevilsface.glitchfilter;

import com.demo.neondevilsface.Utility.Constant;


public enum Rotation {
    NORMAL,
    ROTATION_90,
    ROTATION_180,
    ROTATION_270;

    
    
    static  class AnonymousClass1 {
        static final  int[] $SwitchMap$com$neonhrn$neonhorndevil$glitchfilter$Rotation;

        static {
            int[] iArr = new int[Rotation.values().length];
            $SwitchMap$com$neonhrn$neonhorndevil$glitchfilter$Rotation = iArr;
            try {
                iArr[Rotation.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$neonhrn$neonhorndevil$glitchfilter$Rotation[Rotation.ROTATION_90.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$neonhrn$neonhorndevil$glitchfilter$Rotation[Rotation.ROTATION_180.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$neonhrn$neonhorndevil$glitchfilter$Rotation[Rotation.ROTATION_270.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int asInt() {
        int i = AnonymousClass1.$SwitchMap$com$neonhrn$neonhorndevil$glitchfilter$Rotation[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        return 270;
                    }
                    throw new IllegalStateException("Unknown Rotation!");
                }
                return Constant.ORIENTATION_180;
            }
            return 90;
        }
        return 0;
    }

    public static Rotation fromInt(int i) {
        if (i == 0) {
            return NORMAL;
        }
        if (i == 90) {
            return ROTATION_90;
        }
        if (i == 180) {
            return ROTATION_180;
        }
        if (i == 270) {
            return ROTATION_270;
        }
        if (i == 360) {
            return NORMAL;
        }
        throw new IllegalStateException(i + " is an unknown rotation. Needs to be either 0, 90, 180 or 270!");
    }
}
