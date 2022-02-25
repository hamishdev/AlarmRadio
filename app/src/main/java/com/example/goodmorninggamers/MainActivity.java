package com.example.goodmorninggamers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.goodmorninggamers.screens.OnePlus7AlarmActivity;
import com.example.goodmorninggamers.screens.SetAlarmScreenActivity;

import org.json.JSONArray;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=-5KAN9_CzSA"));
    //https://dev.twitch.tv/docs/api/reference#get-streams
    Intent twitchIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.twitch.tv/sneakylol"));
    Intent OnePlus7AlarmIntent = new Intent(this, OnePlus7AlarmActivity.class);
    Intent h3h3Intent = new Intent(Intent.ACTION_VIEW,Uri.parse(generateH3H3URL()));
    Intent testIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(generateTestURL()));
    private String generateH3H3URL() {
        String H3H3channelID = "UCLtREJY21xRfCuEKvdki1Kw";

        //Connect to youtube to get specific video ID of a H3H3 livestream
        YoutAPI_client APIClient = new YoutAPI_client(this);
        String videoID =APIClient.GetVideoIDyoutubeLivestream(H3H3channelID);

        return "https://www.youtube.com/watch?v="+videoID;
    }

    private String generateTestURL() {
        String testChannelID = "UCBIe28uoEnt_LEdNFbWankA";

        //Connect to youtube to get specific video ID of a H3H3 livestream
        YoutAPI_client APIClient = new YoutAPI_client(this);
        String videoID =APIClient.GetVideoIDyoutubeLivestream(testChannelID);

        return "https://www.youtube.com/watch?v="+videoID;
    }

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

        final Intent alarmScreenActivityIntent = new Intent(this, SetAlarmScreenActivity.class);

        super.onCreate(savedInstanceState);
        count = -1;
        setContentView(R.layout.main_activity);


        final Button detailsButton = (Button) findViewById(R.id.detailsButton);
        final TextView alarmtime = findViewById(R.id.alarmTime);
        final Button alarmScreenButton = (Button) findViewById(R.id.AlarmScreenButton);
        final Button testYoutubeAPIIntentButton= (Button) findViewById(R.id.TestYoutubeAPIIntentBUtton);


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


        testYoutubeAPIIntentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivityForResult(testIntent,0);

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
                setAlarm();
            }
        }
    }

    //set internal public field alarm times
    private void setAlarmFields(String alarmString){
        String[] time = alarmString.split(" ");
        alarmHour= Integer.parseInt(time[0]);
        alarmMinute= Integer.parseInt(time[1]);
    }

    private void setAlarm() {
        AlarmManager myAlarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Calendar CalendarAlarmTime = Calendar.getInstance();

        //Set this alarm to the public field alarm times set in alternate view
        CalendarAlarmTime.set(Calendar.HOUR_OF_DAY,alarmHour);
        CalendarAlarmTime.set(Calendar.MINUTE,alarmMinute);

        //**IMPLEMENT**
        //Choose channel
        //Choose highest priority user choice, if none online do current top views
        //GET https://api.twitch.tv/helix/extensions/live]
        myAlarm.setExact(AlarmManager.RTC_WAKEUP,CalendarAlarmTime.getTimeInMillis(),PendingIntent.getActivity(this,0,OnePlus7AlarmIntent,PendingIntent.FLAG_ONE_SHOT));
                //or
        myAlarm.setExact(AlarmManager.RTC_WAKEUP, CalendarAlarmTime.getTimeInMillis(), PendingIntent.getActivity(this, 0, twitchIntent,PendingIntent.FLAG_ONE_SHOT));
                //But actually only
        myAlarm.setExact(AlarmManager.RTC_WAKEUP,CalendarAlarmTime.getTimeInMillis(),PendingIntent.getActivity(this,0,h3h3Intent,PendingIntent.FLAG_ONE_SHOT));
    }




}
