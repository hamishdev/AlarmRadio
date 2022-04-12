package com.example.goodmorninggamers.AlarmBroadcastReceiver.YoutubeLiveCheckResponse;

import com.google.gson.annotations.SerializedName;

public class ItemsItem{

	@SerializedName("snippet")
	private Snippet snippet;

	@SerializedName("kind")
	private String kind;

	@SerializedName("etag")
	private String etag;

	@SerializedName("id")
	private Id id;

	public Snippet getSnippet(){
		return snippet;
	}

	public String getKind(){
		return kind;
	}

	public String getEtag(){
		return etag;
	}

	public Id getId(){
		return id;
	}
}