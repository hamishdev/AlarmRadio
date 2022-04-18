package com.example.goodmorninggamers.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.goodmorninggamers.Activities.SetAlarmScreen_Components.AlarmOptionClickedListener;
import com.example.goodmorninggamers.Activities.SetAlarmScreen_Components.DefaultButton;
import com.example.goodmorninggamers.Activities.SetAlarmScreen_Components.DefaultOptionClickedListener;
import com.example.goodmorninggamers.Activities.SetAlarmScreen_Components.RingtoneOptionFinishedListener;
import com.example.goodmorninggamers.Activities.SetAlarmScreen_Components.StreamerButton;
import com.example.goodmorninggamers.Alarms.Alarm;
import com.example.goodmorninggamers.Alarms.RingtoneOption;
import com.example.goodmorninggamers.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class EditAlarm_Activity extends AppCompatActivity implements RingtoneOptionFinishedListener, Serializable {


    private static final String TAG = "EditAlarm_Screen";
    private Calendar m_clockTime;

    private ArrayList<RingtoneOption> m_wakeupRingtoneOptions;
    private Boolean defaultIsSet = true;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);

        Intent intent = getIntent();
        Alarm toEditAlarm = (Alarm)intent.getSerializableExtra("Alarm");
        m_clockTime = toEditAlarm.time;
        m_wakeupRingtoneOptions = toEditAlarm.getWakeupOptions();


        setContentView(R.layout.setalarm_screen);

        TimePicker clock = (TimePicker) findViewById(R.id.timePicker1);
        clock.setHour(m_clockTime.get(Calendar.HOUR));
        clock.setMinute(m_clockTime.get(Calendar.MINUTE));

        ImageButton backButton = (ImageButton) findViewById(R.id.SetAlarmBackBUtton);
        StreamerButton setFirstStreamerButton =  (StreamerButton) findViewById(R.id.firstStreamer);
        setFirstStreamerButton.saveOption(m_wakeupRingtoneOptions.get(0),this);
        StreamerButton setSecondStreamerButton = (StreamerButton) findViewById(R.id.secondsStreamer);
        setSecondStreamerButton.saveOption(m_wakeupRingtoneOptions.get(1),this);
        DefaultButton setDefaultStreamerButton = (DefaultButton) findViewById(R.id.defaultStreamer);
        setDefaultStreamerButton.saveDefaultOption(this,m_wakeupRingtoneOptions.get(2));

        ImageButton setAlarmButton = (ImageButton) findViewById(R.id.setAlarmButton);
        TextView alarmTimeText = (TextView) findViewById(R.id.alarmTimeText);
        DatePicker datepicker = (DatePicker) findViewById(R.id.datePicker1);
        datepicker.updateDate(m_clockTime.get(Calendar.YEAR),m_clockTime.get(Calendar.MONTH),m_clockTime.get(Calendar.DAY_OF_MONTH));

        datepicker.setMinDate(System.currentTimeMillis() - 1000);
        datepicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                updateDay(year,month,dayOfMonth);
                updateNextAlarmTextString(alarmTimeText);
            }
        });

        clock.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                updateAlarmTime(i,i1);
                updateNextAlarmTextString(alarmTimeText);
            }
        });


        //FirstStreamer
        setFirstStreamerButton.setOnClickListener(new AlarmOptionClickedListener(EditAlarm_Activity.this,setFirstStreamerButton,0));


        //SecondStreamer
        setSecondStreamerButton.setOnClickListener(new AlarmOptionClickedListener(EditAlarm_Activity.this,setSecondStreamerButton,1));


        //DefaultStreamer
        setDefaultStreamerButton.setOnClickListener(new DefaultOptionClickedListener(EditAlarm_Activity.this,setDefaultStreamerButton,2));

        //SetAlarm
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if(defaultIsSet) {
                    Intent intent = new Intent();
                    //edit alarm
                    Alarm toReturn = toEditAlarm;
                    toReturn.updateRingtoneOptions(m_wakeupRingtoneOptions);
                    toReturn.updateTime(m_clockTime);
                    intent.putExtra("alarm", toReturn);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditAlarm_Activity.this);
                    builder.setMessage("Cannot create an alarm without a backup alarm (in case your streamers aren't live)");
                    builder.create().show();

                }
            }
        });

        //backButton
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditAlarm_Activity.this);
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


    private void updateDay(int year, int month, int dayOfMonth) {

        m_clockTime.set(year,month,dayOfMonth);
        Log.v(TAG,"selectedYear:"+year);
        Log.v(TAG,"selectedMonth:"+month);
        Log.v(TAG,"selectedDayOfMonth:"+dayOfMonth);
        Log.v(TAG, "clocktime date:"+m_clockTime.get(Calendar.DATE));

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

    public void updateNextAlarmTextString(TextView alarmTimeText){
        long difference = m_clockTime.getTimeInMillis()-Calendar.getInstance().getTimeInMillis();
        int days = (int) (difference / (1000*60*60*24));
        int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
        int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
        String clockAlarmString = "Next alarm will ring in "+(days==0?"":days+"days")+(hours==0?"":hours+"h")+min+"m";
        alarmTimeText.setText(clockAlarmString);
    }






    @Override
    public void RingtoneOptionFinished(int option, RingtoneOption ringtone) {
        m_wakeupRingtoneOptions.set(option,ringtone);
        if(option==2){
            updateDefault(true);
        }
    }
}
