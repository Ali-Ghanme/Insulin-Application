package com.example.diabestes_care_app.Models;

public class Reports_Model {
    String data1,data2;

    public Reports_Model(String data1, String data2) {
        this.data1 = data1;
        this.data2 = data2;
    }

    public Reports_Model() {
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }
}
