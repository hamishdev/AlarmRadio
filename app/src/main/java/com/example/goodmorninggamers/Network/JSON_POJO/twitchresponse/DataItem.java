package com.example.goodmorninggamers.Network.JSON_POJO.twitchresponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataItem{

	@SerializedName("game_name")
	private String gameName;

	@SerializedName("tag_ids")
	private List<Object> tagIds;

	@SerializedName("broadcaster_language")
	private String broadcasterLanguage;

	@SerializedName("is_live")
	private boolean isLive;

	@SerializedName("broadcaster_login")
	private String broadcasterLogin;

	@SerializedName("started_at")
	private String startedAt;

	@SerializedName("id")
	private String id;

	@SerializedName("display_name")
	private String displayName;

	@SerializedName("thumbnail_url")
	private String thumbnailUrl;

	@SerializedName("title")
	private String title;

	@SerializedName("game_id")
	private String gameId;

	public DataItem() {
	}

	public String getGameName(){
		return gameName;
	}

	public List<Object> getTagIds(){
		return tagIds;
	}

	public String getBroadcasterLanguage(){
		return broadcasterLanguage;
	}

	public boolean isIsLive(){
		return isLive;
	}

	public String getBroadcasterLogin(){
		return broadcasterLogin;
	}

	public String getStartedAt(){
		return startedAt;
	}

	public String getId(){
		return id;
	}

	public String getDisplayName(){
		return displayName;
	}

	public String getThumbnailUrl(){
		return thumbnailUrl;
	}

	public String getTitle(){
		return title;
	}

	public String getGameId(){
		return gameId;
	}
}
