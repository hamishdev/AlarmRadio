package com.example.goodmorninggamers.Network;

import com.example.goodmorninggamers.Channels.StreamerChannel;

import java.util.ArrayList;

public class YoutubeClient implements PlatformClient {
    @Override
    public ArrayList<StreamerChannel> getChannelsFromSearch() {
        return null;
    }

    @Override
    public void loadChannelsFromString(VolleyListener context, String searchInput) {

    }
}
