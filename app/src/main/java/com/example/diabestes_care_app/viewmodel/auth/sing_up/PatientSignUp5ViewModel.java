package com.example.diabestes_care_app.viewmodel.auth.sing_up;

import android.net.Uri;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.diabestes_care_app.data.repository.PatientRepository;

public class PatientSignUp5ViewModel extends ViewModel {

    private final PatientRepository repository;

    private final MutableLiveData<Integer> uploadProgress = new MutableLiveData<>();
    private final MutableLiveData<Boolean> uploadSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> uploadError = new MutableLiveData<>();

    //==================================== Constructor Initialize Repository ==============================
    public PatientSignUp5ViewModel() {
        repository = new PatientRepository();
    }

    //==================================== Getter For Progress LiveData ==============================
    public LiveData<Integer> getUploadProgress() {
        return uploadProgress;
    }

    //==================================== Getter For Upload Success LiveData ==============================
    public LiveData<Boolean> getUploadSuccess() {
        return uploadSuccess;
    }

    //==================================== Getter For Upload Error Message ==============================
    public LiveData<String> getUploadError() {
        return uploadError;
    }

    //==================================== Upload Image Method ==============================
    public void uploadImage(String patientUserName, Uri imageUri, String fileExtension) {
        repository.uploadPatientImage(patientUserName, imageUri, fileExtension,
                //==================================== On Progress Update ==============================
                snapshot -> {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    uploadProgress.postValue((int) progress);
                },
                //==================================== On Success Callback ==============================
                () -> uploadSuccess.postValue(true),
                //==================================== On Failure Callback ==============================
                e -> uploadError.postValue(e.getMessage())
        );
    }
}
