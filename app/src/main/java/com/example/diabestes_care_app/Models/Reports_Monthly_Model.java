package com.example.diabestes_care_app.Models;

public class Reports_Monthly_Model {
    String result_creatinine, result_urea, result_uric, result_cholesterol, result_triglycerid,
            result_ldl, result_hdl, result_presser, result_bmi_height, result_bmi_weight;


    public Reports_Monthly_Model(String result_creatinine, String result_urea, String result_uric, String result_cholesterol, String result_triglycerid, String result_ldl, String result_hdl, String result_pressuer, String result_bmi_height, String result_bmi_weight) {
        this.result_creatinine = result_creatinine;
        this.result_urea = result_urea;
        this.result_uric = result_uric;
        this.result_cholesterol = result_cholesterol;
        this.result_triglycerid = result_triglycerid;
        this.result_ldl = result_ldl;
        this.result_hdl = result_hdl;
        this.result_presser = result_pressuer;
        this.result_bmi_height = result_bmi_height;
        this.result_bmi_weight = result_bmi_weight;
    }

    public Reports_Monthly_Model() {

    }


    public String getResult_creatinine() {
        return result_creatinine;
    }

    public void setResult_creatinine(String result_creatinine) {
        this.result_creatinine = result_creatinine;
    }

    public String getResult_urea() {
        return result_urea;
    }

    public void setResult_urea(String result_urea) {
        this.result_urea = result_urea;
    }

    public String getResult_uric() {
        return result_uric;
    }

    public void setResult_uric(String result_uric) {
        this.result_uric = result_uric;
    }

    public String getResult_cholesterol() {
        return result_cholesterol;
    }

    public void setResult_cholesterol(String result_cholesterol) {
        this.result_cholesterol = result_cholesterol;
    }

    public String getResult_triglycerid() {
        return result_triglycerid;
    }

    public void setResult_triglycerid(String result_triglycerid) {
        this.result_triglycerid = result_triglycerid;
    }

    public String getResult_ldl() {
        return result_ldl;
    }

    public void setResult_ldl(String result_ldl) {
        this.result_ldl = result_ldl;
    }

    public String getResult_hdl() {
        return result_hdl;
    }

    public void setResult_hdl(String result_hdl) {
        this.result_hdl = result_hdl;
    }

    public String getResult_presser() {
        return result_presser;
    }

    public void setResult_presser(String result_presser) {
        this.result_presser = result_presser;
    }

    public String getResult_bmi_height() {
        return result_bmi_height;
    }

    public void setResult_bmi_height(String result_bmi_height) {
        this.result_bmi_height = result_bmi_height;
    }

    public String getResult_bmi_weight() {
        return result_bmi_weight;
    }

    public void setResult_bmi_weight(String result_bmi_weight) {
        this.result_bmi_weight = result_bmi_weight;
    }
}

