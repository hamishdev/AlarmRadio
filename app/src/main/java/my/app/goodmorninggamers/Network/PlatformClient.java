package my.app.goodmorninggamers.Network;

import my.app.goodmorninggamers.Channels.StreamerChannel;

import java.util.ArrayList;

public interface PlatformClient {
    ArrayList<StreamerChannel> getChannelsFromSearch();

    void loadChannelsFromString(VolleyListener context, String searchInput);

}
