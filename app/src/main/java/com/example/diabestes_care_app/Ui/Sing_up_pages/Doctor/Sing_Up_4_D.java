package com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;

public class Sing_Up_4_D extends Basic_Activity {
    ImageView imageView;
    int SELECT_IMAGE_CODE = 1;
    Button btn_Upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sign_up_4_d);
        //====================================Define Checkbox===============================
        btn_Upload = findViewById(R.id.btn_Upload);
        imageView = findViewById(R.id.imageView);
        //====================================الانتقال من صفحة الستجيل الحالية للصفحة الثانية ===============================
        btn_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Title"),SELECT_IMAGE_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            Uri uri = data.getData();
            imageView.setImageURI(uri);
            btn_Upload.setText("التأكيد");
        }
    }
}