package com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.Models.Upload_Model;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Sing_In.Sing_In;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Sing_up_5_D extends Basic_Activity {
    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView mImageView;
    private DatabaseReference DB_Ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    private StorageReference Storage_Ref = FirebaseStorage.getInstance().getReference();

    Error mError;
    StorageTask mUploadTask;
    ProgressBar mProgress;
    Button btn_Upload;
    Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sing_up5_d);

        //====================================Define================================================
        btn_Upload = findViewById(R.id.Sp5_bt_Upload_D);
        mImageView = findViewById(R.id.Sp5_imageView_D);
        mProgress = findViewById(R.id.Sp5_upPrg_bar_D);

        Storage_Ref = FirebaseStorage.getInstance().getReference("Doctor");
        DB_Ref = FirebaseDatabase.getInstance().getReference("doctor");

        //====================================Pick Up Image=========================================
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileChooser(PICK_IMAGE_REQUEST);
            }
        });

        //==========================Take The Username From The Previous Page========================
        Intent intent_for_image = getIntent();
        String patient_userName = intent_for_image.getStringExtra("username4");

        //====================================Upload Image to Storage Firebase======================
        btn_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(Sing_up_5_D.this, "Upload is progress", Toast.LENGTH_SHORT).show();
                } else {
                    if (mImageUri != null) {
                        StorageReference fileReference = Storage_Ref.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
                        mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // This Handler handle with progress bar delay
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgress.setProgress(0);
                                    }
                                }, 500);

                                // I am with you my
                                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Upload_Model uploadModel = new Upload_Model(uri.toString());
                                        DB_Ref.child(patient_userName).child("personal_info").child("Image").setValue(uploadModel);
                                        Toast.makeText(Sing_up_5_D.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Sing_up_5_D.this, Sing_In.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Sing_up_5_D.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.e("My_Error", mError.getMessage());
                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                        double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                        mProgress.setProgress((int) progress);
                                    }
                                });
                    } else {
                        Toast.makeText(Sing_up_5_D.this, "No File Selected", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //====================================Pick Up Image=========================================
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }
}