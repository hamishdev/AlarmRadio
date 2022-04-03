package com.example.goodmorninggamers.Network;

import com.example.goodmorninggamers.Channels.StreamerChannel;

import java.util.ArrayList;

public interface PlatformClient {
    ArrayList<StreamerChannel> getChannelsFromSearch();

    void loadChannelsFromString(VolleyListener context, String searchInput);

}
