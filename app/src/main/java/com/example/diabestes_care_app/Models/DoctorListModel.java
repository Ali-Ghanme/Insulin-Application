package com.example.diabestes_care_app.Models;

public class DoctorListModel {
    String name;
    String username;
    String imageUrl;
    String status;

    public DoctorListModel(String name, String username, String imageUrl  , String status) {
        this.name = name;
        this.username = username;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public DoctorListModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
