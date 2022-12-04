package com.toters.marvelapp.models;

import java.io.Serializable;

public class Stories implements Serializable {
    private int id;
    private String title;
    private String description;
    private String picPath;
    private String type;

    public Stories(int id, String title, String description, String type, String picPath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.picPath = picPath;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "Stories{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
