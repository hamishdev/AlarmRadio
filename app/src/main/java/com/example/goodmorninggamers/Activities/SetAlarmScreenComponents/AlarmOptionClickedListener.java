package com.example.goodmorninggamers.Activities.SetAlarmScreenComponents;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.goodmorninggamers.Activities.GlideHelper;
import com.example.goodmorninggamers.Activities.SetAlarmScreenActivity;
import com.example.goodmorninggamers.Channels.StreamerChannel;
import com.example.goodmorninggamers.Network.TwitchClient;
import com.example.goodmorninggamers.UI_Classes.RingtoneOption;

public class AlarmOptionClickedListener implements View.OnClickListener, GlideHelper {

    private String TAG = "AlarmOptionClickedListener";
    private SetAlarmScreenActivity m_context;
    private int m_button;
    AlarmOptionFinishedListener alarmOptionFinishedListener;
    RingtoneOptionFinishedListener ringtoneOptionFinishedListener;
    public AlarmOptionClickedListener (SetAlarmScreenActivity context, StreamerButton buttonContext, int button){
        m_context=context;
        alarmOptionFinishedListener = buttonContext;
        m_button=button;
        ringtoneOptionFinishedListener = context;
    }
    public void onClick(View arg0) {


        String[] options = {"Twitch stream", "Youtube stream"};
        AlertDialog.Builder builder = new AlertDialog.Builder(m_context);
        builder.setItems(options, new DialogInterface.OnClickListener() {

            //FirstRingtoneOption search Streamer
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    //twitch Stream
                    case 0:

                        final EditText input = new EditText(m_context);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(m_context);
                        builder2.setView(input);
                        AlertDialog alert2 = builder2.create();
                        //on Enter button
                        input.setOnKeyListener(new CustomOnKeyListener() {

                            ImageView pic = new ImageView(m_context);
                            TwitchClient tc = new TwitchClient();
                            @Override
                            public void requestFinished(boolean existence) {
                                Log.v(TAG, "Requestfinished in Listener");
                                glideResizeandLoadURL(m_context, tc.getChannelsFromSearch().get(0).getPicURL(),pic);
                                AlertDialog.Builder builder3 = new AlertDialog.Builder(m_context);

                                builder3.setPositiveButton("Add streamer",new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialog,int which) {
                                        StreamerChannel choice = tc.getChannelsFromSearch().get(0);
                                        RingtoneOption ringtoneOption = new RingtoneOption(choice.getLiveContentURL(), choice.getPicURL());
                                        alarmOptionFinishedListener.saveOption(ringtoneOption,m_context);
                                        ringtoneOptionFinishedListener.RingtoneOptionFinished(m_button,ringtoneOption);

                                    }
                                });

                                AlertDialog alert3 = builder3.create();
                                String url = tc.getChannelsFromSearch().get(0).getPicURL();
                                alert3.setTitle(url);
                                ImageView pic = new ImageView(m_context);
                                alert3.setView(pic);
                                glideResizeandLoadURL(m_context,url,pic);
                                alert3.show();

                                //Click which streamer you want
                                //Return to setAlarmScreen

                            }

                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
                                if (keyCode==KeyEvent.KEYCODE_ENTER) {
                                    //SearchTwitch API
                                    String searchString = input.getText().toString();
                                    tc.loadChannelsFromString(this,searchString);
                                    alert2.dismiss();

                                }
                                return false;
                            }
                        });

                        alert2.show();

                        //youtubeSearchDialog
                        //TODO
                    case 1:
                }

            }
        });
        AlertDialog alert = builder.create();

        alert.show();
        //Show search youtube/twitch dialogue
        //Show input text dialogue
        //Show returned results dialogue
        //Add Option to SetAlarm activity
    }

}
