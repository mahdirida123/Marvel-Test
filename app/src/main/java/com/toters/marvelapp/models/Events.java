package com.toters.marvelapp.models;

import java.io.Serializable;

public class Events implements Serializable {
    private int id;
    private String title;
    private String description;
    private String picPath;

    public Events(int id, String title, String description, String picPath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.picPath = picPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public String toString() {
        return "Events{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", picPath='" + picPath + '\'' +
                '}';
    }
}
