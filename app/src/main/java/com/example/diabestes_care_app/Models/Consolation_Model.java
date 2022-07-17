package com.example.diabestes_care_app.Models;

public class Consolation_Model {
    String title, question, doctorName, doctorUsername, answer, imageUrl, MSGKey;

    public Consolation_Model(String title, String question, String doctorName, String doctorUsername, String answer, String imageUrl, String MSGKey) {
        this.title = title;
        this.question = question;
        this.doctorName = doctorName;
        this.doctorUsername = doctorUsername;
        this.answer = answer;
        this.imageUrl = imageUrl;
        this.MSGKey = MSGKey;
    }

    public Consolation_Model() {
    }

    public String getMSGKey() {
        return MSGKey;
    }

    public void setMSGKey(String MSGKey) {
        this.MSGKey = MSGKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorUsername() {
        return doctorUsername;
    }

    public void setDoctorUsername(String doctorUsername) {
        this.doctorUsername = doctorUsername;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
