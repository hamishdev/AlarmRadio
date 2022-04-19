package my.app.goodmorninggamers.Network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import my.app.goodmorninggamers.Channels.StreamerChannel;
import my.app.goodmorninggamers.Channels.YoutubeChannel;
import my.app.goodmorninggamers.Network.JSON_POJO.YoutubeChannelResponse.Snippet;
import my.app.goodmorninggamers.Network.JSON_POJO.YoutubeChannelResponse.YoutubeChannelResponse;
import my.app.goodmorninggamers.Network.JSON_POJO.YoutubeSearchResponse.Item;
import my.app.goodmorninggamers.Network.JSON_POJO.YoutubeSearchResponse.YoutAPI_getLiveStream_JSONobject;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class YoutubeClient  {
    private String TAG= "Youtube Client";
    public ArrayList<StreamerChannel> m_searchResults;
    private Context m_activityContext;
    public String APIKey = "AIzaSyBErDC2FJEo492JeSC1kPYZrSzsBwdQd3g";
    public String youtubeSearchURL = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&type=channel&key="+APIKey+"&q=";
    public String channelSearchURL = "https://youtube.googleapis.com/youtube/v3/channels?part=snippet&key="+APIKey+"&id=";

    public YoutubeClient(Context activityContext){
        m_activityContext = activityContext;
        m_searchResults = new ArrayList<StreamerChannel>();
    }

    public ArrayList<StreamerChannel> getChannelsFromSearch() {
        return m_searchResults;
    }


    public void loadChannelsFromString(VolleyListener context, String searchInput) {
        VolleyListener volleyListener = (VolleyListener) context;
        String url =youtubeSearchURL+searchInput;
        RequestQueue queue = Volley.newRequestQueue(m_activityContext);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
YoutAPI_getLiveStream_JSONobject obj = gson.fromJson(response.toString(), YoutAPI_getLiveStream_JSONobject.class);
                if(!obj.getItems().isEmpty()) {
                    for (Item i:obj.getItems()
                         ) {
                        my.app.goodmorninggamers.Network.JSON_POJO.YoutubeSearchResponse.Snippet data = i.getSnippet();

                        YoutubeChannel nextChannel = new YoutubeChannel(data.getTitle(),false,data.getThumbnails().getDefault().getUrl().toString(),0,false, data.getChannelId());
                        m_searchResults.add(nextChannel);
                    }
                }
                volleyListener.searchRequestFinished(true);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                volleyListener.searchRequestFinished(false);
            }
        });

        queue.add(jsObjRequest);

    }


    public void generateLiveContentURL(VolleyListener context, String id) {
        VolleyListener volleyListener = (VolleyListener) context;
        String url =channelSearchURL+id;
        RequestQueue queue = Volley.newRequestQueue((Context) m_activityContext);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                YoutubeChannelResponse obj = gson.fromJson(response.toString(), YoutubeChannelResponse.class);
                if(!obj.getItems().isEmpty()) {

                    Snippet data = obj.getItems().get(0).getSnippet();
                    volleyListener.ChannelRequestFinished(data.getCustomUrl());

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(jsObjRequest);

    }
}

