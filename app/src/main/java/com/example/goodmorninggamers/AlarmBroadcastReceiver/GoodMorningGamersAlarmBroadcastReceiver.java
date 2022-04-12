package com.example.goodmorninggamers.AlarmBroadcastReceiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.goodmorninggamers.Activities.Main_Activity;
import com.example.goodmorninggamers.Alarms.Alarm;
import com.example.goodmorninggamers.Alarms.RingtoneOption;

public class GoodMorningGamersAlarmBroadcastReceiver extends BroadcastReceiver implements LiveCheckerListener {

    private static final String TAG = "broadcastReceiverAlarmNotificationBuild";
    private int ChannelChoice =2;
    Alarm m_receivedAlarm;
    Context m_context;
    Boolean alreadyThrown = false;

    //On Alarm going off (check streamers queued and notify)((Currently just gets backup Option))
    @Override
    public void onReceive(Context context, Intent intent) {
        m_context =context;
        m_receivedAlarm = (Alarm)intent.getSerializableExtra("alarm");

        //Check which streamer to get
        findChannelToOpen();


    }

    private void findChannelToOpen(){
        //Check channels in priority order finding the first one that is live.
        LiveCheckerListener checkListener = this;
        LiveCheckerClient client = new LiveCheckerClient(checkListener,m_context);
        int i = 0;
        for(RingtoneOption option : m_receivedAlarm.getWakeupOptions()){
            if(option!=null){
                if(option.getLiveChecker()!=null){
                    client.check(i,option.getLiveChecker());

                }
            }
            i++;
        }
    }


    @Override
    public void liveCheckFinished(Boolean live,int option) {

        if(live&&!alreadyThrown){
            alreadyThrown = true;
          ChannelChoice =option;
          throwAlarm();

        }

    }

    private void throwAlarm() {

        //Build streamer intent
        String StreamerURL = m_receivedAlarm.getWakeupOptions().get(ChannelChoice).getLiveContentURL();
        Log.v(TAG,"alarm: "+StreamerURL);
        Intent StreamerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(StreamerURL));
        StreamerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        NotifyUser(StreamerIntent,m_context);
    }


    private void NotifyUser(Intent StreamerIntent, Context context){
        //Build Notification
        ////////////////////NEED TO REBUILD NOTIFICATION ALERTER BASED ON HOW IT INTERACTS WITH CLOSED SCREENS AND YOUTUBE VS TWITCH AND A WHOLE HEAP OF THINGS
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, StreamerIntent, PendingIntent.FLAG_IMMUTABLE);
        final int notificationId= (int) System.currentTimeMillis();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Main_Activity.AndroidChannelID)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("alarm!!")
                .setContentText("content..,..")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setFullScreenIntent(pendingIntent, true)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        //Trigger Notification
        notificationManager.notify(notificationId, builder.build());
    }
}
