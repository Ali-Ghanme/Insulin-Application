package com.example.diabestes_care_app.viewmodel.auth.sing_up;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.diabestes_care_app.data.repository.PatientRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Map;

public class PatientSignUp1ViewModel extends ViewModel {
    @SuppressLint("StaticFieldLeak")
    private final PatientRepository repository = new PatientRepository();
    private final MutableLiveData<Boolean> isUsernameTaken = new MutableLiveData<>(false);
    private final MutableLiveData<String> patientToken = new MutableLiveData<>();

    public LiveData<Boolean> getIsUsernameTaken() {
        return isUsernameTaken;
    }

    public LiveData<String> getPatientToken() {
        return patientToken;
    }

    public void fetchPatientToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        patientToken.setValue(task.getResult());
                    }
                });
    }

    public void checkUsername(String username) {
        repository.checkUsernameExists(username, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isUsernameTaken.setValue(snapshot.hasChild(username));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                isUsernameTaken.setValue(false);
            }
        });
    }

    public void saveStep1Data(String username, Map<String, Object> data) {
        repository.saveStep1Data(username, data);
    }
}
