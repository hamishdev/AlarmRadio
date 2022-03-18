package com.example.goodmorninggamers.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.goodmorninggamers.R;
import com.example.goodmorninggamers.Pickers.TimePickerFragment;
import com.example.goodmorninggamers.Pickers.UrlPickerFragment;
import com.example.goodmorninggamers.apppieces.Alarm;

import java.util.ArrayList;
import java.util.Calendar;

public class SetAlarmScreenActivity extends AppCompatActivity implements TimePickerFragment.OnTimePass,UrlPickerFragment.ONURLPASS {


    int alarmHour;
    int alarmMinute;
    private static final String TAG = "SetAlarmScreenActivity";
    private String URL;
    public void onCreate(Bundle SavedInstanceState) {

        super.onCreate(SavedInstanceState);
        setContentView(R.layout.alarm_screen_activity);

        final Button setAlarmTimeDialogButton = (Button) findViewById(R.id.setAlarmTimeDialogueButton);
        final Button showAlarmTimeButton = (Button) findViewById(R.id.ShowAlarmTimeButton);

        setAlarmTimeDialogButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                DialogFragment newFragment = new UrlPickerFragment();
                newFragment.show(getSupportFragmentManager(), "urlPicker");
            }
        });

        showAlarmTimeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                showAlarmTimeButton.setText(alarmHour +":"+alarmMinute);
            }
        });


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
        Intent intent = new Intent();
        String allData = data + " " + URL;

        Alarm alarm = turnAlarmDataIntoAlarm(data);
        intent.putExtra("Alarm",alarm);
        setResult(RESULT_OK, intent);
        finish();

    }


    //Copy pasted from main activity to help with making the alarm when time comes to it.
    private Alarm turnAlarmDataIntoAlarm(String alarmData){

        Calendar calendar =Calendar.getInstance();
        long calendarTimeInMillis =calendar.getTimeInMillis();
        Calendar now = Calendar.getInstance();

        if(calendarTimeInMillis<Calendar.getInstance().getTimeInMillis()){
            if(!(now.get(Calendar.MINUTE)==calendar.get(Calendar.MINUTE)&&now.get(Calendar.HOUR_OF_DAY)==calendar.get(Calendar.HOUR_OF_DAY))){
                calendar.add(Calendar.DAY_OF_YEAR,+1);
            }

        }
        //////THIS IS LOGIC TO PARSE HOUR/MINUTE INFORMATION INTO WHETHER THE ALARM SHOULD BE MEANT FOR TOMORROW OR NOT
        //////AM REVAMPING WHOLE ALARM SETTING PAGE SO THIS WHOLE SECTION WILL BE REDONE ANYWAY IT'S JUST NICE TO REMEMBER THIS ONE FEATURE
        //Alarm was set in the past
        Log.v(TAG,"userSetAlarm ="+AlarmTime.getTimeInMillis()+"Actual time="+now.getTimeInMillis());
        if(AlarmTime.getTimeInMillis()<now.getTimeInMillis()){
            //Set alarm to tomorrow
            Log.v(TAG, "alarm time PRE ="+ AlarmTime.toString());
            AlarmTime.add(Calendar.DAY_OF_YEAR,+1);
            Log.v(TAG, "alarm time POST ="+ AlarmTime.toString());
        }
        //Alarm was set in the future
        else{
            //Alarm is already set to today in the future
        }


        Alarm fromData = new Alarm(alarmTime,null);
        Log.v(TAG,"alarm time set!");
        return fromData;
    }

}
