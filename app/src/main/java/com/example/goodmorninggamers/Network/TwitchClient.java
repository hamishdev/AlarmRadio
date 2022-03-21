package com.example.goodmorninggamers.Network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goodmorninggamers.Activities.SetAlarmScreenActivity;
import com.example.goodmorninggamers.Data.StreamerChannel;
import com.example.goodmorninggamers.Data.TwitchChannel;
import com.example.goodmorninggamers.GlobalClass;
import com.example.goodmorninggamers.helpers.getLiveStreamJSONObject.YoutAPI_getLiveStream_JSONobject;
import com.example.goodmorninggamers.twitchresponse.DataItem;
import com.example.goodmorninggamers.twitchresponse.twitchResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TwitchClient {

    private  String TAG = "TwitchClient";
    private ArrayList<StreamerChannel> m_searchResults;

    public ArrayList<StreamerChannel> getChannelsFromSearch(){
        return m_searchResults;
    }
    public TwitchClient(){
        m_searchResults = new ArrayList<StreamerChannel>();
    }
    public void loadChannelsFromString(Context context, String searchInput){
        VolleyListener volleyListener = (VolleyListener) context;
        Log.v(TAG, "got here");
        VolleyLog.DEBUG = true;
String url = "https://api.twitch.tv/helix/search/channels?query=" + searchInput;
        Log.v(TAG, "url");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization","Bearer ref82ho2xug5awjywyggqnd3ilhnnt");
        headers.put("Client-ID", "cfu1ujuc54ske6vjd0c73qewbuyo1g");
        RequestQueue queue = Volley.newRequestQueue(GlobalClass.context);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.GET,url,headers,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG,"Response: " + response.toString());
                        String JsonString = response.toString();
                        Gson gson = new Gson();
                        twitchResponse obj = (twitchResponse) gson.fromJson(response.toString(),twitchResponse.class);

                        if(!obj.getData().isEmpty()){
                            DataItem channel = obj.getData().get(0);
                            String name = channel.getDisplayName();
                            Boolean verified = false;
                            String pic = channel.getThumbnailUrl();
                            long followers = 0;
                            boolean currentlyLive = channel.isIsLive();
                            String channelID = channel.getId();
                            StreamerChannel topresult = new TwitchChannel(name,verified,pic,followers, currentlyLive, channelID);
                            m_searchResults.add(topresult);
                        }
                        volleyListener.requestFinished(true);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v(TAG, "that didn't work!");
                        volleyListener.requestFinished(false);
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

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
