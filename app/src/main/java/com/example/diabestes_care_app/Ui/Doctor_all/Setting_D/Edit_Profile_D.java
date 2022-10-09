package com.example.diabestes_care_app.Ui.Doctor_all.Setting_D;


import static com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D.Home_Fragment_D.MyPREFERENCES_D;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.Models.Upload_Model;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Doctor_all.Setting_D.Edit_Profile_Fragmane.Diabats_Info;
import com.example.diabestes_care_app.Ui.Doctor_all.Setting_D.Edit_Profile_Fragmane.Personal_Info;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Edit_Profile_D extends Basic_Activity {
    TabLayout tabLayout;
    ViewPager viewPager_subject;
    MainAdapter2 adapter;
    DatabaseReference myRef;
    String restoredText;
    TextView name;
    ImageView imageView, imageView2;

    public static final int PICK_IMAGE_REQUEST = 1;
    private DatabaseReference DB_Ref;
    private StorageReference Storage_Ref = FirebaseStorage.getInstance().getReference();

    Error mError;
    StorageTask mUploadTask;
    ProgressBar mProgress;
    Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_edit_profile_d);

        //============================Defines=======================================================
        tabLayout = findViewById(R.id.tab_layout_2_d);
        viewPager_subject = findViewById(R.id.viewpager_tab_2_d);
        name = findViewById(R.id.EP_doctor_name_d);
        imageView = findViewById(R.id.EP_Doctor_image_d);
        imageView2 = findViewById(R.id.EP_btn_back_d);
        mProgress = findViewById(R.id.Sp5_upPrg_bar_DP);

        Storage_Ref = FirebaseStorage.getInstance().getReference("Doctor");
        DB_Ref = FirebaseDatabase.getInstance().getReference("doctor");

        //===========================Actions========================================================
        imageView2.setOnClickListener(v -> onBackPressed());

        //============================username ShardPreference======================================
        SharedPreferences prefs = Edit_Profile_D.this.getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        restoredText = prefs.getString("TAG_NAME", null);

        imageView.setOnClickListener(v -> OpenFileChooser(PICK_IMAGE_REQUEST));

        //====================Adapter Configuration=================================================
        adapter = new MainAdapter2(getSupportFragmentManager());
        adapter.AddFragment(new Personal_Info(), "بيانات شخصية");
        adapter.AddFragment(new Diabats_Info(), "بيانات مهنية");

        //========================Set Adapter for the viewpager Configuration=======================
        viewPager_subject.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager_subject);
    }

    //=====================================Adapter method===========================================
    public static class MainAdapter2 extends FragmentPagerAdapter {
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();

        //Create Constructor
        public void AddFragment(Fragment fragment, String s) {
            fragmentArrayList.add(fragment);
            stringArrayList.add(s);
        }

        public MainAdapter2(@NonNull FragmentManager fm) {
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

    @Override
    public void onStart() {
        super.onStart();
        myRef = FirebaseDatabase.getInstance().getReference("doctor");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String PatientImage = snapshot.child(restoredText).child("User_Profile_Image").child("Image").child("mImageUrI").getValue(String.class);
                String PatientName = snapshot.child(restoredText).child("personal_info").child("name_ar").getValue(String.class);
                Log.d("TAG", name + "/" + PatientImage);
                Glide.with(getApplicationContext()).load(PatientImage).into(imageView);
                name.setText(PatientName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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
            Picasso.with(this).load(mImageUri).into(imageView);
        }
        uploadPhoto();
    }

    void uploadPhoto() {
        if (mUploadTask != null && mUploadTask.isInProgress()) {
            Toast.makeText(Edit_Profile_D.this, "Upload is progress", Toast.LENGTH_SHORT).show();
        } else {
            if (mImageUri != null) {
                StorageReference fileReference = Storage_Ref.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
                mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(taskSnapshot -> {
                            // This Handler handle with progress bar delay
                            Handler handler = new Handler();
                            handler.postDelayed(() -> mProgress.setProgress(0), 500);

                            // I am with you my
                            fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                                Upload_Model uploadModel = new Upload_Model(uri.toString());
                                DB_Ref.child(restoredText).child("User_Profile_Image").child("Image").setValue(uploadModel);
                                Toast.makeText(Edit_Profile_D.this, "تم تحديث الصورة الشخصية بنجاح", Toast.LENGTH_SHORT).show();
                            });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(Edit_Profile_D.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("My_Error", mError.getMessage());
                        })
                        .addOnProgressListener(snapshot -> {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            mProgress.setVisibility(View.VISIBLE);
                            mProgress.setProgress((int) progress);
                        });
            } else {
                Toast.makeText(Edit_Profile_D.this, "No File Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

}