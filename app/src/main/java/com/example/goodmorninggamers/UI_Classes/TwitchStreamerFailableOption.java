package com.example.goodmorninggamers.UI_Classes;

import android.content.Intent;

public class TwitchStreamerFailableOption {

    String m_ChannelID;
    public TwitchStreamerFailableOption(String ChannelID){
        m_ChannelID = ChannelID;
    }

    //process, check URL Live, return nullable Intent
    public Intent getRingtone(){
        return null;
    }
}