package my.app.goodmorninggamers.Network.JSON_POJO.YoutubeChannelResponse;

import com.google.gson.annotations.SerializedName;

public class Localized{

	@SerializedName("description")
	private String description;

	@SerializedName("title")
	private String title;

	public String getDescription(){
		return description;
	}

	public String getTitle(){
		return title;
	}
}