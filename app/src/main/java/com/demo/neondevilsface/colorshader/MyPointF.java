package com.demo.neondevilsface.colorshader;

import android.graphics.PointF;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class MyPointF extends PointF implements Serializable {
    public MyPointF() {
    }

    public MyPointF(float f, float f2) {
        super(f, f2);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeFloat(this.x);
        objectOutputStream.writeFloat(this.y);
    }

    private void readObject(ObjectInputStream objectInputStream) throws Exception, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.x = objectInputStream.readFloat();
        this.y = objectInputStream.readFloat();
    }
}
