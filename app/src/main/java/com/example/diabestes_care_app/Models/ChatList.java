package com.example.diabestes_care_app.Models;

public class ChatList {
    private String username, name, message, date, time;

    public ChatList(String username, String name, String message, String date, String time) {
        this.username = username;
        this.name = name;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public ChatList() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
