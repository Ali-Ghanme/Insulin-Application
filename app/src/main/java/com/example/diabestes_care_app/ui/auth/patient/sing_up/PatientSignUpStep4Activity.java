package com.example.diabestes_care_app.ui.auth.patient.sing_up;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.diabestes_care_app.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.data.model.sing_up_p.PatientDiseaseInfo;
import com.example.diabestes_care_app.viewmodel.auth.sing_up.PatientSignUp4ViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PatientSignUpStep4Activity extends Basic_Activity {

    // ViewModel instance
    private PatientSignUp4ViewModel viewModel;

    // UI components
    private Button btnNext;
    private EditText mDateInjury, mCause, others;
    private CheckBox cOther;

    // Username passed from previous step
    private String patientUserName;

    // Date picker calendar instance
    private final Calendar myCalendar = Calendar.getInstance();

    // Static data for selection
    private final String[] cause = {"Family History", "Age", "Weight"};
    private final String[] otherIllnesses = {"Respiratory Diseases", "Blood Pressure", "Heart Diseases"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.p_screen_sign_up_4);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(PatientSignUp4ViewModel.class);

        // Bind views
        initViews();

        // Get username from previous screen
        patientUserName = getIntent().getStringExtra("username3");

        // Show cause bottom sheet
        mCause.setOnClickListener(v -> showBottomSheet(cause, mCause));

        // Show other illnesses bottom sheet
        others.setOnClickListener(v -> showBottomSheet(otherIllnesses, others));

        // Enable/disable "others" field based on checkbox
        cOther.setOnCheckedChangeListener((buttonView, isChecked) -> others.setFocusable(isChecked));

        // Save data on button click
        btnNext.setOnClickListener(v -> saveData());

        // Setup date picker
        setupDatePicker();
    }

    // Bind layout views to variables
    private void initViews() {
        btnNext = findViewById(R.id.Sp4_bt_next_P);
        mDateInjury = findViewById(R.id.Sp4_et_enjerdDate_P);
        mCause = findViewById(R.id.Sp4_et_cause_P);
        others = findViewById(R.id.SP4_et_others_P);
        cOther = findViewById(R.id.Sp4_diabetis_others_P);
    }

    // Show bottom sheet list dialog
    private void showBottomSheet(String[] items, EditText target) {
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.p_layout_bottom_sheet_main,
                findViewById(R.id.bottomSheetContier));
        ListView listView = view.findViewById(R.id.City_bottom_listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_listview, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            target.setText(items[position]);
            dialog.dismiss();
        });

        dialog.setContentView(view);
        dialog.show();
    }

    // Save the entered data via ViewModel
    private void saveData() {
        // Collect form data into model object
        PatientDiseaseInfo info = new PatientDiseaseInfo(
                mDateInjury.getText().toString(),
                mCause.getText().toString(),
                others.getText().toString(),
                cOther.isChecked()
        );

        // Save using ViewModel
        viewModel.saveDiseaseInfo(patientUserName, info,
                () -> {
                    Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, PatientSignUpStep5Activity.class)
                            .putExtra("username4", patientUserName));
                    finish();
                },
                () -> Toast.makeText(this, "Failed to save", Toast.LENGTH_SHORT).show()
        );
    }

    // Setup date picker dialog
    private void setupDatePicker() {
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
        };

        mDateInjury.setOnClickListener(v -> new DatePickerDialog(this, date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    // Format and display selected date
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        mDateInjury.setText(sdf.format(myCalendar.getTime()));
    }

    // Handle back button to clean up Firebase temp data
    @Override
    public void onBackPressed() {
        exitHappen(this, FirebaseDatabase.getInstance().getReference("patient"), patientUserName);
    }
}
