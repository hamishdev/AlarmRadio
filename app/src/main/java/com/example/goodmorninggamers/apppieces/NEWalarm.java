package com.example.goodmorninggamers.apppieces;

import com.example.goodmorninggamers.Data.StreamerChannel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NEWalarm {


    private Calendar m_time;
    private ArrayList<StreamerChannel> m_wakeUpChannels;


    public NEWalarm(Calendar time, ArrayList<StreamerChannel> wakeUpChannels){
        m_time = time;
        m_wakeUpChannels = wakeUpChannels;
    }

    public String getTwelvehourclock(){
        return null;
    }

    public ArrayList<StreamerChannel> getWakeUpChannels(){
        return null;
    }
}

