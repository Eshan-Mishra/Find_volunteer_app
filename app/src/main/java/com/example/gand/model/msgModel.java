package com.example.gand.model;

public class msgModel {

    String message,sender_id;
    long timestamp;


    public msgModel(){

    }


    public msgModel(String message, String sender_id, long timestamp) {
        this.message = message;
        this.sender_id = sender_id;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
