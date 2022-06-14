package com.shoppi.app.ui.tutorials.videotuto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "databasevideo")
data class DatabaseVideo(
    @PrimaryKey val url: String,
    val updated: String,
    val title: String,
    val description: String,
    val thumbnail: String
)

fun List<DatabaseVideo>.asDomainModel(): List<DevByteVideo> {
    return this.map {
        DevByteVideo(
            url = it.url,
            title = it.title,
            description = it.description,
            updated = it.updated,
            thumbnail = it.thumbnail
        )
    }
}