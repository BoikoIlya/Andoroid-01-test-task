package com.example.android01.places.domain

import com.example.android01.core.DetailsKeeper
import com.example.android01.core.HandleRequest
import com.example.android01.core.Result
import com.example.android01.places.data.PlacesRepository
import com.example.android01.places.presentation.PlaceUi
import javax.inject.Inject

/**
 * Created by HP on 03.02.2023.
 **/
interface PlacesInteractor {

    suspend fun fetchData(): Result<PlaceDomain>

    fun saveDetails(data: PlaceUi)

    class Base @Inject constructor(
        private val repository: PlacesRepository,
        private val handleRequest: HandleRequest<PlaceDomain>,
        private val detailsKeeper: DetailsKeeper<PlaceUi>
    ):PlacesInteractor{

        override suspend fun fetchData(): Result<PlaceDomain> =
            handleRequest.handle { repository.fetchData() }

        override fun saveDetails(data: PlaceUi) {
            detailsKeeper.save(data)
        }

    }

}