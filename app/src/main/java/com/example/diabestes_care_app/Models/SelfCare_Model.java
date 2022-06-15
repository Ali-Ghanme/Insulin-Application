package com.example.diabestes_care_app.Models;

public class SelfCare_Model {
    String Content;
    int Cont_Image;

    public SelfCare_Model(String content, int cont_Image) {
        Content = content;
        Cont_Image = cont_Image;
    }

    public SelfCare_Model() {
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getCont_Image() {
        return Cont_Image;
    }

    public void setCont_Image(int cont_Image) {
        Cont_Image = cont_Image;
    }
}
