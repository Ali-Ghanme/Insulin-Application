package com.example.diabestes_care_app.Ui.Patient_all.Sections.Doses;

import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Alarms.AlarmReceiver;
import com.example.diabestes_care_app.Alarms.SliderAdapter;
import com.example.diabestes_care_app.Alarms.alarmChooserActivity;
import com.example.diabestes_care_app.Alarms.ringtonePlayingService;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Doses_Home extends Basic_Activity {


    com.example.diabestes_care_app.Alarms.AlarmSound sound = new com.example.diabestes_care_app.Alarms.AlarmSound(this);

    //Alarm variables
    long snoozeTime;
    AlarmManager alarmManager;
    Context context;
    Calendar calendar;
    FloatingActionButton alarmOn;
    Button alarmOff;
    Button alarmSnooze;
    PendingIntent pendingIntent;
    Intent alarmReceiverIntent;
    boolean alarmActive = false;
    TextView alarm_textView;
    Switch alarm_switch;
    RelativeLayout alarm_relativeLayout;
    TextView patient_name;
    ImageView profile_img;
    DatabaseReference myRef;
    String PatientUsername;
    View view1, view2;
    MediaPlayer timer_song;
    //binder
    ringtonePlayingService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doses_home);
        this.context = this;
        //initialize alarm ON/OFF buttons
        alarmOn = findViewById(R.id.alarm_button);
        alarmOff = findViewById(R.id.off_button);
        alarmSnooze = findViewById(R.id.snooze_button);
        profile_img = findViewById(R.id.FD_profile_img);
        patient_name = findViewById(R.id.Patient_Username);  // text is null
        view1 = findViewById(R.id.EP_view3_pC);
        view2 = findViewById(R.id.EP_view4_pC);

        //============================Get Patient Username===========================================
        SharedPreferences prefs = Doses_Home.this.getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);
        //==========================================================================================
        //initialize our alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //initialize our time picker
        calendar = Calendar.getInstance();
        //create an intent to the AlarmReceiver class
        alarmReceiverIntent = new Intent(this.context, AlarmReceiver.class);

        //==========================================================================================
        // View Page Configuration
        ViewPager mSlideViewPager = findViewById(R.id.slidePager);
        mSlideViewPager.setOffscreenPageLimit(2);
        SliderAdapter sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
        mSlideViewPager.setCurrentItem(sliderAdapter.getCount() - 2);

        //==========================================================================================
        // Save Alarm tons on  shared preferences
        SharedPreferences preferences = getSharedPreferences("alarm_tune", Context.MODE_PRIVATE);
        int prefValue = preferences.getInt("tune", 0);
        final SharedPreferences.Editor editor = preferences.edit();
        if (prefValue == 0) {
            editor.putInt("tune", R.raw.down_stream);
            editor.apply();
        } else {
            editor.putInt("tune", prefValue);
            editor.apply();
            //keep previous preference
        }

        //==========================================================================================
        // Alarm Switch on or Off Configuration
        alarm_switch = findViewById(R.id.alarm_switch);
        if (alarm_switch != null) {
            alarm_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        alarmReceiverIntent.putExtra("extra", "alarm off");
                        //cancel the alarm
                        alarmManager.cancel(pendingIntent);
                        //stop the ringtone
                        //sends a message to stop directly to the ringtonePlayingService
                        sendBroadcast(alarmReceiverIntent);
                    }
                }
            });
        }

        //==========================================================================================
        //new thread to update clock The Welcome Screen show the time and date and (Good Morning or Good Evening)
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //time ,date , welcome text initialization
//                                TextView tDate = findViewById(R.id.date);
                                TextView tTime = findViewById(R.id.time);
                                TextView tGreeting = findViewById(R.id.greeting);
