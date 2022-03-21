package com.example.goodmorninggamers.twitchresponse;

import java.util.List;

public class twitchResponse {
	private Pagination pagination;
	private List<DataItem> data;

	public Pagination getPagination(){
		return pagination;
	}

	public List<DataItem> getData(){
		return data;
	}
}