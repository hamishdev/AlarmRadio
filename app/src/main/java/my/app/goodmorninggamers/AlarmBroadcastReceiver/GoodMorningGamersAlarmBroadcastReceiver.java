package my.app.goodmorninggamers.AlarmBroadcastReceiver;

import static my.app.goodmorninggamers.Activities.Main_Activity.YOUTUBE;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import my.app.goodmorninggamers.Activities.FullScreenAlarm;
import my.app.goodmorninggamers.Activities.Main_Activity;
import my.app.goodmorninggamers.Alarms.Alarm;
import my.app.goodmorninggamers.Alarms.AlarmCreator;
import my.app.goodmorninggamers.Alarms.RingtoneOption;
import my.app.goodmorninggamers.R;

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
        //(Checks youtube and twitch in background asynchronously and returns when a stream is found)
        findChannelToOpen();
        
        //rescheduleAlarm();

    }

    private void rescheduleAlarm() {
        AlarmCreator ac = new AlarmCreator();
        ac.createAlarm(m_context,m_receivedAlarm);
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
        if(getWakeup().getPlatform()==YOUTUBE){
            String StreamerURL = getWakeup().getLiveContentURL();
            Intent StreamerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(StreamerURL));
            StreamerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            NotifyUserYoutube(StreamerIntent,m_context);
        }
        else{
            String StreamerURL = getWakeup().getLiveContentURL();
            Intent StreamerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(StreamerURL));
            StreamerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            NotifyUserTwitch(StreamerIntent,m_context);
        }

    }


    private void NotifyUserYoutube(Intent StreamerIntent, Context context){
        //Build Notification
        ////////////////////NEED TO REBUILD NOTIFICATION ALERTER BASED ON HOW IT INTERACTS WITH CLOSED SCREENS AND YOUTUBE VS TWITCH AND A WHOLE HEAP OF THINGS
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, StreamerIntent, PendingIntent.FLAG_IMMUTABLE);
        final int notificationId= (int) System.currentTimeMillis();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Main_Activity.AndroidChannelID)
                //.setSilent(false)
                .setSmallIcon(R.drawable.neveralone_icon_current)
                .setContentTitle("Watch "+m_receivedAlarm.ringtoneOptions.get(ChannelChoice).getName() +"?")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setFullScreenIntent(pendingIntent, true)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        //Trigger Notification
        notificationManager.notify(notificationId, builder.build());
    }

    private void NotifyUserTwitch(Intent StreamerIntent, Context context){
        Intent intent = new Intent(context, FullScreenAlarm.class);
        PendingIntent twitchTestone = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Main_Activity.AndroidChannelID)
                //.setSilent(false)
                .setSmallIcon(R.drawable.neveralone_icon_current)
                .setContentTitle("Watch "+m_receivedAlarm.ringtoneOptions.get(ChannelChoice).getName() +"?")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setFullScreenIntent(twitchTestone, true)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        //Trigger Notification
        final int notificationId= (int) System.currentTimeMillis();
        notificationManager.notify(notificationId, builder.build());
    }

    private RingtoneOption getWakeup(){
        return m_receivedAlarm.getWakeupOptions().get(ChannelChoice);
    }
}
