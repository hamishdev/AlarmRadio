package com.example.goodmorninggamers.Activities.SetAlarmScreen_Components;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodmorninggamers.Alarms.LiveChecker;
import com.example.goodmorninggamers.Helpers.GlideHelper;
import com.example.goodmorninggamers.Activities.SetAlarmScreen_Activity;
import com.example.goodmorninggamers.Channels.StreamerChannel;
import com.example.goodmorninggamers.Channels.YoutubeChannel;
import com.example.goodmorninggamers.Network.PlatformClient;
import com.example.goodmorninggamers.Network.TwitchClient;
import com.example.goodmorninggamers.Network.YoutubeClient;
import com.example.goodmorninggamers.Alarms.RingtoneOption;

import java.util.ArrayList;

public class AlarmOptionClickedListener implements View.OnClickListener, GlideHelper {

    private String TAG = "AlarmOptionClickedListener";
    private SetAlarmScreen_Activity m_context;
    private int m_button;
    AlarmOptionFinishedListener alarmOptionFinishedListener;
    RingtoneOptionFinishedListener ringtoneOptionFinishedListener;
    int SEARCHREQUEST = 0;
    int CHANNELREQUEST = 0;
    public AlarmOptionClickedListener (SetAlarmScreen_Activity context, StreamerButton buttonContext, int button){
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
                Log.v(TAG,"which:"+which);
                if(which==0) {
                    //twitch Stream
                    Log.v(TAG, "0:" + which);
                    EditText input = new EditText(m_context);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setGravity(Gravity.CENTER);
                    input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            if (b) {
                                input.setText("");
                                input.setTextColor(Color.BLACK);
                            }
                        }
                    });
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(m_context);
                    builder2.setView(input);
                    TextView title = new TextView(m_context);
                    title.setText("Search twitch");
                    title.setGravity(Gravity.CENTER);
                    builder2.setCustomTitle(title);
                    AlertDialog alert2 = builder2.create();
                    //on Enter button
                    input.setOnKeyListener(new CustomOnKeyListener() {

                        ImageView pic = new ImageView(m_context);
                        PlatformClient tc = new TwitchClient(m_context);

                        @Override
                        public void searchRequestFinished(boolean existence) {
                            Log.v(TAG, "Requestfinished in Listener");
                            glideResizeandLoadURL(m_context, tc.getChannelsFromSearch().get(0).getPicURL(), pic);
                            AlertDialog.Builder builder3 = new AlertDialog.Builder(m_context);

                            AlertDialog alert3 = builder3.create();
                            //recycler view
                            //add searchresultsitems
                            CustomAdapter searchResultsAdapter = new CustomAdapter(tc.getChannelsFromSearch(), m_context);
                            searchResultsAdapter.setClickListener(new CustomAdapter.ItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    StreamerChannel choice = tc.getChannelsFromSearch().get(position);
                                    RingtoneOption ringtoneOption = new RingtoneOption(choice.getLiveContentURL(), choice.getPicURL(), choice.getName(),new LiveChecker(choice.getName(),0));
                                    alarmOptionFinishedListener.saveOption(ringtoneOption, m_context);
                                    ringtoneOptionFinishedListener.RingtoneOptionFinished(m_button, ringtoneOption);
                                    alert3.dismiss();
                                }
                            });
                            RecyclerView searchResultsRecyclerView = new RecyclerView(m_context);
                            RecyclerView.LayoutManager searchResultsLayoutManager = new LinearLayoutManager(m_context);
                            searchResultsRecyclerView.setLayoutManager(searchResultsLayoutManager);
                            searchResultsRecyclerView.setAdapter(searchResultsAdapter);


                            alert3.setView(searchResultsRecyclerView);
                            alert3.show();

                            //Click which streamer you want
                            //Return to setAlarmScreen

                        }

                        @Override
                        public void ChannelRequestFinished(String url) {
                        }

                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
                            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                //SearchTwitch API
                                String searchString = input.getText().toString();
                                tc.loadChannelsFromString(this, searchString);
                                alert2.dismiss();

                            }
                            return false;
                        }
                    });

                    alert2.show();
                }
                   if(which==1){
                        Log.v(TAG,"1:"+which);
                        EditText input1_2 = new EditText(m_context);
                        input1_2.setInputType(InputType.TYPE_CLASS_TEXT);
                        input1_2.setGravity(Gravity.CENTER);
                        input1_2.setTextColor(Color.GRAY);
                        input1_2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View view, boolean b) {
                                if(b){
                                    input1_2.setText("");
                                    input1_2.setTextColor(Color.BLACK);
                                }
                            }
                        });
                        AlertDialog.Builder builder2_2 = new AlertDialog.Builder(m_context);
                        TextView title = new TextView(m_context);
                        title.setText("Search Youtube");
                        title.setGravity(Gravity.CENTER);
                        builder2_2.setCustomTitle(title);
                        builder2_2.setView(input1_2);

                        AlertDialog alert2_2 = builder2_2.create();

                        //on Enter button
                        input1_2.setOnKeyListener(new CustomOnKeyListener() {

                            CustomOnKeyListener secretVolleyListener = this;
                            YoutubeClient yc = new YoutubeClient(m_context);
                            int searchResultChosen;

                            @Override
                            public void ChannelRequestFinished(String url) {
                                YoutubeChannel choice =  (YoutubeChannel) yc.getChannelsFromSearch().get(searchResultChosen);
                                choice.setLiveContentURL(url);
                                RingtoneOption ringtoneOption = new RingtoneOption(choice.getLiveContentURL(), choice.getPicURL(), choice.getName(),new LiveChecker(choice.getID(), 1));
                                alarmOptionFinishedListener.saveOption(ringtoneOption, m_context);
                                ringtoneOptionFinishedListener.RingtoneOptionFinished(m_button, ringtoneOption);
                            }

                            @Override
                            public void searchRequestFinished(boolean existence) {

                                AlertDialog.Builder builder3 = new AlertDialog.Builder(m_context);
                                AlertDialog alert3 = builder3.create();
                                //recycler view
                                //add searchresultsitems
                                ArrayList<StreamerChannel> results = yc.getChannelsFromSearch();
                                CustomAdapter searchResultsAdapter = new CustomAdapter(results, m_context);
                                searchResultsAdapter.setClickListener(new CustomAdapter.ItemClickListener()  {

                                    @Override
                                    //On choosing searchresult
                                    public void onItemClick(View view, int position) {
                                        searchResultChosen = position;
                                        YoutubeChannel choice = (YoutubeChannel) results.get(searchResultChosen);
                                        yc.generateLiveContentURL(secretVolleyListener, choice.getID());
                                        alert3.dismiss();

                                    }

                                });
                                RecyclerView searchResultsRecyclerView = new RecyclerView(m_context);
                                RecyclerView.LayoutManager searchResultsLayoutManager = new LinearLayoutManager(m_context);
                                searchResultsRecyclerView.setLayoutManager(searchResultsLayoutManager);
                                searchResultsRecyclerView.setAdapter(searchResultsAdapter);


                                alert3.setView(searchResultsRecyclerView);
                                alert3.show();

                                //Click which streamer you want
                                //Return to setAlarmScreen
                            }

                            @Override
                            //Search for Streamers
                            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
                                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                    //SearchTwitch API
                                    String searchString = input1_2.getText().toString();
                                    yc.loadChannelsFromString(this, searchString);
                                    alert2_2.dismiss();

                                }
                                return false;
                            }
                        });

                        alert2_2.show();
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
