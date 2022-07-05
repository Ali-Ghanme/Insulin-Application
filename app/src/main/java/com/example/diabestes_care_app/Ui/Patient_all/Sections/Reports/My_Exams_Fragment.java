package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports;

import android.annotation.SuppressLint;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class My_Exams_Fragment extends Fragment {

    // فحوصاتي

    LinearLayout liner_show_one, liner_show_tow;
    // Btn sheet save for examine college
    Button sugaer_one, sugaer_tow, btn_checked_save ,checked_save_height_weight ,checked_save_blood ,checked_save_cholesterol, btn_checked_save_suger;
    // Btn_shett sheet كلية  // Btn_sheet دهون  // Btn_sheet دم
    EditText examine_bmi, examine_college, examine_fats, examine_blood, et_number_suger, btn_sheet_time_day_suger;
    TextView tv_date   , tv_date_time;
    ListView listView;
    String[] time_day_suger = { "قبل النوم","بعد العشاء", "قبل العشاء", "بعد الغداء ", "قبل الغداء", "بعد الإفطار", "قبل الإفطار"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my__exams_, container, false);

        // time blood
        tv_date_time = view.findViewById(R.id.tv_date_time);

        // visible and gone
        sugaer_one = view.findViewById(R.id.btn_day1);
        sugaer_tow = view.findViewById(R.id.btn_week1);
        liner_show_one = view.findViewById(R.id.liner_show_one);
        liner_show_tow = view.findViewById(R.id.liner_show_tow);

        // data from  Home suger
        et_number_suger = view.findViewById(R.id.et_number_suger);
        btn_sheet_time_day_suger = view.findViewById(R.id.btn_sheet_time_day_suger);
        tv_date = view.findViewById(R.id.tv_date);
        btn_checked_save_suger = view.findViewById(R.id.btn_checked_save_suger);


        // btn  sheet next Home
        examine_college = view.findViewById(R.id.examine_college);
        examine_fats = view.findViewById(R.id.examine_fats);
        examine_blood = view.findViewById(R.id.examine_blood);
        examine_bmi = view.findViewById(R.id.examine_bmi);


 // ####################################### Start Visible And Gone
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
// #######################################  End Visible And Gone


 //##########################   Home (My_Exams) Start

        //============================  View Time Zone by Text View in page My_Exams   ============================================
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss ");
        String currentDateandTime = sdf.format(new Date());
        tv_date.setText(currentDateandTime);

        //============================   Button Sheet -   to View time Day  ==============================================
        btn_sheet_time_day_suger.setOnClickListener(new View.OnClickListener() {
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
                        String sugar = listView.getAdapter().getItem(position).toString();
                        sugaer_one.setText(sugar);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.City_bottom_listView);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        //=============================== cheak and save data from suger ==================================================
        btn_checked_save_suger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tv_date.setText(currentDateandTime);

                String number_suger = et_number_suger.getText().toString();
                //   String day_suger = btn_sheet_time_day_suger.getText().toString();
                if (number_suger.length() == 0) {
                    //EditText is empty
                    Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();
                } else {
                    //EditText is not empty
                    Toast.makeText(getActivity(), " قيمة السكر : " + number_suger , Toast.LENGTH_SHORT).show();

                }

            }
        });

// ##########################   Home (My_Exams) End

// ##########################  College Start
        //============================ Button Sheet   -  view data college and Chek,Save  Data ==============================================
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
                        String creat = et_Creatinine.getText().toString();
                        String uric = et_uricacid.getText().toString();
                        String urea = et_urea.getText().toString();
                        if (creat.length() == 0 || uric.length() == 0 || urea.length() == 0) {
                            //EditText is empty
                            Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();
                        } else {
                            //EditText is not empty
                            Toast.makeText(getActivity(), " قيمة : " + creat + " creat" + uric + "uric" + urea + "urea", Toast.LENGTH_SHORT).show();
                            bottomSheetDialog.dismiss();

                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

// ##########################  Fats Start
        //============================ Button Sheet   -  view data Fats and Chek,Save  Data ==============================================
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
                        String cholesterol = et_cholesterol.getText().toString();
                        String triglycerid = et_triglycerid.getText().toString();
                        String ldl = et_ldl.getText().toString();
                        String hdl = et_hdl.getText().toString();
                        if (cholesterol.length() == 0 || triglycerid.length() == 0 || ldl.length() == 0 || hdl.length() == 0) {
                            //EditText is empty
                            Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();
                        } else {
                            //EditText is not empty
                            Toast.makeText(getActivity(), " قيمة : " + cholesterol + " cholesterol : " + triglycerid + "triglycerid : " + ldl + "ldl : "+ hdl + "hdl : ", Toast.LENGTH_SHORT).show();
                            bottomSheetDialog.dismiss();

                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

// ##########################  Blood Start
        //============================ Button Sheet   -  view data Blood and Chek,Save  Data ==============================================
        examine_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_examine_blood_btnsheet, (LinearLayout) getActivity().findViewById(R.id.bottomSheetContier));

//                //============================  View Time Zone by Text View in page My_Exams   ============================================
//                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss ");
//                String currentDateandDateTime = sdf2.format(new Date());
//                tv_date_time.setText(currentDateandDateTime);  // show time exams Blood

                final EditText et_Creatinine = bottomSheetView.findViewById(R.id.et_Creatinine);
                final TextView tv_date_time = bottomSheetView.findViewById(R.id.tv_date_time);

                checked_save_blood = bottomSheetView.findViewById(R.id.checked_save_blood);
                checked_save_blood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Creatinine = et_Creatinine.getText().toString();
                        String date_time = tv_date_time.getText().toString();

                        if (Creatinine.length() == 0 || date_time.length() == 0) {
                            //EditText is empty
                            Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();
                        } else {
                            //EditText is not empty
                            Toast.makeText(getActivity(), " قيمة : " + Creatinine + " date_time" + date_time, Toast.LENGTH_SHORT).show();
                            bottomSheetDialog.dismiss();

                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

// ##########################  Bmi Start
        //============================ Button Sheet   -  view data Fats and Chek,Save  Data ==============================================
        examine_bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme);
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

                        if (bmi_height.length() == 0 || bmi_weight.length() == 0 ) {
                            //EditText is empty
                            Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();
                        } else {
                            //EditText is not empty
                            Toast.makeText(getActivity(), " قيمة : " + bmi_height + " creat" + bmi_weight , Toast.LENGTH_SHORT).show();
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