package com.example.diabestes_care_app.Models;

public class Reports_Model {
    String TimeSugar, Time, BloodSugar, StatusSugar;

    public Reports_Model(String timeSugar, String time, String bloodSugar, String statusSugar) {
        TimeSugar = timeSugar;
        Time = time;
        BloodSugar = bloodSugar;
        StatusSugar = statusSugar;
    }

    public Reports_Model() {
    }

    public String getTimeSugar() {
        return TimeSugar;
    }

    public void setTimeSugar(String timeSugar) {
        TimeSugar = timeSugar;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getBloodSugar() {
        return BloodSugar;
    }

    public void setBloodSugar(String bloodSugar) {
        BloodSugar = bloodSugar;
    }

    public String getStatusSugar() {
        return StatusSugar;
    }

    public void setStatusSugar(String statusSugar) {
        StatusSugar = statusSugar;
    }
}

