package my.app.goodmorninggamers.Alarms;

import java.io.Serializable;

public class RingtoneOption implements Serializable {

    String m_LiveContentURL;
    String m_ringtonePictureURL;
    String m_name;
    LiveChecker m_liveChecker;

    public RingtoneOption(String LiveContentURL, String pictureURL, String name, LiveChecker liveChecker){
        m_liveChecker=liveChecker;
m_LiveContentURL= LiveContentURL;
m_ringtonePictureURL = pictureURL;
m_name = name;
    }


    public String getRingtonePicture(){
        return m_ringtonePictureURL;
    }
    public String getLiveContentURL() {
        return m_LiveContentURL;
    }
    public String getName() {return m_name;}

    public LiveChecker getLiveChecker() {
        return m_liveChecker;
    }

    //Picture
    //Process that tries to get a ringtone
}
