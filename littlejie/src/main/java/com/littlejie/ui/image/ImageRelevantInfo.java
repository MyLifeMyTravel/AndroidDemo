package com.littlejie.ui.image;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lion on 2016/6/28.
 */
public class ImageRelevantInfo implements Serializable {

    private int index;
    private int x;
    private int y;
    //防止出现除0
    private int width = 1;
    private int height = 1;
    private int numColums = 3;
    private List<String> lstUri;

    public ImageRelevantInfo(int index, int x, int y, int width, int height, int numColums, List<String> lstUri) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.numColums = numColums;
        this.lstUri = lstUri;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public int getNumColums() {
        return numColums;
    }

    public void setNumColums(int numColums) {
        this.numColums = numColums;
    }

    public List<String> getLstUri() {
        return lstUri;
    }

    public void setLstUri(List<String> lstUri) {
        this.lstUri = lstUri;
    }

    @Override
    public String toString() {
        return "ImageRelevantInfo{" +
                "index=" + index +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", numColums=" + numColums +
                ", lstUri=" + lstUri +
                '}';
    }
}
