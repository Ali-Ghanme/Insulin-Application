package com.example.diabestes_care_app.Base_Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.bitvale.switcher.SwitcherX;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Basic_Activity extends AppCompatActivity {
    static boolean password_is_visible;
    Dialog dialog;

    public void fullscreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return true;
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean validCellPhone(String number) {
        return !android.util.Patterns.PHONE.matcher(number).matches();
    }

    public boolean validUserName(String userName) {
        if (userName.startsWith("P")) {
            return true;
        } else return userName.length() <= 15;
    }

    public boolean validPassword(String password) {
        return password.length() <= 8;
    }

    public boolean validCoPassword(String password, String CoPassword) {
        return !password.equals(CoPassword);
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

    @SuppressLint("UseCompatLoadingForDrawables")
    public void exitHappen(Context context, DatabaseReference db, String UserName) {
        //============================Create + Configure the Dialog here============================
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.exite_layout);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
        Button close = dialog.findViewById(R.id.Close);
        Button continues = dialog.findViewById(R.id.Continue2);
        close.setOnClickListener(v -> {
            if (UserName != null) {
                db.child(UserName).removeValue();
                finish();
            } else {
                Toast.makeText(context, "أنت لم تسجل مستخدم بعد!", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
        continues.setOnClickListener(v -> dialog.dismiss());
    }

    public  void switchTheme(SwitcherX theme, Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);

        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            theme.setChecked(true, true);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            theme.setChecked(false, true);
        }

        theme.setOnClickListener(
                view -> {
                    // When user taps the enable/disable
                    // dark mode button
                    if (isDarkModeOn) {

                        // if dark mode is on it
                        // will turn it off
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        // it will set isDarkModeOn
                        // boolean to false
                        editor.putBoolean("isDarkModeOn", false);
                        editor.apply();

                        // change text of Button
                        theme.setChecked(false, true);
                    } else {

                        // if dark mode is off
                        // it will turn it on
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                        // it will set isDarkModeOn
                        // boolean to true
                        editor.putBoolean("isDarkModeOff", true);
                        editor.apply();

                        // change text of Button
                        theme.setChecked(true , true);
                    }
                });
    }

}
