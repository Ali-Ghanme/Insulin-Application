// ملف: PatientInfo.java
package com.example.diabestes_care_app.data.model.sing_up_p;

public class PatientInfo {
    public String phone;
    public String email;
    public String password;
    public String confirmPassword;
    public String city;

    public PatientInfo(String phone, String email, String password, String confirmPassword, String city) {
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.city = city;
    }
}
