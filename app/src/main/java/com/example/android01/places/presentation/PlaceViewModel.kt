package com.example.android01.places.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android01.core.Communication
import com.example.android01.core.HandleResult
import com.example.android01.places.domain.PlaceDomain
import com.example.android01.places.domain.PlacesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

/**
 * Created by HP on 03.02.2023.
 **/
@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val interactor: PlacesInteractor,
    private val communication: PlacesCommunication,
    private val handleResult: HandleResult<PlaceDomain>
): ViewModel(), Communication.Collector<PlacesUiState> {

    init {
       loadData()
    }

    fun loadData() = handleResult.handle(viewModelScope){
        communication.map(PlacesUiState.Loading)
        interactor.fetchData()
    }

    fun saveDetails(data: PlaceUi){
        interactor.saveDetails(data)
    }

    override suspend fun collect(
        lifecycleOwner: LifecycleOwner,
        collector: FlowCollector<PlacesUiState>,
    ) = communication.collect(lifecycleOwner, collector)


}