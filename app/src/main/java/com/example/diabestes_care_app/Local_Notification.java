package com.example.diabestes_care_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Local_Notification extends AppCompatActivity {

    private Button btn_save;
    public static final String MyPREFERENCES_Local = "Local_Notification";
    EditText DATA_1, DATA2;
    TextView tvs;
    SharedPreferences sharedpreferences;
    int data_1, data_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_notification);

        tvs = findViewById(R.id.tvs);
        btn_save = findViewById(R.id.btn_save);
        DATA_1 = findViewById(R.id.Sugare);
        DATA2 = findViewById(R.id.When_take_it);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
//        loadData();
//        updateChat();
    }

    public void saveData() {
        sharedpreferences = Local_Notification.this.getSharedPreferences(MyPREFERENCES_Local, MODE_PRIVATE);



        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("TAG_DATA_1", data_1);
        editor.putInt("TAG_DATA_2", data_2);
        editor.apply();

        String Blood = DATA_1.getText().toString();
        data_1 = Integer.parseInt(Blood);
        String Time = DATA2.getText().toString();
        data_2 = Integer.parseInt(Time);

        Log.e("TAG_Local", Blood + Time);
    } // end method save Data

//    public void loadData() {
//        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES_Local, MODE_PRIVATE);
//        tt = sharedPreferences.getString(T, "");
//        nn = sharedPreferences.getString(N, "");
//        ww = sharedPreferences.getString(W, "");
//        bll = sharedPreferences.getString(BL, "");
//        blblbl = sharedPreferences.getString(BLBL, "");
//    }
//
//    public void updateChat() {
//        tvs.setText(tt);
//        tvs.setText(nn);
//        tvs.setText(ww);
//        tvs.setText(bll);
//        tvs.setText(blblbl);
//    }

} // end page