package com.example.goodmorninggamers.Network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.goodmorninggamers.GlobalClass;
import com.example.goodmorninggamers.helpers.getLiveStreamJSONObject.YoutAPI_getLiveStream_JSONobject;
import com.google.gson.Gson;


import org.json.JSONObject;

//https://developer.android.com/training/volley
public class YoutAPI_client {
    private static final String TAG = "YoutAPI_client";
    public boolean H3H3isLive;
    private String VideoID;
    String APIKey = "AIzaSyBErDC2FJEo492JeSC1kPYZrSzsBwdQd3g";
    private static String ytChannelID;

    public YoutAPI_client(String ytChannelID){
        this.ytChannelID = ytChannelID;
        H3H3isLive =false;
        SendChannelVideoLivestreamRequest(ytChannelID);
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
                            //H3H3 is live
                            //REsponse comes back with full JSON
                            //Videos (getItems) is not empty
                            if(!obj.getItems().isEmpty()){
                                VideoID = obj.getItems().get(0).getId().getVideoId();
                                H3H3isLive = true;
                            }
                            else{
                                //Shouldn't get here
                                H3H3isLive = false;
                            }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v(TAG, "that didn't work!");

                    }
                });
        queue.add(jsonObjectRequest);
    }


}
