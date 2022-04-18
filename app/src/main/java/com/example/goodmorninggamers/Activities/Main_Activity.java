package com.example.goodmorninggamers.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.goodmorninggamers.Alarms.Alarm;
import com.example.goodmorninggamers.Alarms.ToggleListener;
import com.example.goodmorninggamers.PersistentData.AlarmDao;
import com.example.goodmorninggamers.PersistentData.AppDatabase;
import com.example.goodmorninggamers.Alarms.AlarmAdapter;
import com.example.goodmorninggamers.R;
import com.example.goodmorninggamers.Alarms.AlarmCreator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


/**

 * This class is the main activity

 * And output

 * @version 1.0

 * @author Hamish Burns

 */
public class Main_Activity extends AppCompatActivity implements ToggleListener {

    private static final String TAG = "MainActivity";


    private static final int SETALARM_ACTIVITY_REQUEST_CODE = 0;
    private static final int EDITALARM_ACTIVITY_REQUESTCODE= 2;
    private static final int ALARMHOMESCREEN_ACTIVITY_REQUEST_CODE = 1;

    public static String AndroidChannelID = "Alarm";
    public ArrayList<Alarm> m_alarms;
    public AlarmAdapter alarmArrayAdapter;
    public AlarmCreator m_alarmCreator = new AlarmCreator();
    public AppDatabase db;
    public AlarmDao alarmDao;


    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {


        //ONCREATE
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        createNotificationChannel();

        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"database-1").allowMainThreadQueries().build();
        alarmDao = db.alarmDao();
        m_alarms = (ArrayList<Alarm>)  alarmDao.getAll();

        //INITIALISE ALARMS
        //Initialise alarm list
        if(m_alarms ==null){
            m_alarms = new ArrayList<Alarm>(){};
        };
        Log.v(TAG,m_alarms.size()+"");

        m_alarms.sort((o1,o2) ->o1.time.compareTo(o2.time));
        alarmArrayAdapter = new AlarmAdapter(this, m_alarms);
        //Initialise alarm ListView
        ListView alarmsListView = (ListView) findViewById(R.id.list);
        alarmsListView.setAdapter(alarmArrayAdapter);


        FloatingActionButton setAlarmButton = (FloatingActionButton) findViewById(R.id.setAlarmActionButton);

        //SET ALARM SCREEN
        final Intent setAlarmActivity = new Intent(this, SetAlarmScreen_Activity.class);
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivityForResult(setAlarmActivity, SETALARM_ACTIVITY_REQUEST_CODE);

            }
        });

    }

    //New activity result handling ((Just using for editAlarm atm))
    ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        // ToDo : Do your stuff...
                        Alarm editedAlarm = (Alarm)result.getData().getSerializableExtra("alarm");
                        int indexOf=-1;
                        int i=0;
                        for (Alarm a: m_alarms
                             ) {
                            if(a.id==editedAlarm.id){
                                indexOf=i;
                            }
                            i++;
                        }
                        m_alarms.set(indexOf,editedAlarm);
                        alarmDao.updateAlarms(m_alarms);
                        m_alarmCreator.editAlarm(Main_Activity.this,m_alarms.get(indexOf));
                        alarmArrayAdapter.notifyDataSetChanged();

                    }
                }
            });
    /**   // On SetAlarmScreen being completed
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SETALARM activity with an OK result
        if (requestCode == SETALARM_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // get Alarm from SetAlarmScreen

                    Log.v(TAG,data.getSerializableExtra("alarm").toString());
                    Alarm alarm = (Alarm) data.getSerializableExtra("alarm");
                    Log.v(TAG,"alarmurl"+alarm.getWakeupOptions().get(0).getLiveContentURL());
                    m_alarms.add(alarm);
                    m_alarms.sort((o1,o2) ->o1.time.compareTo(o2.time));
                    Log.v(TAG,"alarms:"+m_alarms.size());
                    m_alarmCreator.createAlarm(this, m_alarms.get(m_alarms.size() - 1));
                    alarmDao.insertAll(alarm);

                    alarmArrayAdapter.notifyDataSetChanged();
                    Log.v(TAG, "All alarms: " + m_alarms.get(0).ToString());




            }
            if(resultCode== RESULT_CANCELED){

            }
        }


    }


    /**
     *
     */
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

    @Override
    public void onStop(){
        //Save alarms
        alarmDao.updateAlarms(m_alarms);
        super.onStop();
    }

    //Alarm turned OFF via main screen toggle
    @Override
    public void turnOffAlarm(Alarm alarm) {
        m_alarmCreator.deleteAlarm(this,alarm);

    }


    //Alarm turned ON via main screen toggle
    @Override
    public void turnOnAlarm(Alarm alarm) {
        Calendar now = Calendar.getInstance();
        //Don't switch on in-the-past alarms
        if(now.compareTo(alarm.getCalendarTime())<0) {
            m_alarmCreator.createAlarm(this, alarm);
        }
    }

    @Override
    public void deleteAlarm(Alarm currentAlarm) {
        alarmDao.delete(currentAlarm);
        m_alarmCreator.deleteAlarm(this,currentAlarm);
    }

    @Override
    public void editAlarm(Alarm currentAlarm){
        final Intent editAlarmActivity = new Intent(this, EditAlarm_Activity.class);
        editAlarmActivity.putExtra("Alarm",currentAlarm);
        activityResultLaunch.launch(editAlarmActivity);
    }
}
