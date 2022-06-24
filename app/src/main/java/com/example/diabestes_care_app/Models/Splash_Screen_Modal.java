package com.example.diabestes_care_app.Models;

public class Splash_Screen_Modal {
    String Title, Description   ;
    int ScreenImg;

    public Splash_Screen_Modal(String title, String description,  int screenImg) {
        Title = title;
        Description = description;
        ScreenImg = screenImg;

    }

    public Splash_Screen_Modal() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getScreenImg() {
        return ScreenImg;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }



}
