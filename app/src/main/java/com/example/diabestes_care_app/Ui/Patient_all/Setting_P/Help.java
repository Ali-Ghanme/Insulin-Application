package com.example.diabestes_care_app.Ui.Patient_all.Setting_P;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.diabestes_care_app.Policy;
import com.example.diabestes_care_app.Privacy;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.about_app;

public class Help extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout help_app,about,agreement_app,Privacy_app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        findViews();


    }



    private void findViews() {
        help_app =  findViewById(R.id.help_app);
        about =  findViewById(R.id.about_app);
        agreement_app =  findViewById(R.id.agreement_app);
        Privacy_app =  findViewById(R.id.Privacy_app);
    }


    @Override
    public void onClick(View v) {
        if (v == help_app){
            Intent intent = new Intent(Help.this , about_app.class);
            startActivity(intent);
        }else if (v == about){
            Intent intent = new Intent(Help.this , about_app.class);
            startActivity(intent);
        }else if (v == agreement_app){
            Intent intent = new Intent(Help.this , Privacy.class);
            startActivity(intent);
        }else if (v ==Privacy_app){
            Intent intent = new Intent(Help.this , Policy.class);
            startActivity(intent);
        }
    }
}