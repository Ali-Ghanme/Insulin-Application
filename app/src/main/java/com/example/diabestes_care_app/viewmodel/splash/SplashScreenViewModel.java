package com.example.diabestes_care_app.viewmodel.splash;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.data.model.Splash_Screen_Modal;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenViewModel extends AndroidViewModel {

    // قائمة تمثل الشاشات التقديمية (Splash Pages)
    private final MutableLiveData<List<Splash_Screen_Modal>> splashPages = new MutableLiveData<>();

    // مؤشر ما إذا كانت الصفحة الحالية هي الأخيرة
    private final MutableLiveData<Boolean> isLastPage = new MutableLiveData<>(false);

    // لتخزين واسترجاع تفضيلات المستخدم
    private final SharedPreferences prefs;

    public SplashScreenViewModel(@NonNull Application application) {
        super(application);
        // التهيئة على SharedPreferences
        prefs = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        // تحميل محتوى الشاشات التقديمية
        loadSplashPages();
    }

    // تعبئة قائمة الشاشات التقديمية
    private void loadSplashPages() {
        List<Splash_Screen_Modal> list = new ArrayList<>();
        list.add(new Splash_Screen_Modal("أنسولين", "هو تطبيق للرعاية ومتابعة مرضى السكري", R.drawable.ic_splash_1));
        list.add(new Splash_Screen_Modal("نخبة من الأطباء", "استشارة نخبة من الأطباء المختصين", R.drawable.ic_splash_2));
        list.add(new Splash_Screen_Modal("المحادثات الخاصة", "التواصل المستمر مع الأطباء", R.drawable.ic_splash_chat));
        list.add(new Splash_Screen_Modal("قسم صحي", "استشارات، تنبيهات، تقارير", R.drawable.ic_spalsh_report));
        list.add(new Splash_Screen_Modal("نظم جرعاتك", "منبه للتذكير بالعلاج", R.drawable.ic_splash_4));
        list.add(new Splash_Screen_Modal("تقارير", "التذكير بالفحوصات الدورية", R.drawable.ic_report));
        list.add(new Splash_Screen_Modal("عناية ذاتية", "قسم للتعليمات والأنظمة الصحية", R.drawable.ic_splash_instraction));
        splashPages.setValue(list); // تحديث القيم المرتبطة بالواجهة
    }

    public MutableLiveData<List<Splash_Screen_Modal>> getSplashPages() {
        return splashPages;
    }

    public MutableLiveData<Boolean> getIsLastPage() {
        return isLastPage;
    }

    // يُستدعى عند تغيير الصفحة لتحديث حالة "هل هذه آخر صفحة؟"
    public void onPageChanged(int position) {
        isLastPage.setValue(position == splashPages.getValue().size() - 1);
    }

    // حفظ تفضيل بأن المقدمة تم عرضها بالفعل
    public void saveIntroSeen() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.apply();
    }

    // التحقق مما إذا كانت المقدمة قد عُرضت سابقًا
    public boolean isIntroAlreadySeen() {
        return prefs.getBoolean("isIntroOpened", false);
    }
}
