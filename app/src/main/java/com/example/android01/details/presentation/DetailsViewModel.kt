package com.example.android01.details.presentation

import androidx.lifecycle.ViewModel
import com.example.android01.core.DetailsKeeper
import com.example.android01.places.presentation.PlaceUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by HP on 04.02.2023.
 **/
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsKeeper: DetailsKeeper<PlaceUi>
): ViewModel() {

    fun loadData() = detailsKeeper.read()


}