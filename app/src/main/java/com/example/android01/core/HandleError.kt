package com.example.android01.core


import com.example.android01.R
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

interface HandleError {

    fun handle(e: Exception):String

    class Base @Inject constructor(
        private val managerResource: ManagerResource
    ): HandleError{
        override fun handle(e: Exception): String = managerResource.getString(
            when(e){
                is UnknownHostException -> R.string.no_connection_message
                is java.net.SocketTimeoutException -> R.string.timeout_message
                else -> R.string.service_is_unavailable
            }
        )

    }
}
