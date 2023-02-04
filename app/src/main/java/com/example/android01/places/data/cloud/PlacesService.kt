package com.example.android01.places.data.cloud

import retrofit2.http.GET

/**
 * Created by HP on 04.02.2023.
 **/
interface PlacesService {

    companion object{
        private const val placesPath = "get_points/11/"
    }

    @GET(placesPath)
    suspend fun places(): List<PlaceCloud>
}