package com.example.diabestes_care_app.viewmodel.auth.sing_up;

import android.util.Patterns;

import androidx.lifecycle.ViewModel;

import com.example.diabestes_care_app.data.model.sing_up_p.PatientInfo;
import com.example.diabestes_care_app.data.repository.PatientRepository;
import com.google.android.gms.tasks.OnCompleteListener;

public class PatientSignUp2ViewModel extends ViewModel {

    private final PatientRepository repository = new PatientRepository();

    public void saveStep2Data(String username, PatientInfo info, OnCompleteListener<Void> listener) {
        repository.saveStep2Data(username, info, listener);
    }

    public boolean isAnyFieldEmpty(String... fields) {
        for (String field : fields) {
            if (field == null || field.isEmpty()) return true;
        }
        return false;
    }

    public boolean isValidEmail(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public boolean isValidPhone(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public void deletePatientNodeIfExists(String username) {
        repository.getReference().child(username).removeValue();
    }
}
