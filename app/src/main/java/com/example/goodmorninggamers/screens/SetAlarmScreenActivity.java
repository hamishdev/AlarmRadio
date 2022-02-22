package com.example.goodmorninggamers.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.goodmorninggamers.R;
import com.example.goodmorninggamers.TimePickerFragment;

public class SetAlarmScreenActivity extends AppCompatActivity implements TimePickerFragment.OnDataPass {


    int alarmHour;
    int alarmMinute;

    public void onCreate(Bundle SavedInstanceState) {

        super.onCreate(SavedInstanceState);
        setContentView(R.layout.alarm_screen_activity);

        final Button alarmTimeDialogButton = (Button) findViewById(R.id.AlarmTimeDialogButton);
        final Button showAlarmTimeButton = (Button) findViewById(R.id.ShowAlarmTimeButton);

        alarmTimeDialogButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
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
    public void onDataPass(String data) {
        dataParse(data);
    }

    private void dataParse(String data) {


        Log.d("dataparse",data);

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_TEXT, data);
        setResult(RESULT_OK, intent);
        finish();



    }
}
