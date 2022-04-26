package com.example.diabestes_care_app.Base_Activity;

import android.content.Intent;
import android.text.Layout;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Basic_Activity extends AppCompatActivity {

    public void fullscreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean validCellPhone(String number) {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }

    public boolean validUserName(String userName) {
        if (userName.startsWith("P")) {
            return true;
        } else return userName.length() <= 15;
    }

    public boolean validPassword(String password) {
        return password.length() < 8;
    }

    public boolean validCoPassword(String password, String CoPassword) {
        return password.equals(CoPassword);
    }

    public boolean validIsEmpty(String name, String username, String phone, String email, String password, String coPassword) {
        return name.isEmpty() || username.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()
                || coPassword.isEmpty();
    }
}
