package com.example.goodmorninggamers;

import android.content.Context;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goodmorninggamers.getLiveStreamJSONObject.YoutAPI_getLiveStream_JSONobject;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONObject;

//https://developer.android.com/training/volley
public class YoutAPI_client {
    private static final String TAG = "YoutAPI_client";
    private String VideoID;
    String APIKey = "AIzaSyBErDC2FJEo492JeSC1kPYZrSzsBwdQd3g";

    public YoutAPI_client(){

    }

    public void volleyJSONRequest_H3H3LivestreamVideoID(String url){

        RequestQueue queue = Volley.newRequestQueue(GlobalClass.context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG,"Response: " + response.toString());
                        String JsonString = response.toString();
                        Gson gson = new Gson();
                        YoutAPI_getLiveStream_JSONobject obj = gson.fromJson(response.toString(),YoutAPI_getLiveStream_JSONobject.class);
                        VideoID = obj.getItems().get(0).getId().getVideoId();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v(TAG, "that didn't work!");

                    }
                });
        queue.add(jsonObjectRequest);
    }

    public String getVideoID(){
        Log.v(TAG,"video ID = "+ VideoID);
        return VideoID;
    }
    public void SendChannelVideoLivestreamRequest(String ChannelID) {

        String URL = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&channelId="+ChannelID+"&eventType=live&maxResults=25&type=video&key="+APIKey;
        //Send request
        Log.v(TAG, "Url "+ URL);
        volleyJSONRequest_H3H3LivestreamVideoID(URL);

    }
}
