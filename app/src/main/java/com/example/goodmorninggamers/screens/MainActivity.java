package com.example.goodmorninggamers.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.goodmorninggamers.apppieces.Alarm;
import com.example.goodmorninggamers.AlarmAdapter;
import com.example.goodmorninggamers.R;
import com.example.goodmorninggamers.apppieces.AlarmCreator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    private static final int SETALARM_ACTIVITY_REQUEST_CODE = 0;
    private static final int ALARMHOMESCREEN_ACTIVITY_REQUEST_CODE = 1;

    public static String AndroidChannelID = "Alarm";
    public ArrayList<Alarm> m_alarms;
    public AlarmAdapter alarmArrayAdapter;
    public AlarmCreator m_alarmCreator = new AlarmCreator();
    @Override
    public void onCreate(Bundle savedInstanceState) {


        //ONCREATE
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmhomescreen_activty);
        createNotificationChannel();


        //INITIALISE ALARMS
        //Initialise alarm list
        if(m_alarms ==null){
            m_alarms = new ArrayList<Alarm>(){};
        };
        alarmArrayAdapter = new AlarmAdapter(this, m_alarms);
        //Initialise alarm ListView
        ListView alarmsListView = (ListView) findViewById(R.id.AlarmListView);
        alarmsListView.setAdapter(alarmArrayAdapter);


        //SET ALARM SCREEN
        final Button setAlarmButton = (Button) findViewById(R.id.alarmScreenButton);
        final Intent setAlarmActivity = new Intent(this, SetAlarmScreenActivity.class);
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivityForResult(setAlarmActivity, SETALARM_ACTIVITY_REQUEST_CODE);

            }
        });

    }


    // On SetAlarmScreen being completed
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SETALARM activity with an OK result
        if (requestCode == SETALARM_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // get Alarm from SetAlarmScreen
                Alarm alarm = (Alarm)data.getSerializableExtra("alarm");
                m_alarms.add(alarm);

                m_alarmCreator.createAlarm(m_alarms.get(m_alarms.size()-1));
                alarmArrayAdapter.notifyDataSetChanged();
                Log.v(TAG,"All alarms: "+ m_alarms.get(0).ToString());


            }
        }
    }


    //Create notification channel?
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





}
