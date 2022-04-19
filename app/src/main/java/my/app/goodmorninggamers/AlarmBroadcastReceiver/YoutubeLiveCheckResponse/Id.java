package my.app.goodmorninggamers.AlarmBroadcastReceiver.YoutubeLiveCheckResponse;

import com.google.gson.annotations.SerializedName;

public class Id{

	@SerializedName("kind")
	private String kind;

	@SerializedName("videoId")
	private String videoId;

	public String getKind(){
		return kind;
	}

	public String getVideoId(){
		return videoId;
	}
}