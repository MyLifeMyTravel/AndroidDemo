package com.littlejie.ui.image.entity;

import java.io.Serializable;

/**
 * Created by Lion on 2016/6/23.
 */
public class SelectImageInfo implements Serializable {

    //选中Image的坐标
    private float x;
    private float y;
    //选择Image的宽高
    private int width;
    private int height;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
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

    @Override
    public String toString() {
        return "SelectImageInfo{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
