package com.example.goodmorninggamers.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.goodmorninggamers.R;
import com.example.goodmorninggamers.Pickers.TimePickerFragment;
import com.example.goodmorninggamers.Pickers.UrlPickerFragment;

import java.util.ArrayList;

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
        intent.putExtra(Intent.EXTRA_TEXT, allData);
        setResult(RESULT_OK, intent);
        finish();

    }

}
