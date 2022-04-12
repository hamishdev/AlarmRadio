package com.example.goodmorninggamers.AlarmBroadcastReceiver;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.goodmorninggamers.Alarms.LiveChecker;
import com.example.goodmorninggamers.Channels.StreamerChannel;
import com.example.goodmorninggamers.Channels.TwitchChannel;
import com.example.goodmorninggamers.Channels.YoutubeChannel;
import com.example.goodmorninggamers.Network.CustomRequest;
import com.example.goodmorninggamers.Network.JSON_POJO.YoutubeSearchResponse.Item;
import com.example.goodmorninggamers.Network.JSON_POJO.YoutubeSearchResponse.Snippet;
import com.example.goodmorninggamers.Network.JSON_POJO.YoutubeSearchResponse.YoutAPI_getLiveStream_JSONobject;
import com.example.goodmorninggamers.Network.JSON_POJO.twitchresponse.DataItem;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LiveCheckerClient {
    LiveCheckerListener m_checkListener;
    Context m_context;
    String TAG = "liveListener";

    public LiveCheckerClient(LiveCheckerListener checkListener, Context context) {
        m_context = context;
        m_checkListener=checkListener;
    }

    public void check(int i, LiveChecker liveChecker) {
        //twitchCheck
        if(liveChecker.m_platform==0){
            VolleyLog.DEBUG = true;
            String url = "https://api.twitch.tv/helix/streams?user_login=" + liveChecker.m_url;
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Authorization","Bearer ref82ho2xug5awjywyggqnd3ilhnnt");
            headers.put("Client-ID", "cfu1ujuc54ske6vjd0c73qewbuyo1g");
            RequestQueue queue = Volley.newRequestQueue( m_context);
            CustomRequest jsObjRequest = new CustomRequest(Request.Method.GET,url,headers,new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.v(TAG,"com.example.goodmorninggamers.Network.JSON_POJO.twitchresponse.Response: " + response.toString());
                    Gson gson = new Gson();

                    com.example.goodmorninggamers.AlarmBroadcastReceiver.TwitchLiveCheckResponse.Response obj = gson.fromJson(response.toString(), com.example.goodmorninggamers.AlarmBroadcastReceiver.TwitchLiveCheckResponse.Response.class);

                    if(obj.getData().isEmpty()){
                        m_checkListener.liveCheckFinished(false,i);
                    }
                    else{
                        m_checkListener.liveCheckFinished(true,i);
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v(TAG, "that didn't work!");
                }
            });

            queue.add(jsObjRequest);
        }
        //youtubeCheck
        else if(liveChecker.m_platform==1){
            VolleyLog.DEBUG = true;
            String url = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&eventType=live&maxResults=25&key=AIzaSyBErDC2FJEo492JeSC1kPYZrSzsBwdQd3g&type=video&channelId=" + liveChecker.m_url;
            RequestQueue queue = Volley.newRequestQueue(m_context);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Gson gson = new Gson();
                    com.example.goodmorninggamers.AlarmBroadcastReceiver.YoutubeLiveCheckResponse.Response obj = gson.fromJson(response.toString(), com.example.goodmorninggamers.AlarmBroadcastReceiver.YoutubeLiveCheckResponse.Response.class);
                    if(obj.getItems().isEmpty()) {
                        m_checkListener.liveCheckFinished(false,i);
                    }
                    else{
                        m_checkListener.liveCheckFinished(true,i);

                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v(TAG, "that didn't work!");
                }
            });

            queue.add(jsObjRequest);
        }
    }
}
