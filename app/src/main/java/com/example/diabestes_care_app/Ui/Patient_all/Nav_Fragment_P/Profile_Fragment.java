package com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.NotificationSender.SendNotification;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Setting_P.Edit_Profile_P;
import com.example.diabestes_care_app.Ui.Patient_all.Setting_P.Help;
import com.example.diabestes_care_app.Ui.Patient_all.Setting_P.notification;
import com.example.diabestes_care_app.Ui.Sing_In.Sing_In;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class Profile_Fragment extends Fragment {
    // Section
    RelativeLayout notification_cont, DarkMode_cont, help_cont, LogOut_cont, FB_help_cont;
    // Image Patient Profile , Edit Icon
    ImageView imageView, imageView2;
    // Patient Name
    TextView name;
    // Firebase
    DatabaseReference myRef;
    // Patient Username
    String PatientUsername;
    // Shared Preference
    SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_, container, false);

        //============================Defines=======================================================
        notification_cont = view.findViewById(R.id.FB_notification_cont);
        DarkMode_cont = view.findViewById(R.id.FB_DarkMode_cont);
        help_cont = view.findViewById(R.id.FB_help_cont);
        LogOut_cont = view.findViewById(R.id.FB_LogOut_cont);
        name = view.findViewById(R.id.FB_tv_Patient_name);
        imageView = view.findViewById(R.id.FB_Patient_image);
        imageView2 = view.findViewById(R.id.FB_Patient_edit);
        FB_help_cont = view.findViewById(R.id.FB_help_contt);
        Switch DarkMode_cont = view.findViewById(R.id.FB_switcher);

        //============================Shared Preference=============================================
        prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);

        //============================Next Click Listener===========================================
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Edit_Profile_P.class);
                startActivity(intent);
            }
        });

        FB_help_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SendNotification.class);
                startActivity(intent);
            }
        });

        notification_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), notification.class);
                startActivity(intent);
            }
        });

//        DarkMode_cont.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    Toast.makeText(getContext(), "Hallow Dark Mod is Unavailable right now 😉", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(getContext(), "😉", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });

        help_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Help.class);
                startActivity(intent);
            }
        });

        //==============================Logout Patient==============================================
        LogOut_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("checkbox_P", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember_P", "false");
                editor.apply();
                editor.clear();
                Intent intent_p = new Intent(getActivity(), Sing_In.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent_p);
                getActivity().finishAffinity();
            }
        });
        return view;
    }

    //============================Get And Show Patient Data=========================================
    @Override
    public void onStart() {
        super.onStart();
        myRef = FirebaseDatabase.getInstance().getReference("patient");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getActivity() == null) {
                    return;
                }
                String PatientImage = snapshot.child(PatientUsername).child("User_Profile_Image").child("Image").child("mImageUrI").getValue(String.class);
                String PatientName = snapshot.child(PatientUsername).child("personal_info").child("name").getValue(String.class);
                Log.d("TAG", name + "/" + PatientImage);
                name.setText(PatientName);
                Glide.with(getActivity()).load(PatientImage).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}