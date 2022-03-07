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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.goodmorninggamers.AlarmReceiver;
import com.example.goodmorninggamers.R;
import com.example.goodmorninggamers.YoutAPI_client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    public ArrayList<Alarm> myAlarmArray;
    public ArrayAdapter<Alarm> alarmArrayAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {


        //ONCREATE
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmhomescreen_activty);

        APIClient = new YoutAPI_client(currentYTchannelID);
        ChannelID="Alarm";
        createNotificationChannel();
        AlarmTime="null";
        if(myAlarmArray==null){
            myAlarmArray = new ArrayList<Alarm>(){};
        };

        youtubeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        twitchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final Intent alarmScreenActivityIntent = new Intent(this, SetAlarmScreenActivity.class);

        alarmArrayAdapter = new ArrayAdapter<Alarm>(this, android.R.layout.simple_list_item_1,myAlarmArray);
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
                turnAlarmFragmentIntoAlarm(returnString);
                Log.v(TAG,"is there any alarms here lul"+myAlarmArray.get(0).ToString());


            }
        }
    }

    private void turnAlarmFragmentIntoAlarm(String alarmFragment){
        // set text view with string
        TextView textView = (TextView) findViewById(R.id.alarmTime);
        textView.setText(alarmFragment);

        // set alarm time
        AlarmTime = alarmFragment;
        setAlarmFields(alarmFragment);
        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,alarmHour);
        calendar.set(Calendar.MINUTE,alarmMinute);
        long calendarTimeInMillis =calendar.getTimeInMillis();

        setAlarm();
        myAlarmArray.add(new Alarm(calendarTimeInMillis));
        alarmArrayAdapter.notifyDataSetChanged();
        Log.v(TAG,"alarm time set!");
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
class Alarm{
    private static final int TODAY =0;
    private static final int TOMORROW =1;
    private static final int ANYOTHER =2;
    private Calendar timeOfAlarm;
    private int dayInRelationToToday;
    private Calendar today;
    private int amPM;

    public Alarm(long timeInMillis){
        timeOfAlarm = Calendar.getInstance();
        timeOfAlarm.setTimeInMillis(timeInMillis);
        today = Calendar.getInstance();
        SetAMPM();
        SetDayInRelationToToday();


    }
    public void SetAMPM(){
        amPM = timeOfAlarm.get(Calendar.AM_PM);
    }

    private void SetDayInRelationToToday(){
        //if date && year matches
        if(timeOfAlarm.get(Calendar.DAY_OF_YEAR)==today.get(Calendar.DAY_OF_YEAR)&&timeOfAlarm.get(Calendar.YEAR)==today.get(Calendar.YEAR)){
            dayInRelationToToday=TODAY;
        }
        //if year matches && day == next one
        else if(timeOfAlarm.get(Calendar.YEAR)==today.get(Calendar.YEAR)&&timeOfAlarm.get(Calendar.DAY_OF_YEAR)==today.get(Calendar.DAY_OF_YEAR)+1){
            dayInRelationToToday=TOMORROW;
        }
        //NOT TODAY OR TOMORROW
        else {
            dayInRelationToToday=ANYOTHER;
        }

    }

    public String Day(){
        switch(dayInRelationToToday){
            case 0:
                return "Today";
            case 1:
                return "Tomorrow";
            case 2:
                return timeOfAlarm.get(Calendar.DAY_OF_MONTH)+" "+timeOfAlarm.get(Calendar.MONTH);
        }
        return "ERORR";
    }

    public String TimeToString(){
        return timeOfAlarm.get(Calendar.HOUR)+":"+timeOfAlarm.get(Calendar.MINUTE)+(amPM==Calendar.AM?"am":"pm");
    }
    public String ToString(){
        String alarmTimeToString = new SimpleDateFormat("MM/dd/yyyy").format(timeOfAlarm.getTime());
        return alarmTimeToString;
    }



}
