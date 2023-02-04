package com.example.android01.places.domain

import com.example.android01.places.presentation.PlaceUi
import javax.inject.Inject

/**
 * Created by HP on 04.02.2023.
 **/
data class PlaceDomain(
    val id: Int,
    val images: List<String>,
    val logo: String,
    val name: String,
    val sound: String,
    val text: String,
){
    fun <T> map(mapper: Mapper<T>): T = mapper.map(
        id, images, logo, name, sound, text
    )

    interface Mapper<T> {
        fun map(
            id: Int,
            images: List<String>,
            logo: String,
            name: String,
            sound: String,
            text: String,
        ): T
    }

    class ToPlaceUi @Inject constructor(): Mapper<PlaceUi>{
        override fun map(
            id: Int,
            images: List<String>,
            logo: String,
            name: String,
            sound: String,
            text: String,
        ): PlaceUi {
            return PlaceUi(
                id = id,
                images = images,
                logo = logo,
                name = name,
                sound = sound,
                text = text,
            )
        }

    }
}
