package com.demo.neondevilsface.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


public class ComponentInfo implements Parcelable {
    public static final Creator<ComponentInfo> CREATOR = new Creator<ComponentInfo>() { 

        @Override 
        public ComponentInfo createFromParcel(Parcel parcel) {
            return new ComponentInfo(parcel);
        }


        @Override 
        public ComponentInfo[] newArray(int i) {
            return new ComponentInfo[i];
        }
    };
    private Bitmap BITMAP;
    private String COLORTYPE;
    private int COMP_ID;
    private String FIELD_FOUR;
    int FIELD_ONE;
    private String FIELD_THREE;
    private String FIELD_TWO;
    private int HEIGHT;
    private int ORDER;
    private float POS_X;
    private float POS_Y;
    private String RES_ID;
    private Uri RES_URI;
    private float ROTATION;
    private int STC_COLOR;
    private int STC_HUE;
    private int STC_OPACITY;
    private String STKR_PATH;
    int ScaleProg;
    private int TEMPLATE_ID;
    private String TYPE;
    private int WIDTH;
    int XRotateProg;
    int YRotateProg;
    private float Y_ROTATION;
    int ZRotateProg;

    @Override 
    public int describeContents() {
        return 0;
    }

    public ComponentInfo() {
        this.FIELD_FOUR = "";
        this.FIELD_ONE = 0;
        this.FIELD_THREE = "";
        this.FIELD_TWO = "";
        this.STKR_PATH = "";
        this.TYPE = "";
    }

    public ComponentInfo(int i, float f, float f2, int i2, int i3, float f3, float f4, String str, String str2, int i4, int i5, int i6, int i7, int i8, int i9, int i10, String str3, String str4, int i11, int i12, String str5, String str6, String str7, Uri uri, Bitmap bitmap) {
        this.FIELD_FOUR = "";
        this.FIELD_ONE = 0;
        this.FIELD_THREE = "";
        this.FIELD_TWO = "";
        this.STKR_PATH = "";
        this.TYPE = "";
        this.TEMPLATE_ID = i;
        this.POS_X = f;
        this.POS_Y = f2;
        this.WIDTH = i2;
        this.HEIGHT = i3;
        this.ROTATION = f3;
        this.Y_ROTATION = f4;
        this.RES_ID = str;
        this.RES_URI = uri;
        this.BITMAP = bitmap;
        this.TYPE = str2;
        this.ORDER = i4;
        this.STC_COLOR = i5;
        this.COLORTYPE = str4;
        this.STC_OPACITY = i6;
        this.XRotateProg = i7;
        this.YRotateProg = i8;
        this.ZRotateProg = i9;
        this.ScaleProg = i10;
        this.STKR_PATH = str3;
        this.STC_HUE = i11;
        this.FIELD_ONE = i12;
        this.FIELD_TWO = str5;
        this.FIELD_THREE = str6;
        this.FIELD_FOUR = str7;
    }

    public ComponentInfo(ComponentInfo componentInfo) {
        this.FIELD_FOUR = "";
        this.FIELD_ONE = 0;
        this.FIELD_THREE = "";
        this.FIELD_TWO = "";
        this.STKR_PATH = "";
        this.TYPE = "";
        if (componentInfo != null) {
            this.COMP_ID = componentInfo.getCOMP_ID();
            this.TEMPLATE_ID = componentInfo.getTEMPLATE_ID();
            this.POS_X = componentInfo.getPOS_X();
            this.POS_Y = componentInfo.getPOS_Y();
            this.WIDTH = componentInfo.getWIDTH();
            this.HEIGHT = componentInfo.getHEIGHT();
            this.ROTATION = componentInfo.getROTATION();
            this.Y_ROTATION = componentInfo.getY_ROTATION();
            this.RES_ID = componentInfo.getRES_ID();
            this.RES_URI = componentInfo.getRES_URI();
            this.BITMAP = componentInfo.getBITMAP();
            this.STC_COLOR = componentInfo.getSTC_COLOR();
            this.TYPE = componentInfo.getTYPE();
            this.COLORTYPE = componentInfo.getCOLORTYPE();
            this.STC_OPACITY = componentInfo.getSTC_OPACITY();
            this.XRotateProg = componentInfo.getXRotateProg();
            this.YRotateProg = componentInfo.getYRotateProg();
            this.ZRotateProg = componentInfo.getZRotateProg();
            this.ScaleProg = componentInfo.getScaleProg();
            this.STC_HUE = componentInfo.getSTC_HUE();
            this.ORDER = componentInfo.getORDER();
            this.FIELD_ONE = componentInfo.getFIELD_ONE();
            this.STKR_PATH = componentInfo.getSTKR_PATH();
            this.FIELD_TWO = componentInfo.getFIELD_TWO();
            this.FIELD_THREE = componentInfo.getFIELD_THREE();
            this.FIELD_FOUR = componentInfo.getFIELD_FOUR();
        }
    }

