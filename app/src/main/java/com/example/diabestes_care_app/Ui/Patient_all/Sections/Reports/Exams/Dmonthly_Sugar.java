package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports.Exams;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.Content.Content;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Dmonthly_Sugar extends Fragment {
    // Tow LinerLayout (Home,Multiple Scan)
    LinearLayout liner_show_tow;
    RelativeLayout fats, college;
    // Btn sheet
    Button btn_checked_save, btn, checked_save_height_weight, checked_save_blood, checked_save_cholesterol, checked_save_triglycerid;
    // Btn_sheet sheet كلية  // Btn_sheet دهون  // Btn_sheet دم
    EditText examine_college_creatinine, examine_fats_hdl, examine_fats_ldl, examine_fats_triglycerid, examine_fats_cholesterol, examine_college_uric, examine_college_urea;
    TextView examine_blood, examine_fats, examine_college;
    DatabaseReference databaseReference;
    Double bmi, creatineValue, uricValue, ureaValue, cholesterolValue, triglycerideValue, ldlValue, hdlValue, Blood_PressureValue;

    ListView listView;
    String PatientUsername, bmi_weight, bmi_height, creatine, uric, urea, hdl, ldl, triglyceride, cholesterol, Blood_Pressure;

    // Mohammed Siam
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dmonthly_suger, container, false);
        // time blood
        //  tv_date_time = view.findViewById(R.id.tv_date_time);


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
//        examine_blood = view.findViewById(R.id.examine_blood);
        // examine_bmi = view.findViewById(R.id.examine_bmi);

        //============================Get Patient Username===========================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);
        databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername).child("Reports_info").child("فحوصات دورية");

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
                        } else if ((creatineValue = Double.parseDouble(creatine)) > 0.7 && (creatineValue = Double.parseDouble(creatine)) <= 1.2) {
                            showSuccessDialog("أنت بصحة جيدة ", "استمر على هذا النحو من المحافظة على صحتك ");
                            // Upload Data on Firebase
                            databaseReference.child("فحوصات وظائف الكلى").child("Creatine").setValue(creatine + "");
                            bottomSheetDialog.dismiss();
                        } else if ((creatineValue = Double.parseDouble(creatine)) < 0.6 || (creatineValue = Double.parseDouble(creatine)) > 1.3) {
                            showWarningDialog("لا بأس استمر على الارشادات ", "هذا المؤشر ينبهك بالمحافظة على الصحة واتباع الإرشادات لانك معرض للاصابة بالسكري ");
                            // Upload Data on Firebase
                            databaseReference.child("فحوصات وظائف الكلى").child("Creatine").setValue(creatine + "");
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
                        } else if ((uricValue = Double.parseDouble(uric)) >= 3.5 && (uricValue = Double.parseDouble(uric)) <= 7.2) {
                            showSuccessDialog("أنت بصحة جيدة ", "استمر على هذا النحو من المحافظة على صحتك ");
                            // Upload Data on Firebase
                            databaseReference.child("فحوصات وظائف الكلى").child("Uric").setValue(uric + "");
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
                        } else if ((ureaValue = Double.parseDouble(urea)) >= 6 && (ureaValue = Double.parseDouble(urea)) <= 25) {
                            showSuccessDialog("أنت بصحة جيدة ", "استمر على هذا النحو من المحافظة على صحتك ");
                            // Upload Data on Firebase
                            databaseReference.child("فحوصات وظائف الكلى").child("Urea").setValue(urea + "");
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
                            Toast.makeText(getActivity(), "لا توجد قيم مدرجة", Toast.LENGTH_SHORT).show();
                        } else if ((cholesterolValue = Double.parseDouble(cholesterol)) <= 200) {
                            showSuccessDialog("أنت بصحة جيدة ", "استمر على هذا النحو من المحافظة على صحتك " + cholesterolValue);
                            databaseReference.child("فحوصات الدهون").child("Cholesterol").setValue(cholesterol + "");
                        } else if ((cholesterolValue = Double.parseDouble(cholesterol)) > 200 && (cholesterolValue = Double.parseDouble(cholesterol)) <= 239) {
                            showWarningDialog("لا بأس استمر على الارشادات ", "هذا المؤشر ينبهك بالمحافظة على الصحة واتباع الإرشادات");
                            databaseReference.child("فحوصات الدهون").child("Cholesterol").setValue(cholesterol + "");
                        } else if ((cholesterolValue = Double.parseDouble(cholesterol)) >= 240) {
                            showErrorDialog("تحذير للمتابعة", " برجى استشارة طبيب على الفور او التوجه لاقرب مستشفى  ");
                            databaseReference.child("فحوصات الدهون").child("Cholesterol").setValue(cholesterol + "");
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
                checked_save_triglycerid = bottomSheetView.findViewById(R.id.checked_save_triglycerid);
                checked_save_triglycerid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        triglyceride = et_triglycerid.getText().toString();
                        if (triglyceride.isEmpty()) {
                            Toast.makeText(getActivity(), "لا توجد قيم مدرجة", Toast.LENGTH_SHORT).show();
                        } else if ((triglycerideValue = Double.parseDouble(triglyceride)) < 150) {
                            databaseReference.child("فحوصات الدهون").child("Triglyceride").setValue(triglyceride + "");
                            showSuccessDialog("أنت بصحة جيدة ", "استمر على هذا النحو من المحافظة على صحتك ");

                            //  200 - 499
                        } else if ((triglycerideValue = Double.parseDouble(triglyceride)) >= 200 && (triglycerideValue = Double.parseDouble(triglyceride)) <= 499) {
                            databaseReference.child("فحوصات الدهون").child("Triglyceride").setValue(triglyceride + "");
                            showWarningDialog("متابعة مع الطبيب ", "مؤشر خطير يجب اتباع الإرشادات ومراجعة طبيب ");


                        } else if ((triglycerideValue = Double.parseDouble(triglyceride)) > 500) {
                            databaseReference.child("فحوصات الدهون").child("Triglyceride").setValue(triglyceride + "");
                            showErrorDialog("تحذير", "مرفتع جدا يرجى مراجعة الطبيب عاجلا واتباع حمية غذائية قاسية  ");
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
                            Toast.makeText(getActivity(), "لا توجد قيم مدرجة", Toast.LENGTH_SHORT).show();

                        } else if ((ldlValue = Double.parseDouble(ldl)) >= 70 && (ldlValue = Double.parseDouble(ldl)) <= 129) {
                            databaseReference.child("فحوصات الدهون").child("IDL").setValue(ldl + "");
                            showSuccessDialog("أنت بصحة جيدة ", "استمر على هذا النحو من المحافظة على صحتك ");


                        } else if ((ldlValue = Double.parseDouble(ldl)) <= 70) {
                            databaseReference.child("فحوصات الدهون").child("IDL").setValue(ldl + "");
                            showWarningDialog("لا بأس استمر على الارشادات ", "هذا المؤشر ينبهك بالمحافظة على الصحة واتباع الإرشادات");


                        } else if ((ldlValue = Double.parseDouble(ldl)) > 130) {
                            databaseReference.child("فحوصات الدهون").child("IDL").setValue(ldl + "");
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
        examine_fats_hdl.setOnClickListener(v -> {
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
                        Toast.makeText(getActivity(), "لا توجد قيم مدرجة", Toast.LENGTH_SHORT).show();
                    } else if ((hdlValue = Double.parseDouble(hdl)) >= 60 && (hdlValue = Double.parseDouble(hdl)) <= 148) {
                        databaseReference.child("فحوصات الدهون").child("HDL").setValue(hdl + "");
                        showSuccessDialog("أنت بصحة جيدة ", "استمر على هذا النحو من المحافظة على صحتك ");

                    } else if ((hdlValue = Double.parseDouble(hdl)) >= 40 || (hdlValue = Double.parseDouble(hdl)) <= 59) {
                        showWarningDialog(" منخفظ  ", "يمكنك استشارة الطبيب عبر التطبيق");

                        databaseReference.child("فحوصات الدهون").child("HDL").setValue(hdl + "");

                    } else if ((hdlValue = Double.parseDouble(hdl)) < 39) {
                        showErrorDialog("منخفظ جدا ", " يرجى مراجعة الطبيب   ");

                        databaseReference.child("فحوصات الدهون").child("HDL").setValue(hdl + "");

                    } else {
                        // Upload Data on Firebase
                        bottomSheetDialog.dismiss();
                    }
                }
            });
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });

        return view;


    }

    //=================  Custom Dialog  ===============
    private void showSuccessDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_success_dialog, getActivity().findViewById(R.id.layoutDialogContainer));

        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(title);
        ((TextView) view.findViewById(R.id.textMessage)).setText(message);
        ((Button) view.findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.okay));
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_succsess);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonAction).setOnClickListener(v -> alertDialog.dismiss());
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showWarningDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_warning_dialog, getActivity().findViewById(R.id.layoutDialogContainer));

        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(title);
        ((TextView) view.findViewById(R.id.textMessage)).setText(message);
        ((Button) view.findViewById(R.id.buttonYes)).setText(getResources().getString(R.string.yes));
        ((Button) view.findViewById(R.id.buttonNo)).setText(getResources().getString(R.string.no));
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_warning);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonYes).setOnClickListener(v -> {
            alertDialog.dismiss();
            Toast.makeText(getActivity(), "الإرشادات", Toast.LENGTH_SHORT).show();
            Intent mainActivity = new Intent(getActivity(), Content.class);
            startActivity(mainActivity);

        });
        view.findViewById(R.id.buttonNo).setOnClickListener(v -> {
            alertDialog.dismiss();
            Toast.makeText(getActivity(), "Ok", Toast.LENGTH_SHORT).show();
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showErrorDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_error_dialog, getActivity().findViewById(R.id.layoutDialogContainer));
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(title);

        ((TextView) view.findViewById(R.id.textMessage)).setText(message);
        ((Button) view.findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.okay));
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_error);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonAction).setOnClickListener(v -> alertDialog.dismiss());
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
// Hallow this is Update
}