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
import android.widget.TextView;

import com.example.goodmorninggamers.AlarmReceiver;
import com.example.goodmorninggamers.R;
import com.example.goodmorninggamers.YoutAPI_client;
import com.example.goodmorninggamers.screens.SetAlarmScreenActivity;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=-5KAN9_CzSA"));
    Intent twitchIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.twitch.tv/sneakylol"));



    int alarmHour;
    int alarmMinute;
    String AlarmTime;
    String timeOfTheAlarmAsWords;
    private static final int SETALARM_ACTIVITY_REQUEST_CODE = 0;
    private static final int ALARMHOMESCREEN_ACTIVITY_REQUEST_CODE = 1;

    public static String ChannelID;
    public static String sleepytimesYTChannelID ="UCBIe28uoEnt_LEdNFbWankA";
    public static String H3H3YTChannelID = "UCLtREJY21xRfCuEKvdki1Kw";
    public static String currentYTchannelID = H3H3YTChannelID;
    static YoutAPI_client APIClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        APIClient = new YoutAPI_client(currentYTchannelID);
        ChannelID="Alarm";
        createNotificationChannel();
        AlarmTime="null";
        youtubeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        twitchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final Intent alarmScreenActivityIntent = new Intent(this, SetAlarmScreenActivity.class);
        final Intent alarmHomeScreenActivityIntent = new Intent(this, AlarmHomeScreenActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        final Button detailsButton = (Button) findViewById(R.id.detailsButton);
        final TextView alarmtime = findViewById(R.id.alarmTime);
        final Button alarmScreenButton = (Button) findViewById(R.id.AlarmScreenButton);
        final Button newAlarmsButton = (Button) findViewById(R.id.new_alarms);
        final Button testYoutubeAPIIntentButton= (Button) findViewById(R.id.TestYoutubeAPIIntentBUtton);



        //NEW ALARMS SCREEN
        newAlarmsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivityForResult(alarmHomeScreenActivityIntent, ALARMHOMESCREEN_ACTIVITY_REQUEST_CODE);

            }
        });
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

        //TEST YOUTUBE API
        testYoutubeAPIIntentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String videoID = APIClient.getVideoID();
                Log.v(TAG,"videoID"+videoID);
                buildAlarmNotification();
                //startActivityForResult(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/watch?v="+videoID)),0);

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

                // set text view with string
                TextView textView = (TextView) findViewById(R.id.alarmTime);
                textView.setText(returnString);

                // set alarm time
                AlarmTime = returnString;
                setAlarmFields(returnString);

                setAlarm();
                Log.v(TAG,"alarm time set!");
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
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_IMMUTABLE);
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
            NotificationChannel channel = new NotificationChannel(ChannelID, name, importance);
            channel.setDescription(description);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }
    private void buildAlarmNotification(){

        // request code and flags not added for demo purposes
        Intent testIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=0mgu0n81nYE"));
        testIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, testIntent , PendingIntent.FLAG_IMMUTABLE);

        Log.v(TAG,"test notification");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ChannelID)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("alarm!!")
                .setContentText("Youtube B)")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setFullScreenIntent(pendingIntent,true)
                .setAutoCancel(true);



    }



    public static class broadcastReceiverAlarmNotificationBuild extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {

            //Triggers livestream check inside broadcastreceiver
            APIClient.SendChannelVideoLivestreamRequest(currentYTchannelID);
            //Choose Intent based on whether channel is live or not
            Intent testIntent = chooseIntent();
            testIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, testIntent , PendingIntent.FLAG_IMMUTABLE);

            Log.v(TAG,"test notification");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ChannelID)
                    .setSmallIcon(android.R.drawable.arrow_up_float)
                    .setContentTitle("alarm!!")
                    .setContentText("Youtube B)")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setFullScreenIntent(pendingIntent,true)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            int notificationId = 1;
// notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, builder.build());
        }

        private Intent chooseIntent() {

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
    }



    //Alarm. set exact not opening application is closed....
    private void setYoutubeAlarm(Calendar calendar,AlarmManager alarm){

        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_ONE_SHOT|PendingIntent.FLAG_IMMUTABLE);
        Log.v(TAG,"alarm intent set");
        alarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
    }
    private void setTwitchAlarm(Calendar calendar,AlarmManager alarm){
        PendingIntent alarmIntent =  PendingIntent.getBroadcast(this, 0, twitchIntent,PendingIntent.FLAG_ONE_SHOT|PendingIntent.FLAG_IMMUTABLE);
        alarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
    }

   /* public class AlarmReceiver extends BroadcastReceiver {
        private static final String TAG = "AlarmReceiver";
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(TAG, "Received alarm");
            String videoID = APIClient.getVideoID();
            Log.v(TAG,"videoID"+videoID);
            Intent H3H3intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/watch?v="+videoID));
            startActivityForResult(H3H3intent,0);
        }
    }
*/


}