    protected ComponentInfo(Parcel parcel) {
        this.FIELD_FOUR = "";
        this.FIELD_ONE = 0;
        this.FIELD_THREE = "";
        this.FIELD_TWO = "";
        this.STKR_PATH = "";
        this.TYPE = "";
        this.COMP_ID = parcel.readInt();
        this.TEMPLATE_ID = parcel.readInt();
        this.POS_X = parcel.readFloat();
        this.POS_Y = parcel.readFloat();
        this.WIDTH = parcel.readInt();
        this.HEIGHT = parcel.readInt();
        this.ROTATION = parcel.readFloat();
        this.Y_ROTATION = parcel.readFloat();
        this.RES_ID = parcel.readString();
        this.RES_URI = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.BITMAP = (Bitmap) parcel.readParcelable(Bitmap.class.getClassLoader());
        this.STC_COLOR = parcel.readInt();
        this.TYPE = parcel.readString();
        this.COLORTYPE = parcel.readString();
        this.STC_OPACITY = parcel.readInt();
        this.XRotateProg = parcel.readInt();
        this.YRotateProg = parcel.readInt();
        this.ZRotateProg = parcel.readInt();
        this.ScaleProg = parcel.readInt();
        this.STC_HUE = parcel.readInt();
        this.ORDER = parcel.readInt();
        this.FIELD_ONE = parcel.readInt();
        this.STKR_PATH = parcel.readString();
        this.FIELD_TWO = parcel.readString();
        this.FIELD_THREE = parcel.readString();
        this.FIELD_FOUR = parcel.readString();
    }

