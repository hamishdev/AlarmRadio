package my.app.goodmorninggamers.Alarms;

import static my.app.goodmorninggamers.Activities.Main_Activity.ALARMACTION;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import my.app.goodmorninggamers.Activities.Main_Activity;
import my.app.goodmorninggamers.AlarmBroadcastReceiver.GoodMorningGamersAlarmBroadcastReceiver;

import java.util.Calendar;

public class AlarmCreator extends AppCompatActivity {

    private String TAG = "AlarmSetter";
    Main_Activity m_testingContext;


    public AlarmCreator(){
       // m_testingContext = testingContext;
    }



    public void createAlarm(Context context, Alarm alarm){



            AlarmManager myAlarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


            Calendar alarmTime = Calendar.getInstance();
            int dayTotalMinutes = alarmTime.get(Calendar.HOUR_OF_DAY)*60 +alarmTime.get(Calendar.MINUTE);
            //Comparing whether alarm should be set today or tomorrow
            if(dayTotalMinutes>alarm.getTotalMinutes()){
                alarmTime.add(Calendar.DAY_OF_YEAR,1);
            }
            alarmTime.set(Calendar.HOUR_OF_DAY, alarm.getHour());
            alarmTime.set(Calendar.MINUTE, alarm.getMinute());


            Intent intent = new Intent(context, GoodMorningGamersAlarmBroadcastReceiver.class);
            intent.putExtra("alarm", alarm);

            final int id = alarm.getId();
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE);


            ////////Logging

            //Create AlARM
            myAlarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), alarmIntent);
        //m_testingContext.addRealAlarm();

    }


    public void deleteAlarm(Context context, Alarm alarm){
        AlarmManager myManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent toCancel = new Intent(context, GoodMorningGamersAlarmBroadcastReceiver.class);

        PendingIntent alarmToCancel = PendingIntent.getBroadcast(context,alarm.getId(),toCancel,PendingIntent.FLAG_IMMUTABLE);
        alarmToCancel.cancel();
        myManager.cancel(alarmToCancel);
        //m_testingContext.removeRealAlarm();

    }

    public void editAlarm(Context context, Alarm toEditAlarm) {
        //Delete previous alarm
        deleteAlarm(context,toEditAlarm);
        createAlarm(context,toEditAlarm);

    }
}
