package com.example.diabestes_care_app.Models;

public class ChatList_Model_d {
    private String username_d, name_d, message_d, date_d, time_d;

    public ChatList_Model_d(String username_d, String name_d, String message_d, String date_d, String time_d) {
        this.username_d = username_d;
        this.name_d = name_d;
        this.message_d = message_d;
        this.date_d = date_d;
        this.time_d = time_d;
    }

    public String getUsername_d() {
        return username_d;
    }

    public void setUsername_d(String username_d) {
        this.username_d = username_d;
    }

    public String getName_d() {
        return name_d;
    }

    public void setName_d(String name_d) {
        this.name_d = name_d;
    }

    public String getMessage_d() {
        return message_d;
    }

    public void setMessage_d(String message_d) {
        this.message_d = message_d;
    }

    public String getDate_d() {
        return date_d;
    }

    public void setDate_d(String date_d) {
        this.date_d = date_d;
    }

    public String getTime_d() {
        return time_d;
    }

    public void setTime_d(String time_d) {
        this.time_d = time_d;
    }
}
