package com.example.goodmorninggamers.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.goodmorninggamers.Activities.SetAlarmScreen_Components.AlarmOptionClickedListener;
import com.example.goodmorninggamers.Activities.SetAlarmScreen_Components.DefaultButton;
import com.example.goodmorninggamers.Activities.SetAlarmScreen_Components.DefaultOptionClickedListener;
import com.example.goodmorninggamers.Activities.SetAlarmScreen_Components.RingtoneOptionFinishedListener;
import com.example.goodmorninggamers.Activities.SetAlarmScreen_Components.StreamerButton;
import com.example.goodmorninggamers.R;
import com.example.goodmorninggamers.Alarms.Alarm;
import com.example.goodmorninggamers.Alarms.RingtoneOption;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class SetAlarmScreen_Activity extends AppCompatActivity implements  RingtoneOptionFinishedListener, Serializable {


    int alarmHour;
    int alarmMinute;
    private static final String TAG = "SetAlarmScreenActivity";
    private Calendar m_clockTime;
    private ArrayList<RingtoneOption> m_wakeupRingtoneOptions;
    private Boolean defaultIsSet = false;

    public void onCreate(Bundle SavedInstanceState) {

        super.onCreate(SavedInstanceState);
        m_clockTime = Calendar.getInstance();
        m_wakeupRingtoneOptions = new ArrayList<RingtoneOption>(){
        };
        //setting empty ringtones
        m_wakeupRingtoneOptions.add(new RingtoneOption("1","1","1",null));
        m_wakeupRingtoneOptions.add(new RingtoneOption("2","2","2",null));
        m_wakeupRingtoneOptions.add(new RingtoneOption("3","3","3",null));


        setContentView(R.layout.setalarm_screen);

        TimePicker clock = (TimePicker) findViewById(R.id.timePicker1);
        ImageButton backButton = (ImageButton) findViewById(R.id.SetAlarmBackBUtton);
        StreamerButton setFirstStreamerButton =  (StreamerButton) findViewById(R.id.firstStreamer);
        StreamerButton setSecondStreamerButton = (StreamerButton) findViewById(R.id.secondsStreamer);
        DefaultButton setDefaultStreamerButton = (DefaultButton) findViewById(R.id.defaultStreamer);
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
        setFirstStreamerButton.setOnClickListener(new AlarmOptionClickedListener(SetAlarmScreen_Activity.this,setFirstStreamerButton,0));


        //SecondStreamer
        setSecondStreamerButton.setOnClickListener(new AlarmOptionClickedListener(SetAlarmScreen_Activity.this,setSecondStreamerButton,1));


        //DefaultStreamer
        setDefaultStreamerButton.setOnClickListener(new DefaultOptionClickedListener(SetAlarmScreen_Activity.this,setDefaultStreamerButton,2));

        //SetAlarm
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if(defaultIsSet) {
                    Intent intent = new Intent();
                    //build alarm
                    intent.putExtra("alarm", new Alarm(m_clockTime,m_wakeupRingtoneOptions));
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(SetAlarmScreen_Activity.this);
                    builder.setMessage("Cannot create an alarm without a backup alarm (in case your streamers aren't live)");
                    builder.create().show();

                }
            }
        });

        //backButton
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SetAlarmScreen_Activity.this);
                SpannableString title = new SpannableString("Discard modifications?");

                // alert dialog title align center
                title.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0,
                        title.length(),
                        0
                );

                builder.setTitle(title);

                SpannableString[] items = new SpannableString[]{
                        new SpannableString("Discard"),new SpannableString("Cancel")
                };
                items[0].setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0,
                        items[0].length(),
                        0
                );
                items[1].setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0,
                        items[1].length(),
                        0
                );
                builder.setItems(items,new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            setResult(RESULT_CANCELED);
                            finish();
                        }
                        else{

                        }
                    }
                });
                AlertDialog alert =builder.create();
                alert.show();
            }
        });



    }

    public void updateDefault(Boolean defaultstatus){
        defaultIsSet=defaultstatus;
    }

    private Alarm turnFieldsIntoAlarm(){
        Alarm fromData = new Alarm(m_clockTime,m_wakeupRingtoneOptions);
        Log.v(TAG,"alarm time set!");
        return fromData;
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
    public void RingtoneOptionFinished(int option, RingtoneOption ringtone) {
        m_wakeupRingtoneOptions.set(option,ringtone);
        if(option==2){
            updateDefault(true);
        }
    }
}
