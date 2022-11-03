package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports.Exams;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.Content.Content;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Daily_Sugar extends Fragment {
    LinearLayout liner_show_one;
    DatabaseReference databaseReference_daily;
    TableLayout tableLayout;
    Button btn_checked_save_sugar;
    TextView tv_date;
    EditText et_number_sugar, btn_sheet_time_day_sugar;
    ListView listView;
    String PatientUsername, number_sugar, daysugar, currentDataTime;
    String[] time_day_suger = {"قبل النوم", "بعد العشاء", "قبل العشاء", "بعد الغداء ", "قبل الغداء", "بعد الإفطار", "قبل الإفطار"};
    Double sugarValue;
    String success = "success", error = "error", warning = "warning";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily__suger, container, false);

        //============================Get Patient Username===========================================
        SharedPreferences prefs = this.requireActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);

        //================================Define Variable===========================================
        tableLayout = view.findViewById(R.id.Repo_tab_layout_SR);
        liner_show_one = view.findViewById(R.id.liner_show_one);
        et_number_sugar = view.findViewById(R.id.et_number_suger);
        btn_sheet_time_day_sugar = view.findViewById(R.id.btn_sheet_time_day_suger);
        tv_date = view.findViewById(R.id.tv_date);
        btn_checked_save_sugar = view.findViewById(R.id.btn_checked_save_suger);
        databaseReference_daily = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername).child("Reports_info").child("فحص يومي").push();
        Calendar cal = Calendar.getInstance();

        //============================  View Time Zone by Text View in page My_Exams================
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        currentDataTime = sdf.format(cal.getTime());
        tv_date.setText(currentDataTime);

        //=============================== Check and save data from sugar Edit Text==================
        btn_checked_save_sugar.setOnClickListener(v -> {
            number_sugar = et_number_sugar.getText().toString();
            if (number_sugar.isEmpty()) {
                Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();
            } else if ((sugarValue = Double.parseDouble(number_sugar)) >= 200 || (sugarValue = Double.parseDouble(number_sugar)) <= 89) {

                showErrorDialog("تحذير السكر مرتفع", "يرجى مراجعة الطبيب على الفور");
                // Upload Data on Firebase
                databaseReference_daily.child("نسبة السكر في الدم").setValue(number_sugar);
                databaseReference_daily.child("فترة القياس").setValue(daysugar);
                databaseReference_daily.child("وقت القياس").setValue(currentDataTime);
                databaseReference_daily.child("حالة القياس").setValue(error);
                // Mohammed Siam
            } else if ((sugarValue = Double.parseDouble(number_sugar)) <= 140 && (sugarValue = Double.parseDouble(number_sugar)) >= 90) {

                showSuccessDialog();
                // Upload Data on Firebase
                databaseReference_daily.child("نسبة السكر في الدم").setValue(number_sugar);
                databaseReference_daily.child("فترة القياس").setValue(daysugar);
                databaseReference_daily.child("وقت القياس").setValue(currentDataTime);
                databaseReference_daily.child("حالة القياس").setValue(success);

            } else if ((sugarValue = Double.parseDouble(number_sugar)) > 140 && (sugarValue = Double.parseDouble(number_sugar)) <= 199) {
                showWarningDialog("يرجى إستشارة الطبيب وإتباع حمية غذائية قليلة السكريات ولا بأس بالقيام ببعض التمارين الرياضية");
                // Upload Data on Firebase
                databaseReference_daily.child("نسبة السكر في الدم").setValue(number_sugar);
                databaseReference_daily.child("فترة القياس").setValue(daysugar);
                databaseReference_daily.child("وقت القياس").setValue(currentDataTime);
                databaseReference_daily.child("حالة القياس").setValue(warning);
            } else {
                Toast.makeText(getActivity(), " خطا في البيانات المدخلة ", Toast.LENGTH_SHORT).show();
            }
        });
        //============================Button Sheet to View time Day list =================================
        btn_sheet_time_day_sugar.setOnClickListener(v -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                    requireActivity(), R.style.BottomSheetDialogTheme);
            View bottomSheetView = LayoutInflater.from(getActivity())
                    .inflate(R.layout.layout_bottom_sheet_main, (LinearLayout) requireActivity().findViewById(R.id.bottomSheetContier));
            listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.activity_listview, time_day_suger);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view1, position, id) -> {
                daysugar = listView.getAdapter().getItem(position).toString();
                btn_sheet_time_day_sugar.setText(daysugar);

                bottomSheetDialog.dismiss();
            });
            bottomSheetView.findViewById(R.id.City_bottom_listView);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });

        return view;
    }

    //======================================  Custom Dialog  =======================================
    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_success_dialog, (ConstraintLayout) requireActivity().findViewById(R.id.layoutDialogContainer));

        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("أنت بصحة جيدة");
        ((TextView) view.findViewById(R.id.textMessage)).setText("استمر على هذا النحو من المحافظة على صحتك ");
        ((Button) view.findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.okay));
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_succsess);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonAction).setOnClickListener(v -> alertDialog.dismiss());
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showWarningDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_warning_dialog, (ConstraintLayout) requireActivity().findViewById(R.id.layoutDialogContainer));

        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("لا بأس استمر على الارشادات ");
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
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    public void showErrorDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_error_dialog, (ConstraintLayout) requireActivity().findViewById(R.id.layoutDialogContainer));
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
}