package com.example.diabestes_care_app.Base_Activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diabestes_care_app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Basic_Activity extends AppCompatActivity {
    static boolean password_is_visible;

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
        return password.length() > 8;
    }

    public boolean validCoPassword(String password, String CoPassword) {
        return password.equals(CoPassword);
    }

    public boolean validIsEmpty(String edit1, String edit2, String edit3, String edit4, String edit5, String edit6) {
        return edit1.isEmpty() || edit2.isEmpty() || edit3.isEmpty() || edit4.isEmpty() || edit5.isEmpty() || edit6.isEmpty();
    }

    public static void showPass(EditText password) {
        int selection = password.getSelectionEnd();
        if (password_is_visible) {
            //set drawable image here
            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_password_hide, 0);
            //for hide password
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            password_is_visible = false;
        } else {
            //set drawable image here
            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_password_show, 0);
            //for hide password
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            password_is_visible = true;
        }
        password.setSelection(selection);
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void OpenFileChooser(int PICK_IMAGE_REQUEST) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void updateLabel(EditText mData, Calendar myCalendar, String myFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.UK);
        mData.setText(dateFormat.format(myCalendar.getTime()));
    }

}
