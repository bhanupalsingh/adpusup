package com.example.adpushup.bean;

import java.util.concurrent.atomic.AtomicInteger;

public class Website {
    private int id;
    private String url;

    

    public Website(int id , String url) {
    	this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
