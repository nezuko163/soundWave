package com.nezuko.data.model

data class Audio(
    val id: Long ,
    val title: String,
    val artist: String,
    val album: String,
    val artUrl: String,
    val mediaUrl: String,
    val duration: Long ,
    val owner_id: String,
    val dateAdded: Long,
    var queueId: Long
)