package com.example.diabestes_care_app.Models;

public class Notification_Model {
    String username, time, image;

    public Notification_Model(String username, String time, String image) {
        this.username = username;
        this.time = time;
        this.image = image;
    }

    public Notification_Model() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
