package com.example.diabestes_care_app.viewmodel.auth.sing_up;

import androidx.lifecycle.ViewModel;

import com.example.diabestes_care_app.data.model.sing_up_p.DiseaseInfo;
import com.example.diabestes_care_app.data.repository.PatientRepository;
import com.google.android.gms.tasks.OnCompleteListener;

public class PatientSignUp3ViewModel extends ViewModel {
    private final PatientRepository repository = new PatientRepository();
    private String diabetesType = "";
    private final StringBuilder medicsBuilder = new StringBuilder();

    public void setDiabetesType(String type) {
        diabetesType = type;
    }

    public void addMedicType(String type) {
        if (!medicsBuilder.toString().contains(type)) {
            if (medicsBuilder.length() > 0) medicsBuilder.append(",");
            medicsBuilder.append(type);
        }
    }

    public void saveDiseaseInfo(String username, OnCompleteListener<Void> listener) {
        DiseaseInfo info = new DiseaseInfo(diabetesType, medicsBuilder.toString());
        repository.saveStep3DiseaseInfo(username, info, listener);
    }

    public boolean isValid() {
        return !diabetesType.isEmpty();
    }
}