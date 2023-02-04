package com.example.android01.places.data

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
        private val mapper: PlaceCloud.Mapper<PlaceDomain>
    ): PlacesRepository {

        override suspend fun fetchData(): List<PlaceDomain> = service.places().map { it.map(mapper) }

    }
}