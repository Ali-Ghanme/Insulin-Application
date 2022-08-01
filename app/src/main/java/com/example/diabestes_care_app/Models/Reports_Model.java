package com.example.diabestes_care_app.Models;

public class Reports_Model {
    String time_sugar, time, title, status_sugar;

    public Reports_Model(String time_sugar, String time, String title, String status_sugar) {
        this.time_sugar = time_sugar;
        this.time = time;
        this.title = title;

    }

    public Reports_Model() {

    }

    public String get_status_sugar() {
        return status_sugar;
    }

    public void set_status_sugar(String status_sugar) {
        this.status_sugar = status_sugar;
    }

    public String get_time_sugar() {
        return time_sugar;
    }

    public void set_time_sugar(String time_sugar) {
        this.time_sugar = time_sugar;
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

}

