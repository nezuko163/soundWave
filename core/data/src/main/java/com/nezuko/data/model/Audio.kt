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
    fun setQueueId(queueId: Long) = this.copy(queueId = queueId)
}