package com.shoppi.app.repository.home

import com.shoppi.app.model.HomeData

interface HomeDataSource {

    fun getHomeData(): HomeData?
}