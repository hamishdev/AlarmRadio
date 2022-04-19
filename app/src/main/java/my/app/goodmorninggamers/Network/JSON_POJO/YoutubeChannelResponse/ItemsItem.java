package my.app.goodmorninggamers.Network.JSON_POJO.YoutubeChannelResponse;

import com.google.gson.annotations.SerializedName;

public class ItemsItem{

	@SerializedName("snippet")
	private Snippet snippet;

	@SerializedName("kind")
	private String kind;

	@SerializedName("etag")
	private String etag;

	@SerializedName("id")
	private String id;

	public Snippet getSnippet(){
		return snippet;
	}

	public String getKind(){
		return kind;
	}

	public String getEtag(){
		return etag;
	}

	public String getId(){
		return id;
	}
}