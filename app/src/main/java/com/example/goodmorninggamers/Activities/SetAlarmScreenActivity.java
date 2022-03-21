package com.example.goodmorninggamers.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.goodmorninggamers.Data.TwitchChannel;
import com.example.goodmorninggamers.Network.TwitchClient;
import com.example.goodmorninggamers.R;
import com.example.goodmorninggamers.Pickers.TimePickerFragment;
import com.example.goodmorninggamers.Pickers.UrlPickerFragment;
import com.example.goodmorninggamers.apppieces.Alarm;
import com.example.goodmorninggamers.invisibleapppieces.RingtoneOption;

import java.util.ArrayList;
import java.util.Calendar;

public class SetAlarmScreenActivity extends AppCompatActivity implements TimePickerFragment.OnTimePass,UrlPickerFragment.ONURLPASS {


    int alarmHour;
    int alarmMinute;
    private static final String TAG = "SetAlarmScreenActivity";
    private String URL;
    private Calendar m_clockTime;
    private ArrayList<RingtoneOption> m_wakeupRingtoneOptions;
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

        //FirstRingtoneOption choose platform of ringtone
        setFirstStreamerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                String[] options = {"Twitch stream", "Youtube stream"};
                AlertDialog.Builder builder = new AlertDialog.Builder(SetAlarmScreenActivity.this);
                builder.setItems(options, new DialogInterface.OnClickListener() {

                    //FirstRingtoneOption search Streamer
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            //First RingtoneOption twitchSearchDialog
                            case 0:
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(SetAlarmScreenActivity.this);
                            final EditText input = new EditText(SetAlarmScreenActivity.this);
                            //on Enter button
                                input.setOnKeyListener(new View.OnKeyListener() {
                                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                                        if (keyCode==KeyEvent.KEYCODE_ENTER) {
                                           //SearchTwitch API
                                            String searchString = input.getText().toString();
                                            TwitchClient client = new TwitchClient();
                                            TwitchChannel topChannel = (TwitchChannel) client.getChannelsFromString(searchString).get(0);
                                            String url = topChannel.getLiveContentURL();
                                            String picURL = topChannel.getPicURL();
                                            m_wakeupRingtoneOptions.set(0, new RingtoneOption(url,picURL));
                                            AlertDialog.Builder builder3 = new AlertDialog.Builder(SetAlarmScreenActivity.this);
                                            final ImageView temporary = new ImageView(SetAlarmScreenActivity.this);
                                            Glide.with(SetAlarmScreenActivity.this).load(topChannel.getPicURL()).into(temporary);
                                            builder3.setView(temporary);
                                            builder3.show();


                                        }
                                        return false;
                                    }});
                            input.setInputType(InputType.TYPE_CLASS_TEXT);
                            builder2.setView(input);
                            builder2.show();

                            //youtubeSearchDialog
                            //TODO
                            case 1:
                        }

                    }
                });
                AlertDialog alert = builder.create();

                alert.show();
            //Show search youtube/twitch dialogue
                //Show input text dialogue
                //Show returned results dialogue
                //Add Option to SetAlarm activity
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
