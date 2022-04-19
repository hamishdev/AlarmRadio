package my.app.goodmorninggamers.Network.JSON_POJO.YoutubeChannelResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class YoutubeChannelResponse{

	@SerializedName("kind")
	private String kind;

	@SerializedName("pageInfo")
	private PageInfo pageInfo;

	@SerializedName("etag")
	private String etag;

	@SerializedName("items")
	private List<ItemsItem> items;

	public String getKind(){
		return kind;
	}

	public PageInfo getPageInfo(){
		return pageInfo;
	}

	public String getEtag(){
		return etag;
	}

	public List<ItemsItem> getItems(){
		return items;
	}
}