package com.example.goodmorninggamers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.goodmorninggamers.screens.SetAlarmScreenActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=-5KAN9_CzSA"));
    Intent twitchIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.twitch.tv/sneakylol"));
    YoutAPI_client APIClient = new YoutAPI_client();


    int alarmHour;
    int alarmMinute;
    String AlarmTime;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        AlarmTime="null";
        youtubeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        twitchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        checkLiveStream();
        final Intent alarmScreenActivityIntent = new Intent(this, SetAlarmScreenActivity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        final Button detailsButton = (Button) findViewById(R.id.detailsButton);
        final TextView alarmtime = findViewById(R.id.alarmTime);
        final Button alarmScreenButton = (Button) findViewById(R.id.AlarmScreenButton);
        final Button testYoutubeAPIIntentButton= (Button) findViewById(R.id.TestYoutubeAPIIntentBUtton);


        //ALARM SCREEN
        alarmScreenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivityForResult(alarmScreenActivityIntent,SECOND_ACTIVITY_REQUEST_CODE);

            }
        });

        //DETAILS
        detailsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                alarmtime.setText("AlarmTime: "+AlarmTime);

            }
        });

        //TEST YOUTUBE API
        testYoutubeAPIIntentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String videoID = APIClient.getVideoID();
                Log.v(TAG,"videoID"+videoID);
                startActivityForResult(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/watch?v="+videoID)),0);

            }
        });


    }

    private void checkLiveStream() {
        //String H3H3channelID = "UCLtREJY21xRfCuEKvdki1Kw";
        String testChannelID = "UCBIe28uoEnt_LEdNFbWankA";
        APIClient.SendChannelVideoLivestreamRequest(testChannelID);
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
        Calendar CalendarAlarmTime = Calendar.getInstance();
        //Set this alarm to the public field alarm times set in alternate view
        CalendarAlarmTime.set(Calendar.HOUR_OF_DAY,alarmHour);
        CalendarAlarmTime.set(Calendar.MINUTE,alarmMinute);

        //setTwitchAlarm(CalendarAlarmTime,myAlarm);
        setYoutubeAlarm(CalendarAlarmTime,myAlarm);



    }

    private void setTestAlarm(Calendar calendar, AlarmManager alarm){
        Log.v(TAG,"test alarm");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channelID")
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("alarm!!")
                .setContentText("Youtube B)")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // request code and flags not added for demo purposes
        Intent testIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=0mgu0n81nYE"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, testIntent , PendingIntent.FLAG_ONE_SHOT|PendingIntent.FLAG_IMMUTABLE);

        builder.setFullScreenIntent(pendingIntent, true); // THIS HERE is the full-screen intent
    }
    //Alarm. set exact not opening application is closed....
    private void setYoutubeAlarm(Calendar calendar,AlarmManager alarm){

        Intent intent = new Intent(getApplicationContext(),AlarmReceiver.class);
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
