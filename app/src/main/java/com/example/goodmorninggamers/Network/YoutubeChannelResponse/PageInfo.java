package com.example.goodmorninggamers.Network.YoutubeChannelResponse;

import com.google.gson.annotations.SerializedName;

public class PageInfo{

	@SerializedName("totalResults")
	private int totalResults;

	@SerializedName("resultsPerPage")
	private int resultsPerPage;

	public int getTotalResults(){
		return totalResults;
	}

	public int getResultsPerPage(){
		return resultsPerPage;
	}
}