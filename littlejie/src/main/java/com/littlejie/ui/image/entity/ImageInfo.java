package com.littlejie.ui.image.entity;

import java.io.Serializable;

/**
 * Created by Lion on 2016/6/23.
 */
public class ImageInfo implements Serializable {

    private String url;
    private int width = 1;
    private int height = 1;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
