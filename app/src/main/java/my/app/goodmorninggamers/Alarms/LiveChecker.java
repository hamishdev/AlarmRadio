package my.app.goodmorninggamers.Alarms;

import java.io.Serializable;

public class LiveChecker implements Serializable {

    public String m_url;
    public int m_platform;

    public LiveChecker(String url, int platform){
        m_url= url;
        m_platform=platform;
    }
}
