package com.example.goodmorninggamers.Alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.goodmorninggamers.Activities.GoodMorningGamersAlarmBroadcastReceiver;

import java.util.Calendar;

public class AlarmCreator extends AppCompatActivity {

    private String TAG = "AlarmSetter";

    public AlarmCreator(){
    }

    public void createAlarm(Context context, Alarm alarm){

        AlarmManager myAlarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);



        Calendar now = Calendar.getInstance();
        Calendar AlarmTime = alarm.getCalendarTime();

        Intent intent = new Intent(context, GoodMorningGamersAlarmBroadcastReceiver.class);
        intent.putExtra("alarm", alarm);

        final int id= (int) System.currentTimeMillis();
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context,id,intent,PendingIntent.FLAG_IMMUTABLE);



        ////////Logging
        Log.v(TAG,"alarm broadcast set");
        Log.v(TAG, String.valueOf(AlarmTime.getTimeInMillis()));
        String timeOfTheAlarmAsWords = AlarmTime.getTime().toString();
        Log.v(TAG,timeOfTheAlarmAsWords);

        //Create AlARM
        myAlarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, AlarmTime.getTimeInMillis(), alarmIntent);

    }
}
