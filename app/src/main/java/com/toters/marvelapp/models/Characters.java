package com.toters.marvelapp.models;

import java.io.Serializable;
import java.util.List;

public class Characters implements Serializable {

    private int id;
    private String title;
    private String description;
    private String picPath;
    private String year;
    private String rate;
    private List<Content> objectList;

    public Characters() {}

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Content> getContentList() {
        return objectList;
    }

    public void setContentList(List<Content> objectList) {
        this.objectList = objectList;
    }

    @Override
    public String toString() {
        return "Characters{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", description='" + description + '\'' +
                ", picPath='" + picPath + '\'' +
                ", year='" + year + '\'' +
                ", objectList=" + objectList +
                '}';
    }
}
