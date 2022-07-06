package com.example.diabestes_care_app.Alarms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
//import androidx.viewpager.widget.ViewPager;

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
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.diabestes_care_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Alarm extends AppCompatActivity {

//    private ActivityMainBinding binding;

    //variable to sample alarm music
    com.example.diabestes_care_app.Alarms.AlarmSound sound = new com.example.diabestes_care_app.Alarms.AlarmSound(this);

    //Alarm variables
    long snoozeTime;
    AlarmManager alarmManager;
    TimePicker alarmTimePicker;
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

    //ui
    private TextView[] mdots;
    private Button button;

    //countdown timer
//    private static long START_TIME_IN_MILLIS = 60000; //1 minute
//    private TextView mTextViewCountDown;
//    private FloatingActionButton mButtonStartPause;
//    private FloatingActionButton mButtonReset;
//    private FloatingActionButton mButtonStartPause2;
//    private CountDownTimer mCountDownTimer;
//    private boolean mTimerRunning;
//    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    MediaPlayer timer_song;

    //binder
    ringtonePlayingService mService;
    boolean mBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        this.context = this;


        //==========================================================================================
        //toolbars initialization
//        Toolbar myToolbar = findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
//        myToolbar.setTitleTextColor(Color.WHITE);

        //==========================================================================================
        //initialize alarm ON/OFF buttons
        alarmOn = findViewById(R.id.alarm_button);
        alarmOff = findViewById(R.id.off_button);
        alarmSnooze = findViewById(R.id.snooze_button);
//        button = findViewById(R.id.btn);



        //==========================================================================================
        //initialize our alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //initialize our time picker
        calendar = Calendar.getInstance();
        //create an intent to the AlarmReceiver class
        alarmReceiverIntent = new Intent(this.context, com.example.diabestes_care_app.Alarms.AlarmReceiver.class);

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
        //Ui: dots layout  . . .
        LinearLayout mDotLayout = (LinearLayout) findViewById(R.id.mDotLayout);
        mdots = new TextView[2];
        for (int i = 0; i < mdots.length; i++) {
            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.white_smoke));

            mDotLayout.addView(mdots[i]);
        }
        addDotsIndicator(1);
        mSlideViewPager.addOnPageChangeListener(viewListener);

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
                                TextView tDate = findViewById(R.id.date);
                                TextView tTime = findViewById(R.id.time);
                                TextView tGreeting = findViewById(R.id.greeting);
                                TextView tampm = findViewById(R.id.ampm);

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
                                    tDate.setText(dateString);
                                    tTime.setText(timeString);
                                } catch (NullPointerException ignored) {
                                }
                                if (Integer.parseInt(greetingTimeString) < 12) {
                                    tGreeting.setText(R.string.good_mor);
                                    tampm.setText(R.string.am);
                                } else if (Integer.parseInt(greetingTimeString) < 17) {
                                    tGreeting.setText(R.string.good_aft);
                                    tampm.setText(R.string.pm);
                                } else {
                                    tGreeting.setText(R.string.good_ev);
                                    tampm.setText(R.string.pm);
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
        //countdown timer
//        mTextViewCountDown = findViewById(R.id.text_view_countdown);
//        mButtonStartPause = findViewById(R.id.button_start_pause);
//        mButtonStartPause2 = findViewById(R.id.button_start_pause2);
//        mButtonReset = findViewById(R.id.button_reset);

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

        //timer buttons
//        mButtonStartPause = findViewById(R.id.button_start_pause);
//        mButtonStartPause2 = findViewById(R.id.button_start_pause2);
//        mButtonReset = findViewById(R.id.button_reset);
    }

    //============================Store Alarm data on life cycle ==================================================================
    @Override
    protected void onStart() {
        super.onStart();
        // Bind to ringtonePlayingService
        Intent binder_intent = new Intent(this, ringtonePlayingService.class);
        bindService(binder_intent, mConnection, Context.BIND_AUTO_CREATE);
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

//        mButtonStartPause = findViewById(R.id.button_start_pause);
//        mButtonStartPause2 = findViewById(R.id.button_start_pause2);
//        mButtonReset = findViewById(R.id.button_reset);

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

                    pendingIntent = PendingIntent.getBroadcast(Alarm.this, 0, alarmReceiverIntent, PendingIntent.FLAG_IMMUTABLE);
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

        pendingIntent = PendingIntent.getBroadcast(Alarm.this, 0, alarmReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

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

    //==============================================================================================


    //==============================================================================================
    public void addDotsIndicator(int position) {


        for (TextView mdot : mdots) {
            mdot.setTextColor(getResources().getColor(R.color.white_smoke));
        }

        if (mdots.length > 0) {
            mdots[position].setTextColor(getResources().getColor(R.color.light_sea_green));
        }
    }

    //==============================================================================================
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    //==============================================================================================
    //TODO: change alarm tune while timer is running
    /*TODO: timer setup screen reverts back to original when moved to a new activity while timer is running
    or timer_alarm is ringing*/

    public void startTimer(final View view) {
//        mButtonStartPause = (FloatingActionButton) findViewById(R.id.button_start_pause);
//        mButtonStartPause2 = (FloatingActionButton) findViewById(R.id.button_start_pause2);
//        mButtonReset = (FloatingActionButton) findViewById(R.id.button_reset);

//        try {
//
//            mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
//                @Override
//                public void onTick(long l) {
//                    mTimeLeftInMillis = l;
//                    updateCountDownText(view);
//                }

//                @Override
//                public void onFinish() {
//                    updateCountDownText_done(view);
//                    mTimerRunning = false;
//                    //mButtonStartPause.setText("Start");
//                    mButtonStartPause.setVisibility(View.INVISIBLE);
//                    mButtonStartPause2.setVisibility(View.INVISIBLE);
//                    mButtonReset.setVisibility(View.VISIBLE);
//                    timer_song.start();
//                }
//            }.start();
//        } catch (NullPointerException ignored) {
//        }
//
//        mTimerRunning = true;
//        //mButtonStartPause.setText("Pause");
//        mButtonReset.setVisibility(View.INVISIBLE);
//        mButtonStartPause.setVisibility(View.INVISIBLE);
//        mButtonStartPause2.setVisibility(View.VISIBLE);
//    }

//    public void pauseTimer(View view) {
//        mButtonStartPause = (FloatingActionButton) findViewById(R.id.button_start_pause);
//        mButtonReset = (FloatingActionButton) findViewById(R.id.button_reset);
//
//        mCountDownTimer.cancel();
//        mTimerRunning = false;
//        //mButtonStartPause.setText("Start");
//        mButtonStartPause.setVisibility(View.VISIBLE);
//        mButtonStartPause2.setVisibility(View.INVISIBLE);
//        mButtonReset.setVisibility(View.VISIBLE);
//    }

//    public void resetTimer(View view) {
////        mButtonStartPause = (FloatingActionButton) findViewById(R.id.button_start_pause);
////        mButtonReset = (FloatingActionButton) findViewById(R.id.button_reset);
//
////        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
//        if (timer_song != null) {
//            timer_song.stop();
//            timer_song.reset();
//            timer_song = null;
//        }
//
//        SharedPreferences preferences = getSharedPreferences("alarm_tune", Context.MODE_PRIVATE);
//
//        try {
//            timer_song = MediaPlayer.create(this, preferences.getInt("tune", 0));
//        } catch (Exception e) {
//            timer_song = MediaPlayer.create(this, R.raw.down_stream);
//        }
//
////        mTimeLeftInMillis = START_TIME_IN_MILLIS;
////        updateCountDownText(view);
////        mButtonReset.setVisibility(View.INVISIBLE);
////        mButtonStartPause.setVisibility(View.VISIBLE);
////        mButtonStartPause2.setVisibility(View.INVISIBLE);
//    }

//    public void updateCountDownText(View view) {
//        mTextViewCountDown = (TextView) findViewById(R.id.text_view_countdown);
//
//        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
//        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
//
//        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
//        try {
//            mTextViewCountDown.setText(timeLeftFormatted);
//        } catch (NullPointerException ignored) {
//        }
//    }

//    public void updateCountDownText_done(View view) {
//        mTextViewCountDown = (TextView) findViewById(R.id.text_view_countdown);
//
//        int minutes = 0;
//        int seconds = 0;
//
//        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
//        try {
//            mTextViewCountDown.setText(timeLeftFormatted);
//        } catch (NullPointerException ignored) {
//        }
//    }

//    public void choose_start_pause(View view) {
//        if (mTimerRunning)
//            pauseTimer(view);
//        else
//            startTimer(view);
//    }

//    public void timer_setup(View view) {
//
//        final View view1 = view;
//
//        final AlertDialog.Builder d = new AlertDialog.Builder(context);
//        LayoutInflater inflater = this.getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.number_picker_dialog, null);
//        d.setTitle("Set Timer");
//        d.setView(dialogView);
//
//        final NumberPicker numberPicker_min = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker_min);
//        numberPicker_min.setMaxValue(59);
//        numberPicker_min.setMinValue(0);
//        numberPicker_min.setWrapSelectorWheel(true);
//
//        final NumberPicker numberPicker_sec = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker_sec);
//        numberPicker_sec.setMaxValue(59);
//        numberPicker_sec.setMinValue(0);
//        numberPicker_sec.setWrapSelectorWheel(true);
//        numberPicker_sec.setFormatter(new NumberPicker.Formatter() {
//            @SuppressLint("DefaultLocale")
//            @Override
//            public String format(int i) {
//                return String.format("%02d", i);
//            }
//        });
//
//        SharedPreferences preferences = getSharedPreferences("timer_length", Context.MODE_PRIVATE);
//        int prefMin = preferences.getInt("timer_min", 0);
//        int prefSec = preferences.getInt("timer_sec", 0);
//        final SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt("timer_min", 0);
//        editor.putInt("timer_sec", 0);
//        editor.apply();
//
//        numberPicker_min.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
//                editor.putInt("timer_min", i1);
//                editor.apply();
//            }
//        });
//
//        numberPicker_sec.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
//                editor.putInt("timer_sec", i1);
//                editor.apply();
//            }
//        });
//
////        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
////            final SharedPreferences preferences_done = getSharedPreferences("timer_length", Context.MODE_PRIVATE);
//
////            @Override
////            public void onClick(DialogInterface dialogInterface, int i) {
////                int setMin = preferences_done.getInt("timer_min", 0);
////                int setSec = preferences_done.getInt("timer_sec", 0);
////
////                START_TIME_IN_MILLIS = ((setMin * 60L) + setSec) * 1000;
////                Log.e("timer_min: ", String.valueOf(setMin));
////                Log.e("timer_sec: ", String.valueOf(setSec));
////                Log.e("timer_set: ", String.valueOf(START_TIME_IN_MILLIS));
////
////                mTimeLeftInMillis = START_TIME_IN_MILLIS;
////
////                updateCountDownText(view1);
////                if (mCountDownTimer != null)
////                    mCountDownTimer.cancel();
////
////                mButtonStartPause = (FloatingActionButton) findViewById(R.id.button_start_pause);
////                mButtonStartPause2 = (FloatingActionButton) findViewById(R.id.button_start_pause2);
////                mButtonReset = (FloatingActionButton) findViewById(R.id.button_reset);
////
////                mButtonStartPause.setVisibility(View.VISIBLE);
////                mButtonStartPause2.setVisibility(View.INVISIBLE);
////                mButtonReset.setVisibility(View.INVISIBLE);
////
////            }
////        });
////        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialogInterface, int i) {
////
////            }
////        });
//        AlertDialog alertDialog = d.create();
//        alertDialog.show();
//    }
    }
}
