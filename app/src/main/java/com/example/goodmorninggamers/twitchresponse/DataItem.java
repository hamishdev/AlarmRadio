package com.example.goodmorninggamers.twitchresponse;

import java.util.List;

public class DataItem{
	private String gameName;
	private List<Object> tagIds;
	private String broadcasterLanguage;
	private boolean isLive;
	private String broadcasterLogin;
	private String startedAt;
	private String id;
	private String displayName;
	private String thumbnailUrl;
	private String title;
	private String gameId;

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