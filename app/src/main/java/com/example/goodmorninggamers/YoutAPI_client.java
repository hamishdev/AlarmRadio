package com.example.goodmorninggamers;

import android.content.Context;
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

public class YoutAPI_client {
    private static final String TAG = "YoutAPI_client";

    Context context;
    public YoutAPI_client(Context context){
        this.context = context;
    }

    public void volleyStringRequest(String url){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.v(TAG,"Response is: " + response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(TAG,"That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public String volleyJSONRequest_H3H3LivestreamVideoID(String url){


        final String[] VideoID = {null};

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG,"Response: " + response.toString());
                        String JsonString = response.toString();
                        Gson gson = new Gson();
                        YoutAPI_getLiveStream_JSONobject obj = gson.fromJson(response.toString(),YoutAPI_getLiveStream_JSONobject.class);
                        VideoID[0] = obj.getItems().get(0).getId().getVideoId();
                        return VideoID[0];
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        queue.add(jsonObjectRequest);

    }

    public JSONArray getLiveStream(String ChannelID){
        //rest request to youtubeAPI livestream URL for H3H3
        return null;
    }

    public String GetVideoIDyoutubeLivestream(String ChannelID) {
        //https://developer.android.com/training/volley
        getYoutubeLivestream;
        JSONObject parsedData =parseData(response);

        return parsedData;
    }
}
