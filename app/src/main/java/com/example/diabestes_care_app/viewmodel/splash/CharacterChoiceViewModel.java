package com.example.diabestes_care_app.viewmodel.splash;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * ViewModel لإدارة حالة اختيار نوع المستخدم (طبيب أو مريض)
 */
public class CharacterChoiceViewModel extends AndroidViewModel {

    private final MutableLiveData<String> navigationTarget = new MutableLiveData<>();

    public CharacterChoiceViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * يكشف هل يوجد مستخدم محفوظ (طبيب أو مريض) وينقل مباشرة إلى الشاشة المناسبة
     */
    public void checkSavedUser() {
        SharedPreferences preferences_P = getApplication().getSharedPreferences("checkbox_P", Application.MODE_PRIVATE);
        String checkbox_P = preferences_P.getString("remember_P", "");

        if ("true".equals(checkbox_P)) {
            navigationTarget.setValue("patient");
            return;
        }

        SharedPreferences preferences_D = getApplication().getSharedPreferences("checkbox_D", Application.MODE_PRIVATE);
        String checkbox_D = preferences_D.getString("remember_D", "");

        if ("true".equals(checkbox_D)) {
            navigationTarget.setValue("doctor");
        }
    }

    /**
     * LiveData تستخدم للواجهة لمراقبة وجهة التنقل
     */
    public LiveData<String> getNavigationTarget() {
        return navigationTarget;
    }
}
