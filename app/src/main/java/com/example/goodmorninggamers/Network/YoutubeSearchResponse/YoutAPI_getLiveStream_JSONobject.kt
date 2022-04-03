package com.example.goodmorninggamers.Network.YoutubeSearchResponse

data class YoutAPI_getLiveStream_JSONobject(
        val etag: String,
        val items: List<Item>,
        val kind: String,
        val pageInfo: PageInfo,
        val regionCode: String
)