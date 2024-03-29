package com.example.diabestes_care_app.Ui.Patient_all.Sections.Doses.ui;

import static android.content.Context.VIBRATOR_SERVICE;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;

public final class AlarmLandingPageFragment extends Fragment implements SensorEventListener {
    private long lastTime;
    private float lastX;
    private float lastY;
    private float lastZ;
    private int count;


    static final int SHAKE_THRESHOLD = 100;
    public static int DATA_X = SensorManager.DATA_X;
    public static int DATA_Y = SensorManager.DATA_Y;
    public static int DATA_Z = SensorManager.DATA_Z;
    public SensorManager sensorManager;
    public Sensor accelerormeterSensor;
    private PowerManager pm;
    private PowerManager.WakeLock wl;
    private Vibrator vibrator;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_alarm_landing_page, container, false);
        vibrator = (Vibrator) requireActivity().getSystemService(VIBRATOR_SERVICE);
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        final Button launchMainActivityBtn = v.findViewById(R.id.load_main_activity_btn);
        final Button dismiss = v.findViewById(R.id.dismiss_btn);

        launchMainActivityBtn.setOnClickListener(v12 -> {
            startActivity(new Intent(getContext(), MainActivity.class));
            vibrator.cancel();
            requireActivity().finish();
        });

        dismiss.setOnClickListener(v1 -> {
            vibrator.cancel();
            requireActivity().finish();
        });

        pm = (PowerManager) requireActivity().getSystemService(Context.POWER_SERVICE);
        startvibe();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor,
                    SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 100) {
                lastTime = currentTime;
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];
                float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;
                if (speed > SHAKE_THRESHOLD) {
                    lastTime = currentTime;
                    count++;//for shake test
                    if (count == 10) {
                        vibrator.cancel();
                        requireActivity().finish();
                    }
                }
                lastX = event.values[DATA_X];
                lastY = event.values[DATA_Y];
                lastZ = event.values[DATA_Z];
            }
        }
    }

    public void startvibe() {
        vibrator.vibrate(new long[]{100, 1000, 100, 500, 100, 1000}, 0);
    }
}