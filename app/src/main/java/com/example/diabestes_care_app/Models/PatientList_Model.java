package com.example.diabestes_care_app.Models;

public class PatientList_Model {
    String name, username, imageUrl, token, requestKey, patientType;

    public PatientList_Model(String name, String username, String imageUrl, String token, String requestKey, String patientType) {
        this.name = name;
        this.username = username;
        this.imageUrl = imageUrl;
        this.token = token;
        this.requestKey = requestKey;
        this.patientType = patientType;
    }


    public PatientList_Model() {
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

    public String getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }
}
