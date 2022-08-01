package com.example.diabestes_care_app.Models;

public class Reports_Model {
    String timesuger,time ,title  ;

    public Reports_Model(String timesuger, String time, String title ) {
        this.timesuger = timesuger;
        this.time = time;
        this.title = title;


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

    public Reports_Model() {

    }


}

