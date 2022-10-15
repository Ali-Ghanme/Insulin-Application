package com.example.diabestes_care_app.Ui.Patient_all.Sections.Doses.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Doses.data.DatabaseHelper;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Doses.model.Alarm;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Doses.service.LoadAlarmsService;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class AddEditAlarmActivity extends Basic_Activity {

    public static final String ALARM_EXTRA = "alarm_extra";
    public static final String MODE_EXTRA = "mode_extra";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EDIT_ALARM, ADD_ALARM, UNKNOWN})
    @interface Mode {
    }

    public static final int EDIT_ALARM = 1;
    public static final int ADD_ALARM = 2;
    public static final int UNKNOWN = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        final Alarm alarm = getAlarm();

        if (getSupportFragmentManager().findFragmentById(R.id.edit_alarm_frag_container) == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.edit_alarm_frag_container, AddEditAlarmFragment.newInstance(alarm)).commit();
        }
    }

    private Alarm getAlarm() {
        switch (getMode()) {
            case EDIT_ALARM:
                return getIntent().getParcelableExtra(ALARM_EXTRA);
            case ADD_ALARM:
                final long id = DatabaseHelper.getInstance(this).addAlarm();
                LoadAlarmsService.launchLoadAlarmsService(this);
                return new Alarm(id);
            case UNKNOWN:
            default:
                throw new IllegalStateException("Mode supplied as intent extra for " +
                        AddEditAlarmActivity.class.getSimpleName() + " must match value in " +
                        Mode.class.getSimpleName());
        }
    }

    private @Mode
    int getMode() {
        final @Mode int mode = getIntent().getIntExtra(MODE_EXTRA, UNKNOWN);
        return mode;
    }

    public static Intent buildAddEditAlarmActivityIntent(Context context, @Mode int mode) {
        final Intent i = new Intent(context, AddEditAlarmActivity.class);
        i.putExtra(MODE_EXTRA, mode);
        return i;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
