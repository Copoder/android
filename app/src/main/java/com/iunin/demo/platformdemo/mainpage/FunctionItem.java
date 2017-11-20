package com.iunin.demo.platformdemo.mainpage;

public class FunctionItem {
    public String title;
    public int image;
    public Class clazz;

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public FunctionItem(String title, int image, Class clazz){
        this.title = title;
        this.image = image;
        this.clazz = clazz;
    }

    public FunctionItem(String title,int image){
        this.title = title;
        this.image = image;
    }
}
