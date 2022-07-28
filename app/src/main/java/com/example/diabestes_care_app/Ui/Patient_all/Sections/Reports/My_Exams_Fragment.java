package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class My_Exams_Fragment extends Fragment {


    // Tow LinerLayout (Home,Multiple Scan)
    LinearLayout liner_show_one, liner_show_tow;
    // Btn sheet
    Button sugar_one, sugar_tow, btn_checked_save, checked_save_height_weight, checked_save_blood, checked_save_cholesterol, btn_checked_save_sugar;
    // Btn_sheet sheet كلية  // Btn_sheet دهون  // Btn_sheet دم
    EditText examine_bmi, examine_college, examine_fats, examine_blood, et_number_sugar, btn_sheet_time_day_sugar;
    TextView tv_date, tv_date_time;
    DatabaseReference databaseReference, databaseReference_daily;
    ListView listView;
    String PatientUsername, number_sugar, sugar, currentDataTime, creatine, uric, urea, hdl, ldl, triglyceride, cholesterol, date_time, Blood_Pressure;
    String[] time_day_suger = {"قبل النوم", "بعد العشاء", "قبل العشاء", "بعد الغداء ", "قبل الغداء", "بعد الإفطار", "قبل الإفطار"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my__exams_, container, false);

        // time blood
        tv_date_time = view.findViewById(R.id.tv_date_time);

        // visible and gone
        sugar_one = view.findViewById(R.id.btn_day1);
        sugar_tow = view.findViewById(R.id.btn_week1);
        liner_show_one = view.findViewById(R.id.liner_show_one);
        liner_show_tow = view.findViewById(R.id.liner_show_tow);

        // data from  Home sugar
        et_number_sugar = view.findViewById(R.id.et_number_suger);
        btn_sheet_time_day_sugar = view.findViewById(R.id.btn_sheet_time_day_suger);
        tv_date = view.findViewById(R.id.tv_date);
        btn_checked_save_sugar = view.findViewById(R.id.btn_checked_save_suger);


        // btn  sheet next Home
        examine_college = view.findViewById(R.id.examine_college);
        examine_fats = view.findViewById(R.id.examine_fats);
        examine_blood = view.findViewById(R.id.examine_blood);
        examine_bmi = view.findViewById(R.id.examine_bmi);


        //============================Get Patient Username===========================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);
        databaseReference_daily = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername).child("Reports_info").child("فحص يومي").push();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //=================================Start Visible And Gone===================================
        sugar_one.setOnClickListener(new View.OnClickListener() {
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
        sugar_tow.setOnClickListener(new View.OnClickListener() {
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

        //============================  View Time Zone by Text View in page My_Exams================
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss ");
        currentDataTime = sdf.format(new Date());
        tv_date.setText(currentDataTime);

        //============================Button Sheet to View time Day=================================
        btn_sheet_time_day_sugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_bottom_sheet, (LinearLayout) getActivity().findViewById(R.id.bottomSheetContier));
                listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.activity_listview, time_day_suger);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        sugar = listView.getAdapter().getItem(position).toString();
                        btn_sheet_time_day_sugar.setText(sugar);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetView.findViewById(R.id.City_bottom_listView);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        //=============================== Check and save data from sugar Edit Text==================
        btn_checked_save_sugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_sugar = et_number_sugar.getText().toString();
                if (number_sugar.length() == 0) {
                    Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), " قيمة السكر : " + number_sugar, Toast.LENGTH_SHORT).show();
                    // Upload Data on Firebase
                    databaseReference_daily.child("نسبة السكر في الدم").setValue(number_sugar);
                    databaseReference_daily.child("فترة القياس").setValue(sugar);
                    databaseReference_daily.child("وقت القياس").setValue(currentDataTime);
                }
            }
        });
        // End of First Screen

        //======================Button Sheet view data college and Chek,Save Data===================
        examine_college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_examine_college_btnsheet, (LinearLayout) getActivity().findViewById(R.id.bottomSheetContier));
                final EditText et_Creatinine = bottomSheetView.findViewById(R.id.et_Creatinine);
                final EditText et_uricacid = bottomSheetView.findViewById(R.id.et_uricacid);
                final EditText et_urea = bottomSheetView.findViewById(R.id.et_urea);

                btn_checked_save = bottomSheetView.findViewById(R.id.checked_save);
                btn_checked_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        creatine = et_Creatinine.getText().toString();
                        uric = et_uricacid.getText().toString();
                        urea = et_urea.getText().toString();
                        if (creatine.length() == 0 || uric.length() == 0 || urea.length() == 0) {
                            Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();
                        } else {
                            // Upload Data on Firebase
                            databaseReference.child("patient").child(PatientUsername).child("Reports_info").child("فحوصات دورية").child("فحوصات وظائف الكلى").child("Creatine").setValue(creatine);
                            databaseReference.child("patient").child(PatientUsername).child("Reports_info").child("فحوصات دورية").child("فحوصات وظائف الكلى").child("Uric").setValue(uric);
                            databaseReference.child("patient").child(PatientUsername).child("Reports_info").child("فحوصات دورية").child("فحوصات وظائف الكلى").child("Urea").setValue(urea);
                            bottomSheetDialog.dismiss();
                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        //========== Button Sheet   -  view data Fats and Chek,Save  Data ==========================
        examine_fats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_examine_fats_btnsheet, (LinearLayout) getActivity().findViewById(R.id.bottomSheetContier));
                final EditText et_cholesterol = bottomSheetView.findViewById(R.id.et_cholesterol);
                final EditText et_triglycerid = bottomSheetView.findViewById(R.id.et_triglycerid);
                final EditText et_ldl = bottomSheetView.findViewById(R.id.et_ldl);
                final EditText et_hdl = bottomSheetView.findViewById(R.id.et_hdl);
                checked_save_cholesterol = bottomSheetView.findViewById(R.id.checked_save_cholesterol);
                checked_save_cholesterol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cholesterol = et_cholesterol.getText().toString();
                        triglyceride = et_triglycerid.getText().toString();
                        ldl = et_ldl.getText().toString();
                        hdl = et_hdl.getText().toString();
                        if (cholesterol.length() == 0 || triglyceride.length() == 0 || ldl.length() == 0 || hdl.length() == 0) {
                            Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();
                        } else {
                            // Upload Data on Firebase
                            databaseReference.child("patient").child(PatientUsername).child("Reports_info").child("فحوصات دورية").child("فحوصات الدهون").child("Cholesterol").setValue(cholesterol);
                            databaseReference.child("patient").child(PatientUsername).child("Reports_info").child("فحوصات دورية").child("فحوصات الدهون").child("Triglyceride").setValue(triglyceride);
                            databaseReference.child("patient").child(PatientUsername).child("Reports_info").child("فحوصات دورية").child("فحوصات الدهون").child("IDL").setValue(ldl);
                            databaseReference.child("patient").child(PatientUsername).child("Reports_info").child("فحوصات دورية").child("فحوصات الدهون").child("HDL").setValue(hdl);
                            bottomSheetDialog.dismiss();
                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
//==================================================================================================

        //================ Button Sheet   -  view data Blood and Chek,Save  Data====================
        examine_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_examine_blood_btnsheet, (LinearLayout) getActivity().findViewById(R.id.bottomSheetContier));

                final EditText et_Creatinine = bottomSheetView.findViewById(R.id.et_Creatinine);
                final TextView tv_date_time = bottomSheetView.findViewById(R.id.tv_date_time);

                checked_save_blood = bottomSheetView.findViewById(R.id.checked_save_blood);
                checked_save_blood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Blood_Pressure = et_Creatinine.getText().toString();
                        date_time = tv_date_time.getText().toString();

                        if (Blood_Pressure.length() == 0 || date_time.length() == 0) {
                            Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();
                        } else {
                            // Upload Data on Firebase
                            databaseReference.child("patient").child(PatientUsername).child("Reports_info").child("فحوصات دورية").child("فحوصات الدهون").child("Cholesterol").setValue(Blood_Pressure);
                            databaseReference.child("patient").child(PatientUsername).child("Reports_info").child("فحوصات دورية").child("فحوصات الدهون").child("Triglyceride").setValue(date_time);
                            bottomSheetDialog.dismiss();
                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        //============================ Button Sheet-view data Fats and Chek,Save Data===============
        examine_bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_examine_bmi_btnsheet, (LinearLayout) getActivity().findViewById(R.id.bottomSheetContier));
                final EditText et_bmi_height = bottomSheetView.findViewById(R.id.et_bmi_height);
                final EditText et_bmi_weight = bottomSheetView.findViewById(R.id.et_bmi_weight);

                checked_save_height_weight = bottomSheetView.findViewById(R.id.checked_save_height_weight);
                checked_save_height_weight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String bmi_height = et_bmi_height.getText().toString();
                        String bmi_weight = et_bmi_weight.getText().toString();

                        if (bmi_height.length() == 0 || bmi_weight.length() == 0) {
                            //EditText is empty
                            Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();
                        } else {
                            //EditText is not empty
                            Toast.makeText(getActivity(), " قيمة : " + bmi_height + " creat" + bmi_weight, Toast.LENGTH_SHORT).show();
                            bottomSheetDialog.dismiss();
                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        return view;
    }
}