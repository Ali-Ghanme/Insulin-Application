package com.example.diabestes_care_app.Ui.Patient_all.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diabestes_care_app.Adapters.ViewAdapter_Home_P;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.Models.Doctor_List_Modal_P;
import com.example.diabestes_care_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Home_Fragment extends Fragment {
    RecyclerView DoctorRecyclerview_P;
    ViewAdapter_Home_P DoctorAdapter_P;
    List<Doctor_List_Modal_P> mData;
    EditText searchInput;
    CharSequence search = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_, container, false);
        searchInput = view.findViewById(R.id.search_input);
        DoctorRecyclerview_P = view.findViewById(R.id.Doctor_List_recyclerView);
        mData = new ArrayList<>();

        DoctorRecyclerview_P.setHasFixedSize(true);
        DoctorAdapter_P = new ViewAdapter_Home_P(getContext(), mData);
        DoctorRecyclerview_P.setAdapter(DoctorAdapter_P);
        DoctorRecyclerview_P.setLayoutManager(new LinearLayoutManager(getContext()));

        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "علي غانم", "مطور اندرويد", "4.5"));
        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "محمد صيام", "مطور لرافيل", "4.5"));
        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "عبد الله الدالي", "جرافيك ديزان", "4.5"));
        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "أحمد زويد", "مطور اندرويد", "4.5"));
        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "علي غانم", "مطور اندرويد", "4.5"));
        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "محمد صيام", "مطور لرافيل", "4.5"));
        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "عبد الله الدالي", "جرافيك ديزان", "4.5"));
        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "أحمد زويد", "مطور اندرويد", "4.5"));
        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "علي غانم", "مطور اندرويد", "4.5"));
        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "محمد صيام", "مطور لرافيل", "4.5"));
        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "عبد الله الدالي", "جرافيك ديزان", "4.5"));
        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "أحمد زويد", "مطور اندرويد", "4.5"));
        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "علي غانم", "مطور اندرويد", "4.5"));
        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "محمد صيام", "مطور لرافيل", "4.5"));
        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "عبد الله الدالي", "جرافيك ديزان", "4.5"));
        mData.add(new Doctor_List_Modal_P(R.drawable.doctor_photo, "أحمد زويد", "مطور اندرويد", "4.5"));

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DoctorAdapter_P.getFilter().filter(s);
                search = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return view;
    }
}