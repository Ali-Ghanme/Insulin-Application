package com.example.diabestes_care_app.Models;

public class Doctor_List_Modal_P {
    int Doctor_Image;
    String Doctor_Name;
    String Doctor_Bio;
    String Doctor_Stars;
    String Doctor_like;


    public Doctor_List_Modal_P(int doctor_Image, String doctor_Name, String doctor_Bio, String doctor_Stars) {
        Doctor_Image = doctor_Image;
        Doctor_Name = doctor_Name;
        Doctor_Bio = doctor_Bio;
        Doctor_Stars = doctor_Stars;
    }

    public int getDoctor_Image() {
        return Doctor_Image;
    }

    public void setDoctor_Image(int doctor_Image) {
        Doctor_Image = doctor_Image;
    }

    public String getDoctor_Name() {
        return Doctor_Name;
    }

    public void setDoctor_Name(String doctor_Name) {
        Doctor_Name = doctor_Name;
    }

    public String getDoctor_Bio() {
        return Doctor_Bio;
    }

    public void setDoctor_Bio(String doctor_Bio) {
        Doctor_Bio = doctor_Bio;
    }

    public String getDoctor_Stars() {
        return Doctor_Stars;
    }

    public void setDoctor_Stars(String doctor_Stars) {
        Doctor_Stars = doctor_Stars;
    }

    public String getDoctor_like() {
        return Doctor_like;
    }

    public void setDoctor_like(String doctor_like) {
        Doctor_like = doctor_like;
    }

}
