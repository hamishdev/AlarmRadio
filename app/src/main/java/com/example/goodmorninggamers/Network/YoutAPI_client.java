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


//Youtube API client should connect to the public Youtube API and return a JSON object of the response from the request
//There could be a lot of things required from the youtube API
// Channel picture, Channel name, Channel live video ID, Channel subscribers, verified status,
//Probably need a helper class which gets the information, and this class purely handles making the requests thru Volley (and maybe parsing the data into objects)((maybe))
public class YoutAPI_client {
    private static final String TAG = "YoutAPI_client";

    public YoutAPI_client(){
    }

    public String getVideoID(){
        return null;
    }


    public void SendChannelVideoLivestreamRequest(String ChannelID) {
        String APIKey = null;
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
                        Log.v(TAG,"com.example.goodmorninggamers.twitchresponse.Response: " + response.toString());
                        String JsonString = response.toString();
                        Gson gson = new Gson();
                        YoutAPI_getLiveStream_JSONobject obj = gson.fromJson(response.toString(),YoutAPI_getLiveStream_JSONobject.class);
                            //H3H3 is live
                            //REsponse comes back with full JSON
                            //Videos (getItems) is not empty
                            if(!obj.getItems().isEmpty()){
                                String VideoID = obj.getItems().get(0).getId().getVideoId();
                                Boolean H3H3isLive = true;
                            }
                            else{

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
