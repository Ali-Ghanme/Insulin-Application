package com.example.diabestes_care_app.data.repository;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;

import com.example.diabestes_care_app.Basic_Activity;
import com.example.diabestes_care_app.data.model.Upload_Model;
import com.example.diabestes_care_app.data.model.sing_up_p.DiseaseInfo;
import com.example.diabestes_care_app.data.model.sing_up_p.PatientDiseaseInfo;
import com.example.diabestes_care_app.data.model.sing_up_p.PatientInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Map;

public class PatientRepository extends Basic_Activity {

    //==================================== Firebase References ==============================
    private final DatabaseReference databaseReference; // Reference to the 'patient' node in Realtime Database
    private final StorageReference storageRef;         // Reference to the 'Patient' folder in Firebase Storage

    //==================================== Constructor Initialize Firebase References ==============================
    public PatientRepository() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("patient");
        storageRef = FirebaseStorage.getInstance().getReference("Patient");
    }

    //==================================== Check If Username Already Exists ==============================
    // NOTE: The 'username' parameter is not used. It can be removed or logic improved to check specific username
    public void checkUsernameExists(String username, ValueEventListener listener) {
        // Currently fetches all patient data and passes it to the listener
        // Ideally should be: databaseReference.child(username).addListenerForSingleValueEvent(listener);
        databaseReference.child(username).addListenerForSingleValueEvent(listener);
    }


    //==================================== Save Step 1 Data ==============================
    public void saveStep1Data(String username, Map<String, Object> data) {
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            databaseReference.child(username).child(entry.getKey()).setValue(entry.getValue());
        }
    }

    //==================================== Save Step 2 Data ==============================
    public void saveStep2Data(String username, PatientInfo info, OnCompleteListener<Void> listener) {
        databaseReference.child(username).child("personal_info").child("Phone").setValue(info.phone);
        databaseReference.child(username).child("personal_info").child("Email").setValue(info.email);
        databaseReference.child(username).child("Password").setValue(info.password);
        databaseReference.child(username).child("CoPassword").setValue(info.confirmPassword);
        databaseReference.child(username).child("personal_info").child("City")
                .setValue(info.city).addOnCompleteListener(listener);
    }

    //==================================== Save Step 3 Disease Info ==============================
    public void saveStep3DiseaseInfo(String username, DiseaseInfo info, OnCompleteListener<Void> listener) {
        DatabaseReference userRef = databaseReference.child(username).child("disease_info");
        userRef.child("Diabetes Type").setValue(info.diabetesType);
        userRef.child("Diabetes Medics Type").setValue(info.diabetesMedicsType)
                .addOnCompleteListener(listener);
    }

    //==================================== Save Step 4 Full Disease Info ==============================
    public void saveDiseaseInfo(String username, PatientDiseaseInfo info, Runnable onSuccess, Runnable onFailure) {
        databaseReference.child(username).child("disease_info").setValue(info)
                .addOnSuccessListener(r -> onSuccess.run())
                .addOnFailureListener(e -> onFailure.run());
    }

    //==================================== Upload Patient Image To Firebase ==============================
    public void uploadPatientImage(String patientUserName, Uri imageUri, String fileExtension,
                                   OnProgressListener<UploadTask.TaskSnapshot> onProgress,
                                   Runnable onSuccess,
                                   OnFailureListener onFailure) {

        //==================================== Validate Inputs ==============================
        if (imageUri == null || patientUserName == null || patientUserName.isEmpty()) {
            if (onFailure != null) {
                onFailure.onFailure(new Exception("Invalid input parameters"));
            }
            return;
        }

        //==================================== Generate Unique File Name ==============================
        String fileName = System.currentTimeMillis() + "." + fileExtension;
        StorageReference fileRef = storageRef.child(fileName); // Path in Firebase Storage
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("patient"); // Realtime DB ref

        //==================================== Start File Upload ==============================
        UploadTask uploadTask = fileRef.putFile(imageUri);

        //==================================== Listen To Upload Progress ==============================
        uploadTask.addOnProgressListener(onProgress);

        //==================================== Listen To Upload Success ==============================
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                Upload_Model uploadModel = new Upload_Model(uri.toString());

                //==================================== Save Image URL To Realtime Database ==============================
                dbRef.child(patientUserName).child("User_Profile_Image").child("Image").setValue(uploadModel)
                        .addOnSuccessListener(aVoid -> {
                            if (onSuccess != null) onSuccess.run();
                        })
                        .addOnFailureListener(e -> {
                            if (onFailure != null) onFailure.onFailure(e);
                        });

            }).addOnFailureListener(e -> {
                if (onFailure != null) onFailure.onFailure(e);
            });
        });

        //==================================== Listen To Upload Failure ==============================
        uploadTask.addOnFailureListener(e -> {
            Log.e("PatientRepository", "Upload failed: " + e.getMessage());
            if (onFailure != null) onFailure.onFailure(e);
        });
    }

    //==================================== Get Patient Node Reference ==============================
    public DatabaseReference getReference() {
        return databaseReference;
    }

}
