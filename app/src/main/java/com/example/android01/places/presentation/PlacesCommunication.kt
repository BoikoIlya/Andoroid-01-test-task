package com.example.android01.places.presentation

import com.example.android01.core.Communication
import javax.inject.Inject

/**
 * Created by HP on 03.02.2023.
 **/
interface PlacesCommunication: Communication.Mutable<PlacesUiState> {

    class Base @Inject  constructor(): Communication.UiUpdate<PlacesUiState>(
        PlacesUiState.Loading
    ),PlacesCommunication
}