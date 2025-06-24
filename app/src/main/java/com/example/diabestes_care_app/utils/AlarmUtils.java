package com.example.diabestes_care_app.utils;

import static com.example.diabestes_care_app.data.local.AlarmDatabaseHelper.COL_FRI;
import static com.example.diabestes_care_app.data.local.AlarmDatabaseHelper.COL_IS_ENABLED;
import static com.example.diabestes_care_app.data.local.AlarmDatabaseHelper.COL_LABEL;
import static com.example.diabestes_care_app.data.local.AlarmDatabaseHelper.COL_MON;
import static com.example.diabestes_care_app.data.local.AlarmDatabaseHelper.COL_SAT;
import static com.example.diabestes_care_app.data.local.AlarmDatabaseHelper.COL_SUN;
import static com.example.diabestes_care_app.data.local.AlarmDatabaseHelper.COL_THURS;
import static com.example.diabestes_care_app.data.local.AlarmDatabaseHelper.COL_TIME;
import static com.example.diabestes_care_app.data.local.AlarmDatabaseHelper.COL_TUES;
import static com.example.diabestes_care_app.data.local.AlarmDatabaseHelper.COL_WED;
import static com.example.diabestes_care_app.data.local.AlarmDatabaseHelper._ID;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.util.SparseBooleanArray;

import androidx.core.app.ActivityCompat;

import com.example.diabestes_care_app.data.model.Alarm_Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public final class AlarmUtils {

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("h:mm", Locale.getDefault());
    private static final SimpleDateFormat AM_PM_FORMAT = new SimpleDateFormat("a", Locale.getDefault());

    private static final int REQUEST_ALARM = 1;
    private static final String[] PERMISSIONS_ALARM = {
            Manifest.permission.VIBRATE
    };

    private AlarmUtils() { throw new AssertionError(); }

    public static void checkAlarmPermissions(Activity activity) {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        final int permission = ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.VIBRATE
        );

        if(permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_ALARM,
                    REQUEST_ALARM
            );
        }

    }

    public static ContentValues toContentValues(Alarm_Model alarm) {

        final ContentValues cv = new ContentValues(10);

        cv.put(COL_TIME, alarm.getTime());
        cv.put(COL_LABEL, alarm.getLabel());

        final SparseBooleanArray days = alarm.getDays();
        cv.put(COL_MON, days.get(Alarm_Model.MON) ? 1 : 0);
        cv.put(COL_TUES, days.get(Alarm_Model.TUES) ? 1 : 0);
        cv.put(COL_WED, days.get(Alarm_Model.WED) ? 1 : 0);
        cv.put(COL_THURS, days.get(Alarm_Model.THURS) ? 1 : 0);
        cv.put(COL_FRI, days.get(Alarm_Model.FRI) ? 1 : 0);
        cv.put(COL_SAT, days.get(Alarm_Model.SAT) ? 1 : 0);
        cv.put(COL_SUN, days.get(Alarm_Model.SUN) ? 1 : 0);

        cv.put(COL_IS_ENABLED, alarm.isEnabled());

        return cv;

    }

    public static ArrayList<Alarm_Model> buildAlarmList(Cursor c) {

        if (c == null) return new ArrayList<>();

        final int size = c.getCount();

        final ArrayList<Alarm_Model> alarms = new ArrayList<>(size);

        if (c.moveToFirst()){
            do {

                @SuppressLint("Range") final long id = c.getLong(c.getColumnIndex(_ID));
                @SuppressLint("Range") final long time = c.getLong(c.getColumnIndex(COL_TIME));
                @SuppressLint("Range") final String label = c.getString(c.getColumnIndex(COL_LABEL));
                @SuppressLint("Range") final boolean mon = c.getInt(c.getColumnIndex(COL_MON)) == 1;
                @SuppressLint("Range") final boolean tues = c.getInt(c.getColumnIndex(COL_TUES)) == 1;
                @SuppressLint("Range") final boolean wed = c.getInt(c.getColumnIndex(COL_WED)) == 1;
                @SuppressLint("Range") final boolean thurs = c.getInt(c.getColumnIndex(COL_THURS)) == 1;
                @SuppressLint("Range") final boolean fri = c.getInt(c.getColumnIndex(COL_FRI)) == 1;
                @SuppressLint("Range") final boolean sat = c.getInt(c.getColumnIndex(COL_SAT)) == 1;
                @SuppressLint("Range") final boolean sun = c.getInt(c.getColumnIndex(COL_SUN)) == 1;
                @SuppressLint("Range") final boolean isEnabled = c.getInt(c.getColumnIndex(COL_IS_ENABLED)) == 1;

                final Alarm_Model alarm = new Alarm_Model(id, time, label);
                alarm.setDay(Alarm_Model.MON, mon);
                alarm.setDay(Alarm_Model.TUES, tues);
                alarm.setDay(Alarm_Model.WED, wed);
                alarm.setDay(Alarm_Model.THURS, thurs);
                alarm.setDay(Alarm_Model.FRI, fri);
                alarm.setDay(Alarm_Model.SAT, sat);
                alarm.setDay(Alarm_Model.SUN, sun);

                alarm.setIsEnabled(isEnabled);

                alarms.add(alarm);

            } while (c.moveToNext());
        }

        return alarms;

    }

    public static String getReadableTime(long time) {
        return TIME_FORMAT.format(time);
    }


    public static String getAmPm(long time) {
        return AM_PM_FORMAT.format(time);
    }

    public static boolean isAlarmActive(Alarm_Model alarm) {

        final SparseBooleanArray days = alarm.getDays();

        boolean isActive = false;
        int count = 0;

        while (count < days.size() && !isActive) {
            isActive = days.valueAt(count);
            count++;
        }
        return isActive;
    }

    public static String getActiveDaysAsString(Alarm_Model alarm) {

        StringBuilder builder = new StringBuilder("Active Days: ");

        if(alarm.getDay(Alarm_Model.MON)) builder.append("Monday, ");
        if(alarm.getDay(Alarm_Model.TUES)) builder.append("Tuesday, ");
        if(alarm.getDay(Alarm_Model.WED)) builder.append("Wednesday, ");
        if(alarm.getDay(Alarm_Model.THURS)) builder.append("Thursday, ");
        if(alarm.getDay(Alarm_Model.FRI)) builder.append("Friday, ");
        if(alarm.getDay(Alarm_Model.SAT)) builder.append("Saturday, ");
        if(alarm.getDay(Alarm_Model.SUN)) builder.append("Sunday.");

        if(builder.substring(builder.length()-2).equals(", ")) {
            builder.replace(builder.length()-2,builder.length(),".");
        }
        return builder.toString();

    }

}
