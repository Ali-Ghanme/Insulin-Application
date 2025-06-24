package com.example.diabestes_care_app.ui.auth.patient.sing_up;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.example.diabestes_care_app.Basic_Activity;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.ui.auth.Sing_In;
import com.example.diabestes_care_app.viewmodel.auth.sing_up.PatientSignUp5ViewModel;
import com.squareup.picasso.Picasso;

public class PatientSignUpStep5Activity extends Basic_Activity {

    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    private String patientUserName;

    private PatientSignUp5ViewModel viewModel;

    //==================================== Activity Result Launcher To Pick Image ==============================
    private final ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    mImageUri = uri;
                    Picasso.with(this).load(mImageUri).into(mImageView);
                }
            }
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.p_screen_sign_up_5);

        //==================================== Initialize UI ==============================
        mImageView = findViewById(R.id.Sp5_img_Select_P);
        mProgressBar = findViewById(R.id.Sp5_upPrg_bar_P);
        Button btnUpload = findViewById(R.id.Sp5_bt_Upload_P);

        //==================================== Get Username From Intent ==============================
        patientUserName = getIntent().getStringExtra("username4");

        //==================================== Initialize ViewModel ==============================
        viewModel = new ViewModelProvider(this).get(PatientSignUp5ViewModel.class);

        //==================================== Observe Upload Progress ==============================
        viewModel.getUploadProgress().observe(this, progress -> mProgressBar.setProgress(progress));

        //==================================== Observe Upload Success ==============================
        viewModel.getUploadSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Sing_In.class));
                finish();
            }
        });

        //==================================== Observe Upload Error ==============================
        viewModel.getUploadError().observe(this, errorMsg -> {
            if (errorMsg != null && !errorMsg.isEmpty()) {
                Toast.makeText(this, "Upload failed: " + errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

        //==================================== Set Click Listener To Open Image Picker ==============================
        mImageView.setOnClickListener(v -> openFileChooser());

        //==================================== Set Click Listener To Upload Image ==============================
        btnUpload.setOnClickListener(v -> {
            if (mImageUri == null) {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
                return;
            }
            String extension = getFileExtension(mImageUri);
            viewModel.uploadImage(patientUserName, mImageUri, extension);
        });
    }

    //==================================== Open File Chooser ==============================
    private void openFileChooser() {
        pickImageLauncher.launch("image/*");
    }

    //==================================== Get File Extension From URI ==============================
    public String getFileExtension(Uri uri) {
        String type = getContentResolver().getType(uri);
        if (type == null) return "";
        return type.substring(type.indexOf('/') + 1);
    }

    //==================================== Override Back Press To Handle Exit ==============================
    @Override
    public void onBackPressed() {
        exitHappen(this, null, patientUserName);
    }
}
