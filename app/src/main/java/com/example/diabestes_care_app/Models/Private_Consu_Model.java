package com.example.diabestes_care_app.Models;

public class Private_Consu_Model {
    String patientImage, patientName, consuTitle, consuSubject, doctorAnswer, doctor_name, PushKey, doctorImage,patientToken;

    public Private_Consu_Model(String patientImage, String patientName, String consuTitle, String consuSubject, String doctorAnswer, String doctor_name, String pushKey, String doctorImage, String patientToken) {
        this.patientImage = patientImage;
        this.patientName = patientName;
        this.consuTitle = consuTitle;
        this.consuSubject = consuSubject;
        this.doctorAnswer = doctorAnswer;
        this.doctor_name = doctor_name;
        PushKey = pushKey;
        this.doctorImage = doctorImage;
        this.patientToken = patientToken;
    }

    public Private_Consu_Model() {
    }

    public String getPatientImage() {
        return patientImage;
    }

    public void setPatientImage(String patientImage) {
        this.patientImage = patientImage;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getConsuTitle() {
        return consuTitle;
    }

    public void setConsuTitle(String consuTitle) {
        this.consuTitle = consuTitle;
    }

    public String getConsuSubject() {
        return consuSubject;
    }

    public void setConsuSubject(String consuSubject) {
        this.consuSubject = consuSubject;
    }

    public String getDoctorAnswer() {
        return doctorAnswer;
    }

    public void setDoctorAnswer(String doctorAnswer) {
        this.doctorAnswer = doctorAnswer;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getPushKey() {
        return PushKey;
    }

    public void setPushKey(String pushKey) {
        PushKey = pushKey;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }

    public String getPatientToken() {
        return patientToken;
    }

    public void setPatientToken(String patientToken) {
        this.patientToken = patientToken;
    }
}
