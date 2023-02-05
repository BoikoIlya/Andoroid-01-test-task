package com.example.android01.details.presentation

import com.example.android01.core.Communication
import com.example.android01.places.presentation.PlaceUi
import javax.inject.Inject

/**
 * Created by HP on 05.02.2023.
 **/
interface DetailsCommunication: Communication.Mutable<PlaceUi> {

    class Base @Inject constructor(): DetailsCommunication,
        Communication.UiUpdate<PlaceUi>(PlaceUi(
            id = 0,
            images = listOf(),
            logo = "",
            name = "",
            sound = "",
            text = ""
        ))
}