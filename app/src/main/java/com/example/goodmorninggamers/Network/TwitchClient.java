package com.example.goodmorninggamers.Network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.example.goodmorninggamers.Channels.StreamerChannel;
import com.example.goodmorninggamers.Channels.TwitchChannel;
import com.example.goodmorninggamers.Network.JSON_POJO.twitchresponse.DataItem;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TwitchClient implements PlatformClient {

    private  String TAG = "TwitchClient";
    private ArrayList<StreamerChannel> m_searchResults;

    @Override
    public ArrayList<StreamerChannel> getChannelsFromSearch(){
        return m_searchResults;
    }
    public TwitchClient(){
        m_searchResults = new ArrayList<StreamerChannel>();
    }
    @Override
    public void loadChannelsFromString(VolleyListener context, String searchInput){
        VolleyListener volleyListener = (VolleyListener) context;
        Log.v(TAG, "got here");
        VolleyLog.DEBUG = true;
String url = "https://api.twitch.tv/helix/search/channels?query=" + searchInput;
        Log.v(TAG, "url");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization","Bearer ref82ho2xug5awjywyggqnd3ilhnnt");
        headers.put("Client-ID", "cfu1ujuc54ske6vjd0c73qewbuyo1g");
        RequestQueue queue = Volley.newRequestQueue((Context) context);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.GET,url,headers,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG,"com.example.goodmorninggamers.Network.JSON_POJO.twitchresponse.Response: " + response.toString());
                        Gson gson = new Gson();

                        com.example.goodmorninggamers.Network.JSON_POJO.twitchresponse.Response obj = gson.fromJson(response.toString(), com.example.goodmorninggamers.Network.JSON_POJO.twitchresponse.Response.class);

                        if(!obj.getData().isEmpty()){
                            for (DataItem channel:obj.getData()
                                 ) {
                                String name = channel.getDisplayName();
                                Boolean verified = false;
                                String pic = channel.getThumbnailUrl();
                                long followers = 0;
                                boolean currentlyLive = channel.isIsLive();
                                String channelID = channel.getId();
                                StreamerChannel nextChannel = new TwitchChannel(name,verified,pic,followers, currentlyLive, channelID);
                                m_searchResults.add(nextChannel);

                            }
                        }
                        volleyListener.searchRequestFinished(true);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v(TAG, "that didn't work!");
                        volleyListener.searchRequestFinished(false);
                    }
                });

        try {
            Log.v(TAG,""+jsObjRequest.getHeaders().size());
            for (Map.Entry<String,String> entry :jsObjRequest.getHeaders().entrySet()){
                Log.v(TAG,entry.toString());
            }
        }
        catch(Exception e){
            Log.v(TAG,e.toString());
        }
        queue.add(jsObjRequest);

    }

}
