package com.shoppi.app.repository.home

import com.google.gson.Gson
import com.shoppi.app.model.HomeData
import com.shoppi.app.ui.common.AssetLoader

class HomeAssetDataSource(private val assetLoader: AssetLoader) : HomeDataSource {

    private val gson = Gson()

    override fun getHomeData(): HomeData? {
        return assetLoader.getJsonString("home.json")?.let { homeJsonString ->
            gson.fromJson(homeJsonString, HomeData::class.java)
        }

    }

}