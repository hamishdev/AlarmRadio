package com.example.goodmorninggamers.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.goodmorninggamers.Activities.SetAlarmScreenComponents.AlarmOptionClickedListener;
import com.example.goodmorninggamers.Activities.SetAlarmScreenComponents.AlarmOptionFinishedListener;
import com.example.goodmorninggamers.Activities.SetAlarmScreenComponents.CustomOnKeyListener;
import com.example.goodmorninggamers.Activities.SetAlarmScreenComponents.StreamerButton;
import com.example.goodmorninggamers.Channels.StreamerChannel;
import com.example.goodmorninggamers.Network.TwitchClient;
import com.example.goodmorninggamers.R;
import com.example.goodmorninggamers.UI_Classes.Pickers.TimePickerFragment;
import com.example.goodmorninggamers.UI_Classes.Pickers.UrlPickerFragment;
import com.example.goodmorninggamers.Alarms.Alarm;
import com.example.goodmorninggamers.UI_Classes.RingtoneOption;

import java.util.ArrayList;
import java.util.Calendar;

public class SetAlarmScreenActivity extends AppCompatActivity implements TimePickerFragment.OnTimePass,UrlPickerFragment.ONURLPASS {


    int alarmHour;
    int alarmMinute;
    private static final String TAG = "SetAlarmScreenActivity";
    private String URL;
    private Calendar m_clockTime;
    private ArrayList<RingtoneOption> m_wakeupRingtoneOptions;
    private TwitchClient m_twitchClient;
    private Boolean m_loaded = false;

    public void onCreate(Bundle SavedInstanceState) {

        super.onCreate(SavedInstanceState);
        m_clockTime = Calendar.getInstance();
        m_wakeupRingtoneOptions = new ArrayList<RingtoneOption>(){
        };
        m_wakeupRingtoneOptions.add(new RingtoneOption());
        m_wakeupRingtoneOptions.add(new RingtoneOption());
        m_wakeupRingtoneOptions.add(new RingtoneOption());

        setContentView(R.layout.set_alarm);

        TimePicker clock = (TimePicker) findViewById(R.id.timePicker1);
        StreamerButton setFirstStreamerButton =  (StreamerButton) findViewById(R.id.firstStreamer);
        StreamerButton setSecondStreamerButton = (StreamerButton) findViewById(R.id.secondsStreamer);
        ImageButton setDefaultStreamerButton = (ImageButton) findViewById(R.id.defaultStreamer);
        ImageButton setAlarmButton = (ImageButton) findViewById(R.id.setAlarmButton);
        TextView alarmTimeText = (TextView) findViewById(R.id.alarmTimeText);


        clock.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                updateAlarmTime(i,i1);
                updateAlarmTimeText(alarmTimeText);
            }
        });

        //FirstStreamer
        setFirstStreamerButton.setOnClickListener(new AlarmOptionClickedListener(SetAlarmScreenActivity.this,setFirstStreamerButton));


        //SecondStreamer
        setSecondStreamerButton.setOnClickListener(new AlarmOptionClickedListener(SetAlarmScreenActivity.this,setSecondStreamerButton));


        //DefaultStreamer
        setDefaultStreamerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

            }
        });

        //SetAlarm
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent intent = new Intent();
                Alarm alarm = turnFieldsIntoAlarm();
                intent.putExtra("Alarm",alarm);
                setResult(RESULT_OK, intent);
                finish();
            }
        });



    }




    public void glideHelperLoadURL(Activity activity, String url, ImageView imageView){
        Glide.with(activity).load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(R.drawable.number2)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(imageView);
    }


    public void updateAlarmTime(int hourOfDay,int minute){

        Calendar clockTime =Calendar.getInstance();
        clockTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
        clockTime.set(Calendar.MINUTE,minute);
        long clockTimeInMillis =clockTime.getTimeInMillis();
        Calendar now = Calendar.getInstance();

        //If alarm is set in the past assume alarm should be in the next day 
        if(clockTimeInMillis<now.getTimeInMillis()){
            //(AS long as the alarm isn't set for the current minute)
            if(!(now.get(Calendar.MINUTE)==clockTime.get(Calendar.MINUTE)&&now.get(Calendar.HOUR_OF_DAY)==clockTime.get(Calendar.HOUR_OF_DAY))){
                clockTime.add(Calendar.DAY_OF_YEAR,+1);
            }

        }
        m_clockTime = clockTime;

    }

    public void updateAlarmTimeText(TextView alarmTimeText){
        long difference = m_clockTime.getTimeInMillis()-Calendar.getInstance().getTimeInMillis();
        int days = (int) (difference / (1000*60*60*24));
        int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
        int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
        String clockAlarmString = "Next alarm will ring in "+(hours==0?"":hours+"h")+min+"m";
        alarmTimeText.setText(clockAlarmString);
    }
    public void setAlarmHour(int fragmentAlarmHour){
        alarmHour=fragmentAlarmHour;
    }
    public void setAlarmMinute(int fragmentAlarmMinute){
        alarmMinute=fragmentAlarmMinute;
    }

    @Override
    public void onUrlPass(String data) {

        Log.d(TAG,"passing URL data:"+data);
        URL= data;
        DialogFragment finalFragment = new TimePickerFragment();
        finalFragment.show(getSupportFragmentManager(), "timePicker");

    }
    @Override
    public void onTimePass(String data) {

        Log.d(TAG,"passing time data:"+data);

        String allData = data + " " + URL;



    }


    //Copy pasted from main activity to help with making the alarm when time comes to it.
    private Alarm turnFieldsIntoAlarm(){


        Alarm fromData = new Alarm(m_clockTime,null);
        Log.v(TAG,"alarm time set!");
        return fromData;
    }



}
