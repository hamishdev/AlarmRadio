package my.app.goodmorninggamers.Channels;

public class TwitchChannel extends StreamerChannel{


    String m_twitchChannelID;
    String m_twitchName;
    static String twitchURL = "https://www.twitch.tv/";
    public TwitchChannel(String name, Boolean verified, String pic, long followers, boolean currentlyLive, String ChannelID) {
        super(name, verified, pic, followers, currentlyLive);
        m_twitchChannelID = ChannelID;
        m_twitchName = name;

    }



    @Override
    public String getLiveContentURL() {
        return twitchURL + m_twitchName;
    }
}
