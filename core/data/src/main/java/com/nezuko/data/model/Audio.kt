package com.nezuko.data.model

data class Audio(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val artUrl: String,
    val mediaUrl: String,
    val duration: Long,
    val ownerId: String,
    val dateAdded: Long,
    var queueId: Long = 0
) {
    companion object {
        fun none() = Audio(
            id = 3562346L,
            title = "",
            artist = "",
            album = "",
            artUrl = "",
            mediaUrl = "",
            duration = 0L,
            ownerId = "",
            dateAdded = 0L
        )
    }

    fun setQueueId(queueId: Long) = this.copy(queueId = queueId)
}

