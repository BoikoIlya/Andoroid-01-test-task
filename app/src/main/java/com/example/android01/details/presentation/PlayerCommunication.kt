package com.example.android01.details.presentation

import com.example.android01.core.Communication
import javax.inject.Inject

/**
 * Created by HP on 05.02.2023.
 **/
interface PlayerCommunication: Communication.Mutable<DetailsState> {

    class Base @Inject constructor(): PlayerCommunication,
        Communication.UiUpdate<DetailsState>(DetailsState.Loading)
}