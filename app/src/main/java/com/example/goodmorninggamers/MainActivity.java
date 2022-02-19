package com.example.goodmorninggamers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

import static android.Manifest.permission_group.CALENDAR;

public class MainActivity extends AppCompatActivity {

    Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=-5KAN9_CzSA"));
    //https://dev.twitch.tv/docs/api/reference#get-streams
    Intent twitchIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.twitch.tv/sneakylol"));
    private AlarmManager alrmmanager;
    int count;
    int alarmHour;
    int alarmMinute;
    long AlarmMilliseconds;
    String AlarmTime;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        AlarmTime="null";
        youtubeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        twitchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        final Intent alarmScreenActivityIntent = new Intent(this,AlarmScreenActivity.class);

        super.onCreate(savedInstanceState);
        count = -1;
        setContentView(R.layout.main_activity);


        final Button detailsButton = (Button) findViewById(R.id.detailsButton);
        final TextView alarmtime = findViewById(R.id.alarmTime);
        final Button alarmScreenButton = (Button) findViewById(R.id.AlarmScreenButton);



        //BUTTON CLICKS
        alarmScreenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivityForResult(alarmScreenActivityIntent,SECOND_ACTIVITY_REQUEST_CODE);

            }
        });

        //BUTTON CLICKS
        detailsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                alarmtime.setText("AlarmTime: "+AlarmTime);

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                String returnString = data.getStringExtra(Intent.EXTRA_TEXT);

                // set text view with string
                TextView textView = (TextView) findViewById(R.id.alarmTime);
                textView.setText(returnString);

                // set alarm time
                AlarmTime = returnString;
                setAlarmFields(returnString);
                setTwitchAlarm();
            }
        }
    }

    private void setAlarmFields(String alarmString){
        String[] time = alarmString.split(" ");
        alarmHour= Integer.parseInt(time[0]);
        alarmMinute= Integer.parseInt(time[1]);
    }

    private void setTwitchAlarm() {
        AlarmManager myTwitchAlarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Calendar CalendarAlarmTime = Calendar.getInstance();

        CalendarAlarmTime.set(Calendar.HOUR_OF_DAY,alarmHour);
        CalendarAlarmTime.set(Calendar.MINUTE,alarmMinute);

        //Choose channel
        //Choose highest priority user choice, if none online do current top views
        //GET https://api.twitch.tv/helix/extensions/live
        myTwitchAlarm.set(AlarmManager.RTC_WAKEUP, CalendarAlarmTime.getTimeInMillis(), PendingIntent.getActivity(this, 0, twitchIntent,PendingIntent.FLAG_ONE_SHOT));
    }




}
