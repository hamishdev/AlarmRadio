package com.example.goodmorninggamers.Activities;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.goodmorninggamers.Alarms.Alarm;

public class GoodMorningGamersAlarmBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "broadcastReceiverAlarmNotificationBuild";


    //On Alarm going off (check streamers queued and notify)((Currently just gets backup Option))
    @Override
    public void onReceive(Context context, Intent intent) {

        Alarm receivedAlarm = (Alarm)intent.getSerializableExtra("Alarm");

        //Check which streamer to get
        int ChannelChoice = findChannelToOpen(receivedAlarm);

        //Build streamer intent
        String StreamerURL = receivedAlarm.getWakeupOptions().get(ChannelChoice).getLiveContentURL();
        Intent StreamerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(StreamerURL));
        StreamerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        NotifyUser(StreamerIntent,context);

    }

    private int findChannelToOpen(Alarm alarm){
        //Check channels in priority order finding the first one that is live.
        return 2;
    }

    private void NotifyUser(Intent StreamerIntent, Context context){
        //Build Notification
        ////////////////////NEED TO REBUILD NOTIFICATION ALERTER BASED ON HOW IT INTERACTS WITH CLOSED SCREENS AND YOUTUBE VS TWITCH AND A WHOLE HEAP OF THINGS
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, StreamerIntent, PendingIntent.FLAG_IMMUTABLE);
        final int notificationId= (int) System.currentTimeMillis();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.AndroidChannelID)
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
