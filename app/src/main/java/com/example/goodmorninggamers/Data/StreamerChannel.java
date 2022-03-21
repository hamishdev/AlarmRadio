package com.example.goodmorninggamers.Data;

public class StreamerChannel {

    String m_name;
    Boolean m_verified;
    String m_pic;
    long m_followers;
    boolean m_currentlyLive;
    String m_liveStreamURL;

    public StreamerChannel(String name, Boolean verified, String pic, long followers, boolean currentlyLive){

    }

    public String getPicURL(){
        return m_pic;
    }

    public String getLiveContentURL(){
        return null;
    }
}
