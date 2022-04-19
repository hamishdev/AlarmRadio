package my.app.goodmorninggamers.AlarmBroadcastReceiver.TwitchLiveCheckResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("tag_ids")
	private List<String> tagIds;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("language")
	private String language;

	@SerializedName("is_mature")
	private boolean isMature;

	@SerializedName("type")
	private String type;

	@SerializedName("title")
	private String title;

	@SerializedName("thumbnail_url")
	private String thumbnailUrl;

	@SerializedName("game_name")
	private String gameName;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("user_login")
	private String userLogin;

	@SerializedName("started_at")
	private String startedAt;

	@SerializedName("id")
	private String id;

	@SerializedName("viewer_count")
	private int viewerCount;

	@SerializedName("game_id")
	private String gameId;

	public List<String> getTagIds(){
		return tagIds;
	}

	public String getUserName(){
		return userName;
	}

	public String getLanguage(){
		return language;
	}

	public boolean isIsMature(){
		return isMature;
	}

	public String getType(){
		return type;
	}

	public String getTitle(){
		return title;
	}

	public String getThumbnailUrl(){
		return thumbnailUrl;
	}

	public String getGameName(){
		return gameName;
	}

	public String getUserId(){
		return userId;
	}

	public String getUserLogin(){
		return userLogin;
	}

	public String getStartedAt(){
		return startedAt;
	}

	public String getId(){
		return id;
	}

	public int getViewerCount(){
		return viewerCount;
	}

	public String getGameId(){
		return gameId;
	}
}