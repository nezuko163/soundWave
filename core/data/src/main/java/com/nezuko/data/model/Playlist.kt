package com.nezuko.data.model

data class Playlist(
    val id: Long,
    val title: String,
    val ownerId: String,
    val ownerName: String,
    val artUrl: String,
    var tracksIdList: ArrayList<Long>,
    var tracksList: ArrayList<Audio>,
    val dateCreated: Long,
    val dateModified: Long
) {
    companion object {
        fun none() = Playlist(
            id = -1L,
            title = "",
            ownerId = "",
            ownerName = "",
            artUrl = "",
            tracksList = arrayListOf(),
            tracksIdList = arrayListOf(),
            dateModified = -1L,
            dateCreated = -1L,

        )
    }
}