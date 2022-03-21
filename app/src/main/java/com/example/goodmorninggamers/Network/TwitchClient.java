package com.example.goodmorninggamers.Network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

public class TwitchClient {

    private  String TAG = "TwitchClient";
    public ArrayList<StreamerChannel> getChannelsFromString(String searchInput){
        ArrayList<StreamerChannel> searchResults = new ArrayList<StreamerChannel>();
        Log.v(TAG, "got here");

String url = "https://api.twitch.tv/helix/search/channels?query=" + searchInput;
        Log.v(TAG, "url");
        RequestQueue queue = Volley.newRequestQueue(GlobalClass.context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

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
                            searchResults.add(topresult);
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v(TAG, "that didn't work!");

                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer ref82ho2xug5awjywyggqnd3ilhnnt");
                params.put("Client-ID", "cfu1ujuc54ske6vjd0c73qewbuyo1g");

                return params;
            }
        };
        queue.add(jsonObjectRequest);

        return searchResults;
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
