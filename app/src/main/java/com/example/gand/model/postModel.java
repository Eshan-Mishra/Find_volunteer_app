package com.example.gand.model;

public class postModel {
    String uid,username,body,post_img,location;


    public postModel() {}


    public postModel(String uid, String username, String body, String post_img, String location) {
        this.uid = uid;
        this.username = username;
        this.body = body;
        this.post_img = post_img;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPost_img() {
        return post_img;
    }

    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }
}
