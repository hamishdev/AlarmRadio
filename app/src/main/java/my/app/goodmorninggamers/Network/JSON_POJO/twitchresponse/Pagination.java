package my.app.goodmorninggamers.Network.JSON_POJO.twitchresponse;

import com.google.gson.annotations.SerializedName;

public class Pagination{

	@SerializedName("cursor")
	private String cursor;

	public String getCursor(){
		return cursor;
	}
}
