package com.example.goodmorninggamers.Data;

public class YoutubeChannel extends StreamerChannel{


    //Youtube channel contains
    /*
    Banner Profile Pic
    Channel name
    Subscriber count
    Video count
    Whether Added to the alarm database

    -Backend data
    Channel ID

    */

    //private image m_BannerProfilePic;
    private String m_ChannelName;
    private int m_SubscriberCount;
    private int m_videoCount;
    private boolean m_addedToApp;
    //Backend
    private String m_ChannelID;



    public YoutubeChannel(String name, Boolean verified, String pic, long followers, boolean currentlyLive) {
        super(name, verified, pic, followers, currentlyLive);
    }

    //public image getBannerProfilePic(){
    //    return m_BannerProfilePic;
    //}

    public String getChannelName(){
        return m_ChannelName;
    }

    public int getSubscriberCount(){
        return m_SubscriberCount;
    }

    public int getVideoCount(){
        return m_videoCount;
    }

    public boolean getAddedToApp(){
        return m_addedToApp;
    }

    public String getChannelID(){
        return m_ChannelID;
    }


}
