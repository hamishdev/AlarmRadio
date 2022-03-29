package com.example.goodmorninggamers.UI_Classes;

import java.io.Serializable;

public class RingtoneOption implements Serializable {

    String m_LiveContentURL;
    String m_ringtonePictureURL;
    String m_name;


    public RingtoneOption(String LiveContentURL, String pictureURL, String name){
m_LiveContentURL= LiveContentURL;
m_ringtonePictureURL = pictureURL;
m_name = name;
    }

    public RingtoneOption(){

    }

    public String getRingtonePicture(){
        return m_ringtonePictureURL;
    }
    public String getLiveContentURL() {
        return m_LiveContentURL;
    }
    public String getName() {return m_name;}

    //Picture
    //Process that tries to get a ringtone
}
