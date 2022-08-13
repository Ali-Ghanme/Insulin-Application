package com.example.diabestes_care_app.Models;

import java.io.Serializable;

public class Follow_Model implements Serializable {
    String name, type, health_status, imageUrl, username;

    public Follow_Model(String name, String type, String health_status, String imageUrl, String username) {
        this.name = name;
        this.type = type;
        this.health_status = health_status;
        this.imageUrl = imageUrl;
        this.username = username;
    }

    public Follow_Model() {
    }

    public String getUsername() {
        return username;
    }

    public String setUsername(String username) {
        this.username = username;
        return username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHealth_status() {
        return health_status;
    }

    public void setHealth_status(String health_status) {
        this.health_status = health_status;
    }
}
