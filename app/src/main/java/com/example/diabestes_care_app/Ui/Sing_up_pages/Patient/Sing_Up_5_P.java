package com.example.diabestes_care_app.Ui.Sing_up_pages.Patient;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.Models.Upload;
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

public class Sing_Up_5_P extends Basic_Activity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView mImageView;
    private DatabaseReference DB_Ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    private StorageReference Storage_Ref = FirebaseStorage.getInstance().getReference();

    Error error;
    StorageTask muploadTask;
    ProgressBar mProgressBa;
    Button btn_Upload;
    Uri mImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sign_up_5_p);
        //====================================Define Checkbox===============================
        btn_Upload = findViewById(R.id.Sp5_bt_Upload_P);
        mImageView = findViewById(R.id.Sp5_img_Select_P);
        mProgressBa = findViewById(R.id.Sp5_upPrg_bar_P);


        Storage_Ref = FirebaseStorage.getInstance().getReference("Patient");
        DB_Ref = FirebaseDatabase.getInstance().getReference("patient");

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileChooser();
            }
        });

        Intent intent_for_image = getIntent();
        String patient_userName = intent_for_image.getStringExtra("username4");

        btn_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (muploadTask != null && muploadTask.isInProgress()) {
                    Toast.makeText(Sing_Up_5_P.this, "Upload is progress", Toast.LENGTH_SHORT).show();
                } else {
                    if (mImageUri != null) {
                        StorageReference fileReference = Storage_Ref.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
                        muploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // This Handler handle with progress bar delay
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgressBa.setProgress(0);
                                    }
                                }, 500);

                                // I am with you my
                                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Upload upload = new Upload(uri.toString());
                                        String uploadId = DB_Ref.push().getKey();
                                        assert uploadId != null;

                                        DB_Ref.child(patient_userName).child("Image").child(uploadId).setValue(upload);
                                        Toast.makeText(Sing_Up_5_P.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Sing_Up_5_P.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.e("My_Error", error.getMessage());
                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                        double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                        mProgressBa.setProgress((int) progress);

                                        Intent intent = new Intent(Sing_Up_5_P.this, Sing_In.class);
                                        startActivity(intent);
                                    }
                                });
                    } else {
                        Toast.makeText(Sing_Up_5_P.this, "No File Selected", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void OpenFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

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