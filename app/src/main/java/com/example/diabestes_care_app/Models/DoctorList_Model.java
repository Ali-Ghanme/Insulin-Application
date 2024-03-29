package com.example.diabestes_care_app.Models;

public class DoctorList_Model {
    String name;
    String username;
    String imageUrl;
    String token;

    public DoctorList_Model(String name, String username, String imageUrl, String token) {
        this.name = name;
        this.username = username;
        this.imageUrl = imageUrl;
        this.token = token;
    }


    public DoctorList_Model() {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
