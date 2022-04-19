package my.app.goodmorninggamers.AlarmBroadcastReceiver.YoutubeLiveCheckResponse;

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