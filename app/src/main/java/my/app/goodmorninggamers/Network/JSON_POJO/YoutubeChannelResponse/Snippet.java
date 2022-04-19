package my.app.goodmorninggamers.Network.JSON_POJO.YoutubeChannelResponse;

import com.google.gson.annotations.SerializedName;

public class Snippet{

	@SerializedName("customUrl")
	private String customUrl;

	@SerializedName("country")
	private String country;

	@SerializedName("publishedAt")
	private String publishedAt;

	@SerializedName("localized")
	private Localized localized;

	@SerializedName("description")
	private String description;

	@SerializedName("title")
	private String title;

	@SerializedName("thumbnails")
	private Thumbnails thumbnails;

	public String getCustomUrl(){
		return customUrl;
	}

	public String getCountry(){
		return country;
	}

	public String getPublishedAt(){
		return publishedAt;
	}

	public Localized getLocalized(){
		return localized;
	}

	public String getDescription(){
		return description;
	}

	public String getTitle(){
		return title;
	}

	public Thumbnails getThumbnails(){
		return thumbnails;
	}
}