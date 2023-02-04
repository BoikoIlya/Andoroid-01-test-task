package com.example.android01.core

import javax.inject.Inject

/**
 * Created by HP on 04.02.2023.
 **/
interface DetailsKeeper<T> {

    fun save(data: T)

    fun read(): T

    class Base<T> @Inject constructor(
        private var data: T
    ): DetailsKeeper<T>{


        override fun save(data: T) {
            this.data = data
        }
        override fun read(): T  = data

    }
}