package com.example.goodmorninggamers.UI_Classes;

public class RingtoneOption {

    String m_LiveContentURL;
    String m_ringtonePictureURL;


    public RingtoneOption(String LiveContentURL, String pictureURL){
m_LiveContentURL= LiveContentURL;
m_ringtonePictureURL = pictureURL;
    }

    public RingtoneOption(){

    }

    public String getRingtonePicture(){
        return m_ringtonePictureURL;
    }
    public String getLiveContentURL() {
        return m_LiveContentURL;
    }

    //Picture
    //Process that tries to get a ringtone
}
