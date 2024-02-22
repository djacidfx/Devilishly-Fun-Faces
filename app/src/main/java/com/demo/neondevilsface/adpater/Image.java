package com.demo.neondevilsface.adpater;

import java.util.Date;


public class Image {
    public Date date;
    String id;
    private boolean isSelected;
    String name;
    String path;

    public boolean getSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    public Image() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public Image(String str, boolean z, Date date) {
        this.path = str;
        this.isSelected = z;
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }
}
