package com.example.android01.core


import com.example.android01.places.domain.PlaceDomain
import com.example.android01.places.presentation.PlacesCommunication
import com.example.android01.places.presentation.PlaceUi
import com.example.android01.places.presentation.PlacesUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HandleResult<S> {

    fun handle(
        coroutineScope: CoroutineScope,
        block: suspend()-> Result<S>
        )



    class Base<T> @Inject constructor(
        private val dispatchersList: DispatchersList,
        private val mapper: Result.Mapper<T,Unit>
    ): HandleResult<T>{

        override fun handle(
            coroutineScope: CoroutineScope,
            block: suspend()->Result<T>
        ) {
                coroutineScope.launch(dispatchersList.io()) {
                    val result =  block.invoke()
                    result.map(mapper)
                }
        }
    }
}



class PlacesResultMapper @Inject constructor(
    private val communication: PlacesCommunication,
    private val mapper: PlaceDomain.Mapper<PlaceUi>
): Result.Mapper<PlaceDomain,Unit> {


    override fun map(list: List<PlaceDomain>, errorMessage: String) {
        communication.map(
            if (list.isNotEmpty()) PlacesUiState.ShowList(list.map { it.map(mapper) })
            else PlacesUiState.ShowError(errorMessage)
        )

    }
}