    @Override 
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.COMP_ID);
        parcel.writeInt(this.TEMPLATE_ID);
        parcel.writeFloat(this.POS_X);
        parcel.writeFloat(this.POS_Y);
        parcel.writeInt(this.WIDTH);
        parcel.writeInt(this.HEIGHT);
        parcel.writeFloat(this.ROTATION);
        parcel.writeFloat(this.Y_ROTATION);
        parcel.writeString(this.RES_ID);
        parcel.writeParcelable(this.RES_URI, i);
        parcel.writeParcelable(this.BITMAP, i);
        parcel.writeInt(this.STC_COLOR);
        parcel.writeString(this.TYPE);
        parcel.writeString(this.COLORTYPE);
        parcel.writeInt(this.STC_OPACITY);
        parcel.writeInt(this.XRotateProg);
        parcel.writeInt(this.YRotateProg);
        parcel.writeInt(this.ZRotateProg);
        parcel.writeInt(this.ScaleProg);
        parcel.writeInt(this.STC_HUE);
        parcel.writeInt(this.ORDER);
        parcel.writeInt(this.FIELD_ONE);
        parcel.writeString(this.STKR_PATH);
        parcel.writeString(this.FIELD_TWO);
        parcel.writeString(this.FIELD_THREE);
        parcel.writeString(this.FIELD_FOUR);
    }

    public int getCOMP_ID() {
        return this.COMP_ID;
    }

    public void setCOMP_ID(int i) {
        this.COMP_ID = i;
    }

    public int getTEMPLATE_ID() {
        return this.TEMPLATE_ID;
    }

    public void setTEMPLATE_ID(int i) {
        this.TEMPLATE_ID = i;
    }

    public float getPOS_X() {
        return this.POS_X;
    }

    public void setPOS_X(float f) {
        this.POS_X = f;
    }

    public float getPOS_Y() {
        return this.POS_Y;
    }

    public void setPOS_Y(float f) {
        this.POS_Y = f;
    }

    public String getRES_ID() {
        return this.RES_ID;
    }

    public void setRES_ID(String str) {
        this.RES_ID = str;
    }

    public String getTYPE() {
        return this.TYPE;
    }

    public void setTYPE(String str) {
        this.TYPE = str;
    }

    public int getORDER() {
        return this.ORDER;
    }

    public void setORDER(int i) {
        this.ORDER = i;
    }

    public float getROTATION() {
        return this.ROTATION;
    }

    public void setROTATION(float f) {
        this.ROTATION = f;
    }

    public int getWIDTH() {
        return this.WIDTH;
    }

    public void setWIDTH(int i) {
        this.WIDTH = i;
    }

    public int getHEIGHT() {
        return this.HEIGHT;
    }

    public void setHEIGHT(int i) {
        this.HEIGHT = i;
    }

    public float getY_ROTATION() {
        return this.Y_ROTATION;
    }

    public void setY_ROTATION(float f) {
        this.Y_ROTATION = f;
    }

    public Uri getRES_URI() {
        return this.RES_URI;
    }

    public void setRES_URI(Uri uri) {
        this.RES_URI = uri;
    }

    public Bitmap getBITMAP() {
        return this.BITMAP;
    }

    public void setBITMAP(Bitmap bitmap) {
        this.BITMAP = bitmap;
    }

    public int getSTC_COLOR() {
        return this.STC_COLOR;
    }

    public void setSTC_COLOR(int i) {
        this.STC_COLOR = i;
    }

    public String getCOLORTYPE() {
        return this.COLORTYPE;
    }

    public void setCOLORTYPE(String str) {
        this.COLORTYPE = str;
    }

    public int getSTC_OPACITY() {
        return this.STC_OPACITY;
    }

    public void setSTC_OPACITY(int i) {
        this.STC_OPACITY = i;
    }

    public int getXRotateProg() {
        return this.XRotateProg;
    }

    public void setXRotateProg(int i) {
        this.XRotateProg = i;
    }

    public int getYRotateProg() {
        return this.YRotateProg;
    }

    public void setYRotateProg(int i) {
        this.YRotateProg = i;
    }

    public int getZRotateProg() {
        return this.ZRotateProg;
    }

    public void setZRotateProg(int i) {
        this.ZRotateProg = i;
    }

    public int getScaleProg() {
        return this.ScaleProg;
    }

    public void setScaleProg(int i) {
        this.ScaleProg = i;
    }

    public int getFIELD_ONE() {
        return this.FIELD_ONE;
    }

    public void setFIELD_ONE(int i) {
        this.FIELD_ONE = i;
    }

    public String getFIELD_TWO() {
        return this.FIELD_TWO;
    }

    public void setFIELD_TWO(String str) {
        this.FIELD_TWO = str;
    }

    public String getFIELD_THREE() {
        return this.FIELD_THREE;
    }

    public void setFIELD_THREE(String str) {
        this.FIELD_THREE = str;
    }

    public String getFIELD_FOUR() {
        return this.FIELD_FOUR;
    }

    public void setFIELD_FOUR(String str) {
        this.FIELD_FOUR = str;
    }

    public String getSTKR_PATH() {
        return this.STKR_PATH;
    }

    public void setSTKR_PATH(String str) {
        this.STKR_PATH = str;
    }

    public int getSTC_HUE() {
        return this.STC_HUE;
    }

    public void setSTC_HUE(int i) {
        this.STC_HUE = i;
    }
}
