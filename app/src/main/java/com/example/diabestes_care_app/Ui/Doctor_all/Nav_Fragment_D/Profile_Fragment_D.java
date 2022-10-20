package com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Doctor_Fragment.MyPREFERENCES_D;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bitvale.switcher.SwitcherX;
import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Doctor_all.Setting_D.Edit_Profile_D;
import com.example.diabestes_care_app.Ui.Patient_all.Setting_P.Help;
import com.example.diabestes_care_app.Ui.Sing_In.Sing_In;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile_Fragment_D extends Fragment {

    // Section
    RelativeLayout notification_cont, DarkMode_cont, help_cont, LogOut_cont;
    // Doctor Profile Image
    ImageView imageView, imageView2;
    // Doctor UserName + name
    TextView name;
    //Firebase
    DatabaseReference myRef;
    //Username for SharedPreference
    String DoctorUsername;
    SwitcherX theme;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile___d, container, false);

        //============================Defines=======================================================
        notification_cont = view.findViewById(R.id.FB_notification_cont_d);
        DarkMode_cont = view.findViewById(R.id.FB_DarkMode_cont_d);
        help_cont = view.findViewById(R.id.FB_help_cont_d);
        LogOut_cont = view.findViewById(R.id.FB_LogOut_cont_d);
        name = view.findViewById(R.id.FB_tv_doctor_name_d);
        imageView = view.findViewById(R.id.FB_Doctor_image_d);
        imageView2 = view.findViewById(R.id.FB_Patient_edit_d);
        theme = view.findViewById(R.id.FB_switcher_d);

        SharedPreferences prefs = this.requireActivity().getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);

        //============================Next Click Listener===========================================
        imageView2.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Edit_Profile_D.class);
            startActivity(intent);
        });

        theme.setOnClickListener(v -> ((Basic_Activity) requireActivity()).switchTheme(theme,getContext()));

        help_cont.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Help.class);
            startActivity(intent);
        });

        LogOut_cont.setOnClickListener(v -> {
            SharedPreferences preferences = requireActivity().getSharedPreferences("checkbox_D", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember_D", "false");
            editor.apply();
            Intent intent_d = new Intent(getActivity(), Sing_In.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent_d);
            requireActivity().finish();
        });
        return view;
    }

    //============================Get And Show Patient Data=========================================
    @Override
    public void onStart() {
        super.onStart();
        myRef = FirebaseDatabase.getInstance().getReference("doctor");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getActivity() == null) {
                    return;
                } else {
                    String PatientImage = snapshot.child(DoctorUsername).child("User_Profile_Image").child("Image").child("mImageUrI").getValue(String.class);
                    String PatientName = snapshot.child(DoctorUsername).child("personal_info").child("name_ar").getValue(String.class);
                    Log.d("TAG", name + "/" + PatientImage);
                    Glide.with(getActivity()).load(PatientImage).into(imageView);
                    name.setText(PatientName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}