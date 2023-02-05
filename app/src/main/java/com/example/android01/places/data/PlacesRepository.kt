package com.example.android01.places.data

import com.example.android01.places.data.cache.PlaceCache
import com.example.android01.places.data.cache.PlaceDao
import com.example.android01.places.data.cloud.PlaceCloud
import com.example.android01.places.data.cloud.PlacesService
import com.example.android01.places.domain.PlaceDomain
import javax.inject.Inject

/**
 * Created by HP on 04.02.2023.
 **/
interface PlacesRepository {

    suspend fun fetchData():List<PlaceDomain>


    class Base @Inject constructor(
        private val service: PlacesService,
        private val mapperToPlaceCache: PlaceCloud.Mapper<PlaceCache>,
        private val mapperToPlaceDomain: PlaceCache.Mapper<PlaceDomain>,
        private val cache: PlaceDao
    ): PlacesRepository {

        override suspend fun fetchData(): List<PlaceDomain> {
            val cachedData: List<PlaceCache> =  cache.getPlaces()
            return if(cachedData.isNotEmpty()) cachedData.map { it.map(mapperToPlaceDomain) }
            else {
                cache.insetPlaces(service.places().map { it.map(mapperToPlaceCache) })
                cache.getPlaces().map { it.map(mapperToPlaceDomain) }
            }
        }

    }
}