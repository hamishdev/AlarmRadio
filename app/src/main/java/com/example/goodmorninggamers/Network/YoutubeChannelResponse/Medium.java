package com.example.goodmorninggamers.Network.YoutubeChannelResponse;

import com.google.gson.annotations.SerializedName;

public class Medium{

	@SerializedName("width")
	private int width;

	@SerializedName("url")
	private String url;

	@SerializedName("height")
	private int height;

	public int getWidth(){
		return width;
	}

	public String getUrl(){
		return url;
	}

	public int getHeight(){
		return height;
	}
}