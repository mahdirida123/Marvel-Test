package com.toters.marvelapp.models;

import java.util.List;

public class Content {
    private String title;
    private List<Object> objectList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Object> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<Object> objectList) {
        this.objectList = objectList;
    }

    @Override
    public String toString() {
        return "Content{" +
                "title='" + title + '\'' +
                ", objectList=" + objectList +
                '}';
    }
}
