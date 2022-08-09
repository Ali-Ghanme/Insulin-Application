package com.example.diabestes_care_app.Models;

public class Private_Consu_Model {
    String patientImage, patientName, consuTitle, consuSubject, doctorAnswer;

    public Private_Consu_Model(String patientImage, String patientName, String consuTitle, String consuSubject, String doctorAnswer) {
        this.patientImage = patientImage;
        this.patientName = patientName;
        this.consuTitle = consuTitle;
        this.consuSubject = consuSubject;
        this.doctorAnswer = doctorAnswer;
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
}
