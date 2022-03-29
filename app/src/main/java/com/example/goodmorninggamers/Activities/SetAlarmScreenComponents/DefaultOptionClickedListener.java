package com.example.goodmorninggamers.Activities.SetAlarmScreenComponents;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.example.goodmorninggamers.Activities.GlideHelper;
import com.example.goodmorninggamers.Activities.SetAlarmScreenActivity;
import com.example.goodmorninggamers.UI_Classes.RingtoneOption;

public class DefaultOptionClickedListener implements View.OnClickListener, GlideHelper {
private String TAG = "Default Option Clicked Listener";
private SetAlarmScreenActivity m_context;
private DefaultOptionFinishedListener defaultOptionFinishedListener;
private RingtoneOptionFinishedListener ringtoneOptionFinishedListener;
private int m_button;
private String lofiHiphopVidID="5qap5aO4i9A";
private String lofiHiphop = "https://www.youtube.com/watch?v="+lofiHiphopVidID;
private String lofiHiphopPic ="https://img.youtube.com/vi/"+lofiHiphopVidID+"/hqdefault.jpg";

public DefaultOptionClickedListener(SetAlarmScreenActivity context, DefaultButton buttonContext, Integer button){
    m_context = context;
    defaultOptionFinishedListener = buttonContext;
    ringtoneOptionFinishedListener = context;
    m_button=button;

}

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(m_context);
        builder.setItems(new String[]{"Lofi HipHop"},new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                RingtoneOption createdRingtone = new RingtoneOption(lofiHiphop,lofiHiphopPic,"Lofi Hip Hop");
                defaultOptionFinishedListener.saveDefaultOption(m_context,createdRingtone);
                ringtoneOptionFinishedListener.RingtoneOptionFinished(m_button,createdRingtone);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
}

}
