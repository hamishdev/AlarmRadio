package com.example.goodmorninggamers.Network.YoutubeSearchResponse

data class Item(
        val etag: String,
        val id: Id,
        val kind: String,
        val snippet: Snippet
)