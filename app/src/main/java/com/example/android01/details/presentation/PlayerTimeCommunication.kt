package com.example.android01.details.presentation

import com.example.android01.core.Communication
import javax.inject.Inject

/**
 * Created by HP on 05.02.2023.
 **/
interface PlayerTimeCommunication: Communication.Mutable<String> {

    class Base @Inject constructor(): PlayerTimeCommunication,
        Communication.UiUpdate<String>("")
}