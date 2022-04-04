package com.example.goodmorninggamers.Network.JSON_POJO.twitchresponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response{

	@SerializedName("pagination")
	private Pagination pagination;

	@SerializedName("data")
	private List<DataItem> data;

	public Pagination getPagination(){
		return pagination;
	}

	public List<DataItem> getData(){
		return data;
	}
}
