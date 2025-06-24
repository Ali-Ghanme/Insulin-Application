package com.example.diabestes_care_app.ui.patient.health.alram.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.data.local.AlarmDatabaseHelper;
import com.example.diabestes_care_app.data.model.Alarm_Model;
import com.example.diabestes_care_app.receiver.AlarmReceiver;
import com.example.diabestes_care_app.services.LoadAlarmsService;
import com.example.diabestes_care_app.ui.patient.health.alram.screens.AddEditAlarmActivity;
import com.example.diabestes_care_app.utils.ViewUtils;

import java.util.Calendar;

public final class AddEditAlarmFragment extends Fragment {

    private TimePicker mTimePicker;
    private EditText mLabel;
    private CheckBox mMon, mTues, mWed, mThurs, mFri, mSat, mSun;

    public static Fragment newInstance(Alarm_Model alarm) {
        Bundle args = new Bundle();
        args.putParcelable(AddEditAlarmActivity.ALARM_EXTRA, alarm);
        AddEditAlarmFragment fragment = new AddEditAlarmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.p_fragment_add_edit_alarm, container, false);
        setHasOptionsMenu(true);
        final Alarm_Model alarm = getAlarm();
        mTimePicker = v.findViewById(R.id.edit_alarm_time_picker);
        ViewUtils.setTimePickerTime(mTimePicker, alarm.getTime());
        mLabel = v.findViewById(R.id.edit_alarm_label);
        mLabel.setText(alarm.getLabel());
        mMon = v.findViewById(R.id.edit_alarm_mon);
        mTues = v.findViewById(R.id.edit_alarm_tues);
        mWed = v.findViewById(R.id.edit_alarm_wed);
        mThurs = v.findViewById(R.id.edit_alarm_thurs);
        mFri = v.findViewById(R.id.edit_alarm_fri);
        mSat = v.findViewById(R.id.edit_alarm_sat);
        mSun = v.findViewById(R.id.edit_alarm_sun);
        Button add = v.findViewById(R.id.Add);
        Button del = v.findViewById(R.id.Del);

        add.setOnClickListener(v1 -> {
            save();
        });

        del.setOnClickListener(v12 -> delete());
                setDayCheckboxes(alarm);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_alarm_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                save();
                break;
            case R.id.action_delete:
                delete();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private Alarm_Model getAlarm() {
        assert getArguments() != null;
        return getArguments().getParcelable(AddEditAlarmActivity.ALARM_EXTRA);
    }

    private void setDayCheckboxes(Alarm_Model alarm) {
        mMon.setChecked(alarm.getDay(Alarm_Model.MON));
        mTues.setChecked(alarm.getDay(Alarm_Model.TUES));
        mWed.setChecked(alarm.getDay(Alarm_Model.WED));
        mThurs.setChecked(alarm.getDay(Alarm_Model.THURS));
        mFri.setChecked(alarm.getDay(Alarm_Model.FRI));
        mSat.setChecked(alarm.getDay(Alarm_Model.SAT));
        mSun.setChecked(alarm.getDay(Alarm_Model.SUN));
    }

    private void save() {
        final Alarm_Model alarm = getAlarm();
        final Calendar time = Calendar.getInstance();
        time.set(Calendar.MINUTE, ViewUtils.getTimePickerMinute(mTimePicker));
        time.set(Calendar.HOUR_OF_DAY, ViewUtils.getTimePickerHour(mTimePicker));
        alarm.setTime(time.getTimeInMillis());
        alarm.setLabel(mLabel.getText().toString());
        alarm.setDay(Alarm_Model.MON, mMon.isChecked());
        alarm.setDay(Alarm_Model.TUES, mTues.isChecked());
        alarm.setDay(Alarm_Model.WED, mWed.isChecked());
        alarm.setDay(Alarm_Model.THURS, mThurs.isChecked());
        alarm.setDay(Alarm_Model.FRI, mFri.isChecked());
        alarm.setDay(Alarm_Model.SAT, mSat.isChecked());
        alarm.setDay(Alarm_Model.SUN, mSun.isChecked());

        final int rowsUpdated = AlarmDatabaseHelper.getInstance(getContext()).updateAlarm(alarm);
        final int messageId = (rowsUpdated == 1) ? R.string.update_complete : R.string.update_failed;

        Toast.makeText(getContext(), messageId, Toast.LENGTH_SHORT).show();

        AlarmReceiver.setReminderAlarm(getContext(), alarm);

        requireActivity().finish();

    }

    private void delete() {
        final Alarm_Model alarm = getAlarm();
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(requireContext(), R.style.DeleteAlarmDialogTheme);
        builder.setTitle(R.string.delete_dialog_title);
        builder.setMessage(R.string.delete_dialog_content);
        builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
            //Cancel any pending notifications for this alarm
            AlarmReceiver.cancelReminderAlarm(getContext(), alarm);

            final int rowsDeleted = AlarmDatabaseHelper.getInstance(getContext()).deleteAlarm(alarm);
            int messageId;
            if (rowsDeleted == 1) {
                messageId = R.string.delete_complete;
                Toast.makeText(getContext(), messageId, Toast.LENGTH_SHORT).show();
                LoadAlarmsService.launchLoadAlarmsService(getContext());
                requireActivity().finish();
            } else {
                messageId = R.string.delete_failed;
                Toast.makeText(getContext(), messageId, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.no, null);
        builder.show();
    }
}
