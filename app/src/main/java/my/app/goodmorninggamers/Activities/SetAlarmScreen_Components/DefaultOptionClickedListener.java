package my.app.goodmorninggamers.Activities.SetAlarmScreen_Components;

import static my.app.goodmorninggamers.Activities.Main_Activity.YOUTUBE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import my.app.goodmorninggamers.Alarms.LiveChecker;
import my.app.goodmorninggamers.Helpers.GlideHelper;
import my.app.goodmorninggamers.Alarms.RingtoneOption;

public class DefaultOptionClickedListener implements View.OnClickListener, GlideHelper {
private String TAG = "Default Option Clicked Listener";
private Activity m_context;
private DefaultOptionFinishedListener defaultOptionFinishedListener;
private RingtoneOptionFinishedListener ringtoneOptionFinishedListener;
private int m_button;
private String lofiHiphopVidID="5qap5aO4i9A";
private String lofiHiphop = "https://www.youtube.com/watch?v="+lofiHiphopVidID;
private String lofiHiphopPic ="https://img.youtube.com/vi/"+lofiHiphopVidID+"/hqdefault.jpg";

public DefaultOptionClickedListener(Activity context, DefaultButton buttonContext, Integer button){
    m_context = context;
    defaultOptionFinishedListener = buttonContext;
    ringtoneOptionFinishedListener = (RingtoneOptionFinishedListener) context;
    m_button=button;

}

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(m_context);
        builder.setItems(new String[]{"Lofi HipHop"},new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                RingtoneOption createdRingtone = new RingtoneOption(lofiHiphop,lofiHiphopPic,"Lofi Hip Hop",new LiveChecker("UCSJ4gkVC6NrvII8umztf0Ow",YOUTUBE),YOUTUBE );
                defaultOptionFinishedListener.saveDefaultOption(m_context,createdRingtone);
                ringtoneOptionFinishedListener.RingtoneOptionFinished(m_button,createdRingtone);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
}

}
