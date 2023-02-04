package com.example.android01.core

/**
 * Created by HP on 03.02.2023.
 **/

sealed interface Result<R> {

    interface Mapper<R,T>{
        fun map(list: List<R>, errorMessage: String):T
    }

    fun <T> map(mapper: Mapper<R,T>):T

    data class Success<R>(private val list: List<R> = emptyList()): Result<R> {
        override fun <T> map(mapper: Mapper<R, T>): T = mapper.map(list, "")
    }

    data class Error<R>(private val message: String): Result<R> {
        override fun <T> map(mapper: Mapper<R,T>): T  = mapper.map(emptyList(), message)
    }

}