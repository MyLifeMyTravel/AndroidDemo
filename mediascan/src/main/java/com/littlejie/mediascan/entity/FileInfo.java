package com.littlejie.mediascan.entity;

import com.littlejie.mediascan.TimeUtil;

/**
 * Created by littlejie on 2016/12/28.
 */

public class FileInfo {

    private String path;
    private String name;
    private long modify;
    private int parent;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getModify() {
        return modify;
    }

    public void setModify(long modify) {
        this.modify = modify;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "名称：" + name
                + "\n路径：" + path
                + "\n修改时间：" + TimeUtil.parse2TimeDetail(modify * 1000)
                + "\n是否为目录：" + (parent == 0 ? "是" : "否");
    }
}