//                                TextView tampm = findViewById(R.id.ampm);

                                long date = System.currentTimeMillis(); // get current time
                                SimpleDateFormat sdfDate = new SimpleDateFormat("MMM dd yyyy ", Locale.UK);
                                SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm", Locale.UK);
                                SimpleDateFormat sdfGreetingTime = new SimpleDateFormat("HH", Locale.UK);

                                String dateString = sdfDate.format(date);
                                String timeString = sdfTime.format(date);
                                String greetingTimeString = sdfGreetingTime.format(date);

                                //this is in a try block since the reference returns null when on
                                //different pages
                                try {
//                                    tDate.setText(dateString);
                                    tTime.setText(timeString);
                                } catch (NullPointerException ignored) {
                                }
                                if (Integer.parseInt(greetingTimeString) < 12) {
                                    tGreeting.setText(R.string.good_mor);
//                                    tampm.setText(R.string.am);
                                } else if (Integer.parseInt(greetingTimeString) < 17) {
                                    tGreeting.setText(R.string.good_aft);
//                                    tampm.setText(R.string.pm);
                                } else {
                                    tGreeting.setText(R.string.good_ev);
//                                    tampm.setText(R.string.pm);
                                }
                            }
                        });
                    }
                } catch (InterruptedException ignored) {
                }
            }
        };
        t.start();

        //==========================================================================================
        //new thread to show buttons
        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //some code
                                if (mService.showButtons()) {
                                    Log.e("thread 2", "alarm works");
                                    try {
                                        alarmOff = findViewById(R.id.off_button);
                                        alarmSnooze = findViewById(R.id.snooze_button);
                                        alarmOff.setVisibility(View.VISIBLE);
                                        alarmSnooze.setVisibility(View.VISIBLE);
                                    } catch (Exception ignored) {
                                    }
                                }
                            }
                        });
                    }
                } catch (InterruptedException ignored) {
                }
            }
        };
        t2.start();

        //==============================Timer Code==================================================
        // set shared preferences again and put time song in a try block in cases where preferences
        // were not properly set before
        preferences = getSharedPreferences("alarm_tune", Context.MODE_PRIVATE);
        prefValue = preferences.getInt("tune", 0);
        if (prefValue == 0) {
            editor.putInt("tune", R.raw.down_stream);
            editor.apply();
            Log.e("inside if", "default song set");
        } else {
            editor.putInt("tune", prefValue);
            editor.apply();
            Log.e("inside else", "preferences song set");
            //keep previous preference
        }

        int timer_tune = preferences.getInt("tune", R.raw.down_stream);
        Log.e("preferences value", String.valueOf(timer_tune));
        Log.e("resources value", String.valueOf(R.raw.down_stream));
        try {
            timer_song = MediaPlayer.create(this, timer_tune);
        } catch (Exception e) {
            timer_song = MediaPlayer.create(this, R.raw.down_stream);
        }
    }

    //============================Store Alarm data on life cycle ==================================================================
    @Override
    protected void onStart() {
        super.onStart();
        // Bind to ringtonePlayingService
        Intent binder_intent = new Intent(this, ringtonePlayingService.class);
        bindService(binder_intent, mConnection, Context.BIND_AUTO_CREATE);
        //============================Show The Patient name + image=====================================
        myRef = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (Doses_Home.this == null) {
                    return;
                }
                try {
                    String image = snapshot.child("User_Profile_Image").child("Image").child("mImageUrI").getValue(String.class);
                    String name = snapshot.child("personal_info").child("name").getValue(String.class);
                    Glide.with(Doses_Home.this).load(image).into(profile_img);
                    patient_name.setText(name);
                    Log.d("TAG", name + "/" + image);
                } catch (Exception e) {
                    Log.e("TAG", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
        mBound = false;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("inside resume method", "resetting button visibility");

        if (timer_song.isPlaying()) {
            Log.e("inside resume method", "music is playing from timer");
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("inside restart method", "resetting button visibility");
    }

    //==============================Service Binding=================================================
    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ringtonePlayingService.LocalBinder binder = (ringtonePlayingService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    //==============================================================================================
    public void playSound(View view) {
        SharedPreferences preferences = getSharedPreferences("alarm_tune", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor editor = preferences.edit();

        sound.stopTune();
        sound.chooseTrack(preferences.getInt("tune", 0));
        sound.playTune();

    }

    //==============================================================================================
    public void playSound2(View view) {
        sound.stopTune();
        sound.chooseTrack(R.raw.new_dawn);
        sound.playTune();
    }

    //==============================================================================================
    public void enter(View view) {
        Intent gotoAlarmChooserActivity = new Intent();
        gotoAlarmChooserActivity.setClass(this, alarmChooserActivity.class);
        startActivity(gotoAlarmChooserActivity);

    }

    //==============================================================================================
    public void alarmToggle(View view) {
        alarm_textView = findViewById(R.id.alarm_textView);
        alarm_switch = findViewById(R.id.alarm_switch);
        alarm_relativeLayout = findViewById(R.id.alarm_relativeLayout);

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("UnspecifiedImmutableFlag")
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (timePicker.isShown()) {  //???
                    calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                    calendar.set(Calendar.MINUTE, selectedMinute);
                    Log.e("inside onTimeSet", "calendar updated");

                    if (selectedHour > 12) {
                        @SuppressLint("DefaultLocale") String alarm_text = (selectedHour - 12) + ":" + String.format("%02d", selectedMinute) + " PM";
                        alarm_textView.setText(alarm_text);
                    } else if (selectedHour == 0) {
                        @SuppressLint("DefaultLocale") String alarm_text = (selectedHour + 12) + ":" + String.format("%02d", selectedMinute) + " AM";
                        alarm_textView.setText(alarm_text);
                    } else {
                        @SuppressLint("DefaultLocale") String alarm_text = selectedHour + ":" + String.format("%02d", selectedMinute) + " AM";
                        alarm_textView.setText(alarm_text);
                    }
                    alarm_relativeLayout.setVisibility(View.VISIBLE);
                    alarm_switch.setVisibility(View.VISIBLE);
                    alarm_switch.setChecked(true);

                    snoozeTime = calendar.getTimeInMillis();

                    //put in extra string to say that you stopped the alarm
                    alarmReceiverIntent.putExtra("extra", "alarm on");

                    pendingIntent = PendingIntent.getBroadcast(Doses_Home.this, 0, alarmReceiverIntent, PendingIntent.FLAG_MUTABLE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
                alarmActive = true;
            }
        }, hour, minute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

        Log.e("Set the calendar", "pending intent comes next");
        if (alarmActive)
            Log.e("inside if statement", "alarmActive: true");

    }

    //==============================================================================================
    public void alarmToggleOff(View view) {

        Log.e("inside alarmToggleOff", "turning off the alarm");

        alarmReceiverIntent.putExtra("extra", "alarm off");

        //cancel the alarm
        alarmManager.cancel(pendingIntent);

        //stop the ringtone
        //sends a message to stop directly to the ringtonePlayingService
        sendBroadcast(alarmReceiverIntent);

        alarm_switch.setChecked(false);

        alarmOff = (Button) findViewById(R.id.off_button);
        alarmSnooze = (Button) findViewById(R.id.snooze_button);
        alarmOff.setVisibility(View.INVISIBLE);
        alarmSnooze.setVisibility(View.INVISIBLE);

    }

    //==============================================================================================
    @SuppressLint("UnspecifiedImmutableFlag")
    public void alarmSnooze(View view) {

        Log.e("inside alarmSnooze", "snoozing the alarm");

        alarmReceiverIntent.putExtra("extra", "alarm off");

        //cancel the alarm
        alarmManager.cancel(pendingIntent);

        //stop the ringtone
        //sends a message to stop directly to the ringtonePlayingService
        sendBroadcast(alarmReceiverIntent);

        //put in extra string to say that you stopped the alarm
        alarmReceiverIntent.putExtra("extra", "alarm on");

        pendingIntent = PendingIntent.getBroadcast(Doses_Home.this, 0, alarmReceiverIntent, PendingIntent.FLAG_MUTABLE);

        //get snooze length
        SharedPreferences preferences = getSharedPreferences("alarm_tune", Context.MODE_PRIVATE);
        int prefValue = preferences.getInt("snooze", 0);
        final SharedPreferences.Editor editor = preferences.edit();
        if (prefValue == 0) {
            editor.putInt("snooze", 1);
            editor.apply();

            //one minute snooze time
            snoozeTime += 60000;
        } else {
            editor.putInt("snooze", prefValue);
            editor.apply();

            snoozeTime += (long) prefValue * 60 * 1000;    //minutes*60seconds*1000milliseconds
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, snoozeTime, pendingIntent);
    }
}
