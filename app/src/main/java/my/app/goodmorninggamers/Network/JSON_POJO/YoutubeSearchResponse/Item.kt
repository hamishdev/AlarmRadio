package my.app.goodmorninggamers.Network.JSON_POJO.YoutubeSearchResponse

data class Item(
    val etag: String,
    val id: Id,
    val kind: String,
    val snippet: Snippet
)