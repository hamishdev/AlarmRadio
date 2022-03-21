package com.example.goodmorninggamers.Data;

public class TwitchChannel extends StreamerChannel{


    String m_twitchChannelID;
    static String twitchURL = "https://www.twitch.tv/";
    public TwitchChannel(String name, Boolean verified, String pic, long followers, boolean currentlyLive, String ChannelID) {
        super(name, verified, pic, followers, currentlyLive);
        m_twitchChannelID = ChannelID;
    }



    @Override
    public String getLiveContentURL() {
        return twitchURL + m_twitchChannelID;
    }
}
