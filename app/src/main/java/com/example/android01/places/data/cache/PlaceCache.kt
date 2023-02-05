package com.example.android01.places.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android01.places.domain.PlaceDomain
import com.example.android01.places.presentation.PlaceUi
import javax.inject.Inject

/**
 * Created by HP on 04.02.2023.
 **/
@Entity(tableName = "places")
data class PlaceCache(
    @PrimaryKey(autoGenerate = false)
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

    class ToPlaceDomain @Inject constructor(): Mapper<PlaceDomain>{
        override fun map(
            id: Int,
            images: List<String>,
            logo: String,
            name: String,
            sound: String,
            text: String,
        ): PlaceDomain {
            return PlaceDomain(
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
