package com.example.diabestes_care_app.viewmodel.auth.sing_up;

import androidx.lifecycle.ViewModel;

import com.example.diabestes_care_app.data.model.sing_up_p.PatientDiseaseInfo;
import com.example.diabestes_care_app.data.repository.PatientRepository;

public class PatientSignUp4ViewModel extends ViewModel {
    private final PatientRepository repository = new PatientRepository();

    public void saveDiseaseInfo(String username, PatientDiseaseInfo info, Runnable onSuccess, Runnable onFailure) {
        repository.saveDiseaseInfo(username, info, onSuccess, onFailure);
    }
}
