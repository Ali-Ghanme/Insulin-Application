package com.example.diabestes_care_app.Models;

public class Reports_Model {
    String timesuger, time, title, status_Suger;

    public Reports_Model(String timesuger, String time, String title, String status_Suger) {
        this.timesuger = timesuger;
        this.time = time;
        this.title = title;
        this.status_Suger = status_Suger;
    }

    public Reports_Model() {

    }

    public String getTimesuger() {
        return timesuger;
    }

    public void setTimesuger(String timesuger) {
        this.timesuger = timesuger;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus_Suger() {
        return status_Suger;
    }

    public void setStatus_Suger(String status_Suger) {
        this.status_Suger = status_Suger;
    }
}

