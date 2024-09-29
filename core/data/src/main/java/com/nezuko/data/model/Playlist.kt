package com.nezuko.data.model

data class Playlist(
    val id: Long,
    val title: String,
    val owner_id: String,
    val ownerName: String,
    val artUrl: String,
    var tracksIdList: ArrayList<Long>,
    var tracksList: ArrayList<Audio>,
    val dateCreated: Long,
    val dateModified: Long
)