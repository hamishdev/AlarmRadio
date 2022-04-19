package my.app.goodmorninggamers.Channels;

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



    String m_liveContentURL;
    String m_id;

    public YoutubeChannel(String name, Boolean verified, String pic, long followers, boolean currentlyLive, String id) {
        super(name, verified, pic, followers, currentlyLive);
        m_id = id;
    }

    @Override
    public String getLiveContentURL(){
        return "https://www.youtube.com/"+m_liveContentURL+"/live";

    }


    public void setLiveContentURL(String liveContentURL) {
        m_liveContentURL = liveContentURL;
    }

    public String getID() {
        return m_id;
    }
}
