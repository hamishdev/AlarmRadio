package com.example.goodmorninggamers.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.goodmorninggamers.R;
import com.example.goodmorninggamers.Pickers.TimePickerFragment;
import com.example.goodmorninggamers.Pickers.UrlPickerFragment;
import com.example.goodmorninggamers.apppieces.Alarm;

import java.util.Calendar;

public class SetAlarmScreenActivity extends AppCompatActivity implements TimePickerFragment.OnTimePass,UrlPickerFragment.ONURLPASS {


    int alarmHour;
    int alarmMinute;
    private static final String TAG = "SetAlarmScreenActivity";
    private String URL;
    private Calendar m_clockTime;

    public void onCreate(Bundle SavedInstanceState) {

        super.onCreate(SavedInstanceState);
        m_clockTime = Calendar.getInstance();
        setContentView(R.layout.newset_alarm_activity);

        TimePicker clock = (TimePicker) findViewById(R.id.timePicker1);
        ImageButton setFirstStreamerButton = (ImageButton) findViewById(R.id.firstStreamer);
        ImageButton setSecondStreamerButton = (ImageButton) findViewById(R.id.secondsStreamer);
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
        setFirstStreamerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

            }
        });


        //SecondStreamer
        setSecondStreamerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

            }
        });


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
