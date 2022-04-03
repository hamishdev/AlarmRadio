package com.example.goodmorninggamers.Network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.goodmorninggamers.Channels.StreamerChannel;
import com.example.goodmorninggamers.Channels.YoutubeChannel;
import com.example.goodmorninggamers.GlobalClass;
import com.example.goodmorninggamers.Network.YoutubeChannelResponse.ItemsItem;
import com.example.goodmorninggamers.Network.YoutubeChannelResponse.YoutubeChannelResponse;
import com.example.goodmorninggamers.Network.YoutubeSearchResponse.Item;
import com.example.goodmorninggamers.Network.YoutubeSearchResponse.Snippet;
import com.example.goodmorninggamers.Network.YoutubeSearchResponse.YoutAPI_getLiveStream_JSONobject;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.stream.Stream;

public class YoutubeClient  {
    private String TAG= "Youtube Client";
    public ArrayList<StreamerChannel> m_searchResults;
    public String APIKey = "AIzaSyBErDC2FJEo492JeSC1kPYZrSzsBwdQd3g";
    public String youtubeSearchURL = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&type=channel&key="+APIKey+"&q=";
    public String channelSearchURL = "https://youtube.googleapis.com/youtube/v3/channels?part=snippet&key="+APIKey+"&id=";

    public YoutubeClient(){
        m_searchResults = new ArrayList<StreamerChannel>();
    }

    public ArrayList<StreamerChannel> getChannelsFromSearch() {
        return m_searchResults;
    }


    public void loadChannelsFromString(VolleyListener context, String searchInput) {
        VolleyListener volleyListener = (VolleyListener) context;
        String url =youtubeSearchURL+searchInput;
        Log.v(TAG, "url");
        RequestQueue queue = Volley.newRequestQueue(GlobalClass.context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
YoutAPI_getLiveStream_JSONobject obj = gson.fromJson(response.toString(), YoutAPI_getLiveStream_JSONobject.class);
                if(!obj.getItems().isEmpty()) {
                    for (Item i:obj.getItems()
                         ) {
                        Snippet data = i.getSnippet();

                        YoutubeChannel nextChannel = new YoutubeChannel(data.getTitle(),false,data.getThumbnails().getDefault().getUrl().toString(),0,false, data.getChannelId());
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

        queue.add(jsObjRequest);

    }


    public void generateLiveContentURL(VolleyListener context, String id) {
        VolleyListener volleyListener = (VolleyListener) context;
        String url =channelSearchURL+id;
        Log.v(TAG, "url");
        RequestQueue queue = Volley.newRequestQueue(GlobalClass.context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                YoutubeChannelResponse obj = gson.fromJson(response.toString(), YoutubeChannelResponse.class);
                if(!obj.getItems().isEmpty()) {

                    com.example.goodmorninggamers.Network.YoutubeChannelResponse.Snippet data = obj.getItems().get(0).getSnippet();
                    volleyListener.ChannelRequestFinished(data.getCustomUrl());

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

