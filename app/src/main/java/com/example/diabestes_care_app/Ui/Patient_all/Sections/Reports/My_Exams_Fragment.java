package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.Notification_Controller.LocalNotification.AlarmReceiver;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Sing_up_pages.Patient.Sing_Up_2_P;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class My_Exams_Fragment extends Fragment {

    // فحوصاتي

    LinearLayout liner_show_one, liner_show_tow;
    Button sugaer_one, sugaer_tow;
    EditText btn_sheet;

    ListView listView;
    String[] sheet = {"بعد العشاء", "قبل النوم", "قبل العشاء", "بعد الغداء ", "قبل الغداء", "بعد الإفطار", "قبل الإفطار"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my__exams_, container, false);

        sugaer_one = view.findViewById(R.id.btn_day1);
        btn_sheet = view.findViewById(R.id.btn_sheet);

        sugaer_tow = view.findViewById(R.id.btn_week1);
        liner_show_one = view.findViewById(R.id.liner_show_one);
        liner_show_tow = view.findViewById(R.id.liner_show_tow);



        TextView textView= view.findViewById(R.id.date);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss ");
        String currentDateandTime = sdf.format(new Date());
        textView.setText(currentDateandTime);



        sugaer_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 5;
                int y = 5;
                if (y == x) {

                    liner_show_one.setVisibility(View.VISIBLE);
                    liner_show_tow.setVisibility(View.GONE);
                } else {

                    liner_show_one.setVisibility(View.GONE);
                    liner_show_tow.setVisibility(View.GONE);
                }
            }
        });
        //============================Spinner Function==============================================
        btn_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_bottom_sheet, (LinearLayout) getActivity().findViewById(R.id.bottomSheetContier));
                listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.activity_listview, sheet);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        String cityS = listView.getAdapter().getItem(position).toString();
                        sugaer_one.setText(cityS);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.City_bottom_listView);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });


        sugaer_tow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int w = 5;
                int e = 5;
                if (w == e) {

                    liner_show_tow.setVisibility(View.VISIBLE);
                    liner_show_one.setVisibility(View.GONE);


                } else {

                    liner_show_tow.setVisibility(View.GONE);
                    liner_show_one.setVisibility(View.GONE);
                }
            }
        });


        return view;
    }


}