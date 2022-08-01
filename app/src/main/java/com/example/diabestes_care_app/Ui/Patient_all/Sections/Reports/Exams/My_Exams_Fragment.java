package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports.Exams;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports.Repo.Daily_Repo;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports.Repo.Dwree_Repo;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports.Repo.Reports_Fragment;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.Content.Content;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class My_Exams_Fragment extends Fragment {

    TabLayout tabLayout_exams;
    ViewPager viewPager;
    My_Exams_Fragment.MainAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my__exams_, container, false);

        tabLayout_exams = view.findViewById(R.id.exams_tab_layout_SR);
        viewPager  = view.findViewById(R.id.exams_viewpager_tab_SR);

/*
        // time blood
        tv_date_time = view.findViewById(R.id.tv_date_time);


        liner_show_tow = view.findViewById(R.id.liner_show_tow);
        fats = view.findViewById(R.id.fats);
        college = view.findViewById(R.id.college);
      // btn  sheet next Home
        examine_college = view.findViewById(R.id.examine_college);
        // btn  sheet for examine_college
        examine_college_creatinine = view.findViewById(R.id.examine_college_creatinine);
        examine_college_uric = view.findViewById(R.id.examine_college_uric);
        examine_college_urea = view.findViewById(R.id.examine_college_urea);
        // end  btn  sheet for examine_college

        examine_fats = view.findViewById(R.id.examine_fats);
        // btn  sheet for examine_fats
        examine_fats_hdl = view.findViewById(R.id.examine_fats_hdl);
        examine_fats_ldl = view.findViewById(R.id.examine_fats_ldl);
        examine_fats_triglycerid = view.findViewById(R.id.examine_fats_triglycerid);
        examine_fats_cholesterol = view.findViewById(R.id.examine_fats_cholesterol);
        //end  btn  sheet for examine_fats
        examine_blood = view.findViewById(R.id.examine_blood);
        examine_bmi = view.findViewById(R.id.examine_bmi);
/*
        //============================Get Patient Username===========================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);
         databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername).child("Reports_info").child("فحوصات دورية");
        //=================================Start Visible And Gone===================================

        sugar_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 5;
                int y = 5;
                if (y == x) {
                    sugar_one.getBackground().setTint(Color.parseColor("#707070"));
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
                    sugar_tow.getBackground().setTint(Color.parseColor("#212121"));

                    liner_show_tow.setVisibility(View.VISIBLE);
                    liner_show_one.setVisibility(View.GONE);
                } else {
                    liner_show_tow.setVisibility(View.GONE);
                    liner_show_one.setVisibility(View.GONE);
                }
            }
        });

        //============================  View Time Zone by Text View in page My_Exams================
    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss");
        currentDataTime = sdf.format(new Date());
        tv_date.setText(currentDataTime);
*/
        //====================Adapter Configuration=================================================
        adapter = new My_Exams_Fragment.MainAdapter(getChildFragmentManager());
        adapter.AddFragment(new Daily_Sugar(), "فحص يومي");
        adapter.AddFragment(new Dmonthly_Sugar(), "فحص دوري");

        //========================Set Adapter for the viewpager Configuration=======================
        viewPager.setAdapter(adapter);
        tabLayout_exams.setupWithViewPager(viewPager);

       /*
        //================================= Visible And Gone For fats And college =========
        examine_college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int r = 3;
                if (r == 3) {
                    college.setVisibility(View.VISIBLE);
                    fats.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "فحوصات الكلية : " + r, Toast.LENGTH_SHORT).show();
                } else {

                    college.setVisibility(View.GONE);
                    fats.setVisibility(View.GONE);
                }
            }
        });
        examine_fats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int w = 4;
                if (w == 4) {

                    fats.setVisibility(View.VISIBLE);
                    college.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "فحوصات الدهون : " + w, Toast.LENGTH_SHORT).show();

                } else {
                    fats.setVisibility(View.GONE);
                    college.setVisibility(View.GONE);
                }
            }
        });

        //======================Button Sheet view data college and Chek,Save Data===================
        //====college creatinine
        examine_college_creatinine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_examine_creatinine_btnsheet, (LinearLayout) getActivity().findViewById(R.id.bottomSheetContier));
                final EditText et_Creatinine = bottomSheetView.findViewById(R.id.et_Creatinine);

                btn = bottomSheetView.findViewById(R.id.checked_save_creatinine);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        creatine = et_Creatinine.getText().toString();


                        //   if (creatine.length() == 0) {
                        if (creatine.isEmpty()) {
                            Toast.makeText(getActivity(), "لا توجد قيم مدخلة", Toast.LENGTH_SHORT).show();
                        } else if ((creatineValue = Integer.parseInt(creatine)) >= 0.7 && (creatineValue = Integer.parseInt(creatine)) <= 1.2) {
                            showSuccessDialog("أنت بصحة جيدة ", "استمر على هذا النحو من المحافظة على صحتك ");
                            // Upload Data on Firebase
                            databaseReference.child("فحوصات وظائف الكلى").child("Creatine").setValue(creatine);
                            bottomSheetDialog.dismiss();
                        } else {
                            showErrorDialog("تحذير للمتابعة", " برجى استشارة طبيب على الفور او التوجه لاقرب مستشفى");
                            Toast.makeText(getActivity(), " تبيه ! ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        //==== college uric
        examine_college_uric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_college_uricacid_btnsheet, (LinearLayout) getActivity().findViewById(R.id.bottomSheetContier));
                final EditText et_uricacid = bottomSheetView.findViewById(R.id.et_uricacid);

                btn_checked_save = bottomSheetView.findViewById(R.id.checked_save);
                btn_checked_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uric = et_uricacid.getText().toString();

                        if (uric.isEmpty()) {
                            Toast.makeText(getActivity(), "لا توجد قيم مدخلة", Toast.LENGTH_SHORT).show();
                        } else if ((uricValue = Integer.parseInt(uric)) >= 3.5 && (uricValue = Integer.parseInt(uric)) <= 7.2) {
                            showSuccessDialog("أنت بصحة جيدة ", "استمر على هذا النحو من المحافظة على صحتك ");
                            // Upload Data on Firebase
                            databaseReference.child("فحوصات وظائف الكلى").child("Uric").setValue(uric);
                            bottomSheetDialog.dismiss();
                        } else {
                            showErrorDialog("تحذير للمتابعة", " برجى استشارة طبيب على الفور او التوجه لاقرب مستشفى");
                            Toast.makeText(getActivity(), " تبيه ! ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        //==== college  urea
        examine_college_urea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_examine_urea_btnsheet, (LinearLayout) getActivity().findViewById(R.id.bottomSheetContier));
                final EditText et_urea = bottomSheetView.findViewById(R.id.et_urea);

                btn_checked_save = bottomSheetView.findViewById(R.id.checked_save);
                btn_checked_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        urea = et_urea.getText().toString();

                        if (urea.isEmpty()) {
                            Toast.makeText(getActivity(), "لا توجد قيم مدخلة", Toast.LENGTH_SHORT).show();
                        } else if ((ureaValue = Integer.parseInt(urea)) >= 6 && (ureaValue = Integer.parseInt(urea)) <= 25) {
                            showSuccessDialog("أنت بصحة جيدة ", "استمر على هذا النحو من المحافظة على صحتك ");
                            // Upload Data on Firebase
                            databaseReference.child("فحوصات وظائف الكلى").child("Urea").setValue(urea);
                            bottomSheetDialog.dismiss();
                        } else {
                            showErrorDialog("تحذير للمتابعة", " برجى استشارة طبيب على الفور او التوجه لاقرب مستشفى برجى استشارة طبيب على الفور او التوجه لاقرب مستشفى");
                            Toast.makeText(getActivity(), " تبيه ! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });


        //========== Button Sheet view data Fats , Chek And Save  Data ==========================
        //==== Fats cholesterol
        examine_fats_cholesterol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_fats_cholesterol_btnsheet, (LinearLayout) getActivity().findViewById(R.id.bottomSheetContier));
                final EditText et_cholesterol = bottomSheetView.findViewById(R.id.et_cholesterol);
                checked_save_cholesterol = bottomSheetView.findViewById(R.id.checked_save_cholesterol);
                checked_save_cholesterol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cholesterol = et_cholesterol.getText().toString();

                        if (cholesterol.isEmpty()) {
                            Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();
                        } else if ((cholesterolValue = Integer.parseInt(cholesterol)) <= 200) {
                            showSuccessDialog("أنت بصحة جيدة ", "استمر على هذا النحو من المحافظة على صحتك " + cholesterolValue);
                            databaseReference.child("فحوصات الدهون").child("Cholesterol").setValue(cholesterol);
                        } else if ((cholesterolValue = Integer.parseInt(cholesterol)) > 200 && (cholesterolValue = Integer.parseInt(cholesterol)) <= 239) {
                            showWarningDialog("لا بأس استمر على الارشادات ", "هذا المؤشر ينبهك بالمحافظة على الصحة واتباع الإرشادات");
                            databaseReference.child("فحوصات الدهون").child("Cholesterol").setValue(cholesterol);
                        } else if ((cholesterolValue = Integer.parseInt(cholesterol)) >= 240) {
                            showErrorDialog("تحذير للمتابعة", " برجى استشارة طبيب على الفور او التوجه لاقرب مستشفى  ");
                            databaseReference.child("فحوصات الدهون").child("Cholesterol").setValue(cholesterol);
                        } else {
                            // Upload Data on Firebase
                            bottomSheetDialog.dismiss();
                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        //==== Fats triglycerid
        examine_fats_triglycerid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_fats_triglycerid_btnsheet, (LinearLayout) getActivity().findViewById(R.id.bottomSheetContier));
                final EditText et_triglycerid = bottomSheetView.findViewById(R.id.et_triglycerid);
                checked_save_cholesterol = bottomSheetView.findViewById(R.id.checked_save_cholesterol);
                checked_save_cholesterol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        triglyceride = et_triglycerid.getText().toString();
                        if (cholesterol.isEmpty()) {
                            Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();
                        } else if ((triglycerideValue = Integer.parseInt(triglyceride)) < 150) {
                            databaseReference.child("فحوصات الدهون").child("Triglyceride").setValue(triglyceride);
                            showSuccessDialog("أنت بصحة جيدة ", "استمر على هذا النحو من المحافظة على صحتك "  );

                            //  200 - 499
                        } else if ((triglycerideValue = Integer.parseInt(triglyceride)) >= 200 && (triglycerideValue = Integer.parseInt(triglyceride)) <= 499) {
                            databaseReference.child("فحوصات الدهون").child("Triglyceride").setValue(triglyceride);
                            showWarningDialog("لا بأس استمر على الارشادات ", "هذا المؤشر ينبهك بالمحافظة على الصحة واتباع الإرشادات");


                        } else if ((triglycerideValue = Integer.parseInt(triglyceride)) > 500) {
                            databaseReference.child("فحوصات الدهون").child("Triglyceride").setValue(triglyceride);
                            showErrorDialog("تحذير للمتابعة", " برجى استشارة طبيب على الفور او التوجه لاقرب مستشفى  ");
                        } else {
                            // Upload Data on Firebase
                            bottomSheetDialog.dismiss();
                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        //==== Fats ldl
        examine_fats_ldl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_fats_ldl_btnsheet, (LinearLayout) getActivity().findViewById(R.id.bottomSheetContier));

                final EditText et_ldl = bottomSheetView.findViewById(R.id.et_ldl);
                checked_save_cholesterol = bottomSheetView.findViewById(R.id.checked_save_cholesterol);
                checked_save_cholesterol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ldl = et_ldl.getText().toString();


                        if (ldl.isEmpty()) {
                            Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();

                        } else if ((ldlValue = Integer.parseInt(ldl)) >= 70 && (ldlValue = Integer.parseInt(ldl)) <= 129) {
                            databaseReference.child("فحوصات الدهون").child("IDL").setValue(ldl);
                            showSuccessDialog("أنت بصحة جيدة ", "استمر على هذا النحو من المحافظة على صحتك " );


                        } else if ((ldlValue = Integer.parseInt(ldl)) <= 70) {
                            databaseReference.child("فحوصات الدهون").child("IDL").setValue(ldl);
                            showWarningDialog("لا بأس استمر على الارشادات ", "هذا المؤشر ينبهك بالمحافظة على الصحة واتباع الإرشادات");


                        } else if ((ldlValue = Integer.parseInt(ldl)) > 130) {
                            databaseReference.child("فحوصات الدهون").child("IDL").setValue(ldl);
                            showErrorDialog("تحذير للمتابعة", " برجى استشارة طبيب على الفور او التوجه لاقرب مستشفى  ");

                        } else {
                            // Upload Data on Firebase
                            bottomSheetDialog.dismiss();
                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        //=====Fats hdl
        examine_fats_hdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_fats_hdl_btnsheet, (LinearLayout) getActivity().findViewById(R.id.bottomSheetContier));

                final EditText et_hdl = bottomSheetView.findViewById(R.id.et_hdl);
                checked_save_cholesterol = bottomSheetView.findViewById(R.id.checked_save_cholesterol);
                checked_save_cholesterol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        hdl = et_hdl.getText().toString();


                        if (hdl.isEmpty()) {
                            Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();
                        } else if ((hdlValue = Integer.parseInt(hdl)) >= 60 && (hdlValue = Integer.parseInt(hdl)) <= 148) {
                            databaseReference.child("فحوصات الدهون").child("HDL").setValue(hdl);
                            showSuccessDialog("أنت بصحة جيدة ", "استمر على هذا النحو من المحافظة على صحتك " );

                        } else if ((hdlValue = Integer.parseInt(hdl)) < 60) {
                            showWarningDialog("لا بأس استمر على الارشادات ", "هذا المؤشر ينبهك بالمحافظة على الصحة واتباع الإرشادات");

                            databaseReference.child("فحوصات الدهون").child("HDL").setValue(hdl);

                        } else if ((hdlValue = Integer.parseInt(hdl)) > 149) {
                            showErrorDialog("تحذير للمتابعة", " برجى استشارة طبيب على الفور او التوجه لاقرب مستشفى  ");

                            databaseReference.child("فحوصات الدهون").child("HDL").setValue(hdl);

                        } else {
                            // Upload Data on Firebase
                            bottomSheetDialog.dismiss();
                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        //================ Button Sheet view data Blood and Chek,Save  Data====================
        examine_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.layout_examine_blood_btnsheet, (LinearLayout) getActivity().findViewById(R.id.bottomSheetContier));

                final EditText et_Creatinine = bottomSheetView.findViewById(R.id.et_Creatinine);
                final EditText et_Creatinine2 = bottomSheetView.findViewById(R.id.et_Creatinine2);
                final TextView tv_date_time = bottomSheetView.findViewById(R.id.tv_date_time);

                checked_save_blood = bottomSheetView.findViewById(R.id.checked_save_blood);

                tv_date_time.setText(currentDataBloodTime);
                checked_save_blood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Blood_Pressure = et_Creatinine.getText().toString();
                        Blood_Pressure2 = et_Creatinine2.getText().toString();

                         date_time = tv_date_time.getText().toString();


                        if (Blood_Pressure.isEmpty() && Blood_Pressure2.isEmpty()  ) {
                            Toast.makeText(getActivity(), " توجد نتيجة غير مدخلة", Toast.LENGTH_SHORT).show();
                        } else if ((Blood_PressureValue = Integer.parseInt(Blood_Pressure)) <= 60 && (Blood_PressureValue2 = Integer.parseInt(Blood_Pressure2)) <= 90) {
                        } else if ((Blood_PressureValue = Integer.parseInt(Blood_Pressure)) <= 80 && (Blood_PressureValue2 = Integer.parseInt(Blood_Pressure2)) <= 120) {
                            // Upload Data on Firebase
                            showErrorDialog("تحذير للمتابعة", " برجى استشارة طبيب على الفور او التوجه لاقرب مستشفى");
                            databaseReference.child("فحوصات الدهون").child("Cholesterol").setValue(Blood_Pressure);
                            databaseReference.child("فحوصات الدهون").child("Cholesterol").setValue(Blood_Pressure2);
                            bottomSheetDialog.dismiss();
                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        //================= Button Sheet view data Fats and Chek,Save Data  ===============
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
                        college.setVisibility(View.GONE);
                        fats.setVisibility(View.GONE);
                        bmi_height = et_bmi_height.getText().toString();
                        bmi_weight = et_bmi_weight.getText().toString();

                        // 53 / 173*173
                        if (bmi_height.isEmpty() || bmi_weight.isEmpty()) {
                            //EditText is empty
                            Toast.makeText(getActivity(), "لا توجد قيم مدخلة", Toast.LENGTH_SHORT).show();
                        } else {
                            int bmi_heightValue = Integer.parseInt(bmi_height);
                            int bmi_weightValue = Integer.parseInt(bmi_weight);
                            //EditText is not empty
                            bmi = bmi_heightValue / bmi_weightValue * bmi_weightValue;
                            // Toast.makeText(getActivity(), " مؤشر الكتلة : " + bmi, Toast.LENGTH_SHORT).show();
                            showSuccessDialog("مؤشر الكتلة ",  "  مؤشر كتلة الجسم هوا : ( " +bmi+ " ) النسبة جيدة حافظ على سلامتك   " );

                            databaseReference.child("فحوصات مؤشر كتلة الجسم").child("bmi_height").setValue(bmi_height);
                            databaseReference.child("فحوصات مؤشر كتلة الجسم").child("bmi_weight").setValue(bmi_weight);
                            bottomSheetDialog.dismiss();
                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

*/
        return view;
    }
    //=================  Custom Dialog  ===============
    private void showSuccessDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_success_dialog, (ConstraintLayout) getActivity().findViewById(R.id.layoutDialogContainer));

        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(title);
        ((TextView) view.findViewById(R.id.textMessage)).setText(message);
        ((Button) view.findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.okay));
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_succsess);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }

        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showWarningDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_warning_dialog, (ConstraintLayout) getActivity().findViewById(R.id.layoutDialogContainer));

        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(title);
        ((TextView) view.findViewById(R.id.textMessage)).setText(message);
        ((Button) view.findViewById(R.id.buttonYes)).setText(getResources().getString(R.string.yes));
        ((Button) view.findViewById(R.id.buttonNo)).setText(getResources().getString(R.string.no));
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_warning);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Toast.makeText(getActivity(), "الإرشادات", Toast.LENGTH_SHORT).show();
                Intent mainActivity = new Intent(getActivity(), Content.class);
                startActivity(mainActivity);

            }

        });
        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Toast.makeText(getActivity(), "Ok", Toast.LENGTH_SHORT).show();
            }

        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showErrorDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_error_dialog, (ConstraintLayout) getActivity().findViewById(R.id.layoutDialogContainer));
        builder.setView(view);
         ((TextView) view.findViewById(R.id.textTitle)).setText(title);

        // get variable and show in text view
//        ((TextView) view.findViewById(R.id.textTitle)).setText(number_sugar);
        // هذا السطر بمكني اجيب البيانات  من القيمة المدخلة وتضمينها داخل ال dialog
        // ((TextView) view.findViewById(R.id.textTitle)).setText(number_sugar = et_number_sugar.getText().toString());
//        ((TextView) view.findViewById(R.id.textMessage)).setText(getResources().getString(R.string.content_error));
        ((TextView) view.findViewById(R.id.textMessage)).setText(message);
        ((Button) view.findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.okay));
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_error);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
    //=====================================Adapter method===========================================
    public static class MainAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();

        //Create Constructor
        public void AddFragment(Fragment fragment, String s) {
            fragmentArrayList.add(fragment);
            stringArrayList.add(s);
        }

        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return stringArrayList.get(position);
        }
    }
}