package com.example.goodmorninggamers.invisibleapppieces;

public class RingtoneOption {

    String m_LiveContentURL;
    String m_ringtonePictureURL;


    public RingtoneOption(String LiveContentURL, String RingtonePictureID){
m_LiveContentURL= LiveContentURL;
m_ringtonePictureURL = RingtonePictureID;
    }

    public RingtoneOption(){

    }

    public String getRingtonePictureID(){
        return m_ringtonePictureURL;
    }
    public String getLiveContentURL() {
        return m_LiveContentURL;
    }

    //Picture
    //Process that tries to get a ringtone
}
