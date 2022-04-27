package com.shoppi.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.shoppi.app.database.VideosDatabase
import com.shoppi.app.database.asDomainModel
import com.shoppi.app.domain.DevByteVideo
import com.shoppi.app.network.DevByteNetwork
import com.shoppi.app.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideosRepository(private val database: VideosDatabase) {

    val videos: LiveData<List<DevByteVideo>> = Transformations.map(database.videoDao.getVideos()) {
        it.asDomainModel()
    }


    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {

            val playlist = DevByteNetwork.devbytes.getPlaylist()
            database.videoDao.insertAll(playlist.asDatabaseModel())
        }
    }

}