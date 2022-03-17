package com.example.goodmorninggamers.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.goodmorninggamers.apppieces.Alarm;
import com.example.goodmorninggamers.AlarmAdapter;
import com.example.goodmorninggamers.R;
import com.example.goodmorninggamers.Network.YoutAPI_client;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    private static final int SETALARM_ACTIVITY_REQUEST_CODE = 0;
    private static final int ALARMHOMESCREEN_ACTIVITY_REQUEST_CODE = 1;

    public static String AndroidChannelID = "Alarm";
    static YoutAPI_client APIClient;
    public ArrayList<Alarm> m_alarms;
    public AlarmAdapter alarmArrayAdapter;
    public AlarmSetter m_alarmSetter;
    @Override
    public void onCreate(Bundle savedInstanceState) {


        //ONCREATE
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmhomescreen_activty);
        APIClient = new YoutAPI_client();
        createNotificationChannel();
        if(m_alarms ==null){
            m_alarms = new ArrayList<Alarm>(){};
        };

        final Intent alarmScreenActivityIntent = new Intent(this, SetAlarmScreenActivity.class);

        alarmArrayAdapter = new AlarmAdapter(this, m_alarms);
        ListView alarmsListView = (ListView) findViewById(R.id.AlarmListView);
        alarmsListView.setAdapter(alarmArrayAdapter);


        final Button detailsButton = (Button) findViewById(R.id.detailsButton);
        final TextView alarmtime = findViewById(R.id.alarmTime);
        final Button alarmScreenButton = (Button) findViewById(R.id.alarmScreenButton);


        //ALARM SCREEN
        alarmScreenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivityForResult(alarmScreenActivityIntent, SETALARM_ACTIVITY_REQUEST_CODE);

            }
        });

        //DETAILS
        detailsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                alarmtime.setText("AlarmTime: "+timeOfTheAlarmAsWords);

            }
        });


    }

    //woa
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SETALARM activity with an OK result
        if (requestCode == SETALARM_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                String returnString = data.getStringExtra(Intent.EXTRA_TEXT);

                //Log the alarm
                turnAlarmDataIntoAlarm(returnString);
                Log.v(TAG,"is there any alarms here lul"+ m_alarms.get(0).ToString());


            }
        }
    }

    //set internal public field alarm times
    private void parseAlarmData(String alarmString){
        String[] time = alarmString.split(" ");
        alarmHour= Integer.parseInt(time[0]);
        alarmMinute= Integer.parseInt(time[1]);
        if(time.length<3){
            alarmURL="null";
        }
        else{
            alarmURL = time[2];
        }

    }


    private void turnAlarmDataIntoAlarm(String alarmData){
        // set text view with string
        TextView textView = (TextView) findViewById(R.id.alarmTime);
        textView.setText(alarmData);

        // set alarm time
        AlarmTime = alarmData;
        parseAlarmData(alarmData);
        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,alarmHour);
        calendar.set(Calendar.MINUTE,alarmMinute);
        long calendarTimeInMillis =calendar.getTimeInMillis();
        Calendar now = Calendar.getInstance();

        setPhysicalAlarm();
        if(calendarTimeInMillis<Calendar.getInstance().getTimeInMillis()){
            if(!(now.get(Calendar.MINUTE)==calendar.get(Calendar.MINUTE)&&now.get(Calendar.HOUR_OF_DAY)==calendar.get(Calendar.HOUR_OF_DAY))){
                calendar.add(Calendar.DAY_OF_YEAR,+1);
            }

        }
        calendarTimeInMillis =calendar.getTimeInMillis();
        m_alarms.add(new Alarm(calendarTimeInMillis,alarmURL));
        alarmArrayAdapter.notifyDataSetChanged();
        Log.v(TAG,"alarm time set!");
    }


    private void setPhysicalAlarm() {
        AlarmManager myAlarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Calendar now = Calendar.getInstance();
        Calendar CalendarAlarmTime = Calendar.getInstance();

        //Set this alarm to the public field alarm times set in alternate view
        CalendarAlarmTime.set(Calendar.HOUR_OF_DAY,alarmHour);
        CalendarAlarmTime.set(Calendar.MINUTE,alarmMinute);

        //Alarm was set in the past
        Log.v(TAG,"userSetAlarm ="+CalendarAlarmTime.getTimeInMillis()+"Actual time="+now.getTimeInMillis());
        if(CalendarAlarmTime.getTimeInMillis()<now.getTimeInMillis()){
            //Set alarm to tomorrow
            Log.v(TAG, "alarm time PRE ="+ CalendarAlarmTime.toString());
            CalendarAlarmTime.add(Calendar.DAY_OF_YEAR,+1);
            Log.v(TAG, "alarm time POST ="+ CalendarAlarmTime.toString());
        }
        //Alarm was set in the future
        else{
            //Alarm is already set to today in the future
        }


        Intent intent = new Intent(this,broadcastReceiverAlarmNotificationBuild.class);
        intent.putExtra(Intent.EXTRA_TEXT, alarmURL);
        final int id= (int) System.currentTimeMillis();
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this,id,intent,PendingIntent.FLAG_IMMUTABLE);
        Log.v(TAG,"alarm broadcast set");
        Log.v(TAG, String.valueOf(CalendarAlarmTime.getTimeInMillis()));
        timeOfTheAlarmAsWords = CalendarAlarmTime.getTime().toString();
        Log.v(TAG,timeOfTheAlarmAsWords);

        //Set AlARM
        myAlarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, CalendarAlarmTime.getTimeInMillis(), alarmIntent);

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alarm notifications";
            String description = "Channel for Alarm notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(AndroidChannelID, name, importance);
            channel.setDescription(description);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }




    public static class broadcastReceiverAlarmNotificationBuild extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {

            String intentString = intent.getStringExtra(Intent.EXTRA_TEXT);
            //Triggers livestream check inside broadcastreceiver
            APIClient.SendChannelVideoLivestreamRequest(currentYTchannelID);
            //Choose Intent based on whether channel is live or not
            Intent testIntent = chooseIntent(intentString);
            testIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, testIntent , PendingIntent.FLAG_IMMUTABLE);

            Log.v(TAG,"test notification");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, AndroidChannelID)
                    .setSmallIcon(android.R.drawable.arrow_up_float)
                    .setContentTitle("alarm!!")
                    .setContentText(intentString)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setFullScreenIntent(pendingIntent,true)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            int notificationId = 1;
// notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, builder.build());
        }

        private Intent chooseIntent(String url) {
            if (url.equals("H3H3")) {
                //H3h3 live
                if (APIClient.H3H3isLive){
                    String videoID = APIClient.getVideoID();
                    Log.v(TAG, "h3h3 livestream retrieved videoID"+ videoID+"<-VideoID");
                    return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+videoID));
                }
                //H3H3 not live, send to lofi
                else{
                    Log.v(TAG,"H3H3 not live, sending to Lofi");
                    return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=xgirCNccI68"));
                }

            }
            else{
                Log.v(TAG,"Pure URL");
                return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            }
        }
    }




}
