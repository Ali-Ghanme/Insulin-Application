package com.example.diabestes_care_app.Models;

public class SelfCare_Model {
    int Content;
    int Cont_Image;
    String title;


    public SelfCare_Model(int content, int cont_Image, String title) {
        Content = content;
        Cont_Image = cont_Image;
        this.title = title;
    }

    public SelfCare_Model() {
    }

    public int getContent() {
        return Content;
    }

    public void setContent(int content) {
        Content = content;
    }

    public int getCont_Image() {
        return Cont_Image;
    }

    public void setCont_Image(int cont_Image) {
        Cont_Image = cont_Image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
