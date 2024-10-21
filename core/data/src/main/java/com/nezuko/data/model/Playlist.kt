package com.nezuko.data.model

data class Playlist(
    val id: Long,
    val title: String,
    val ownerId: String,
    val ownerName: String,
    val artUrl: String,
    var tracksIdList: ArrayList<Long>,
    var tracksList: ArrayList<Audio> = arrayListOf(),
    val dateCreated: Long,
    val dateModified: Long
) {
    companion object {
        fun none() = Playlist(
            id = 0L,
            title = "",
            ownerId = "",
            ownerName = "",
            artUrl = "",
            tracksIdList = arrayListOf(),
            dateModified = -1L,
            dateCreated = -1L,
        )
    }
}