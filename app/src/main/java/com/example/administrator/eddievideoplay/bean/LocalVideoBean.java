package com.example.administrator.eddievideoplay.bean;

/**
 * Created by Eddie on 2017/6/22.
 */

public class LocalVideoBean {
    public String title;//视频名称
    public String duration;//视频时长
    public long size;//视频大小
    public String path;//视频的路径

    public LocalVideoBean(String title, String duration, long size, String path) {
        this.title = title;
        this.duration = duration;
        this.size = size;
        this.path = path;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
