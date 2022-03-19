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

    public YoutubeChannel( String ChnnlName, int SubCount, int VidCount, boolean addedToApp, String ChnlID){
        //m_BannerProfilePic = bannerPFP;
        m_ChannelName = ChnnlName;
m_SubscriberCount = SubCount;
m_videoCount =VidCount;
m_addedToApp = addedToApp;
m_ChannelID = ChnlID;
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
