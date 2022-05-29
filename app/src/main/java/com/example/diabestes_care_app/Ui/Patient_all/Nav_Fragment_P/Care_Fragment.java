package com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.diabestes_care_app.R;

public class Care_Fragment extends Fragment {
    ImageView info;
    Dialog dialog;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_care_, container, false);
        info = view.findViewById(R.id.FC_info);

        //Create the Dialog here
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dilog);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog


//        Okay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(getContext(), "Okay", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show(); // Showing the dialog here
            }
        });

        return view;
    }
}