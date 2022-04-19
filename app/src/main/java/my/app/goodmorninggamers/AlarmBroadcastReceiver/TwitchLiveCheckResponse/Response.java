package my.app.goodmorninggamers.AlarmBroadcastReceiver.TwitchLiveCheckResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

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