package com.example.android01.places.data.cloud

import com.example.android01.places.domain.PlaceDomain
import javax.inject.Inject

data class PlaceCloud(
   private val city_id: Int,
   private val creation_date: String,
   private val id: Int,
   private val id_point: Int,
   private val images: List<String>,
   private val is_excursion: Boolean,
   private val lang: Int,
   private val last_edit_time: Int,
   private val lat: Double,
   private val lng: Double,
   private val logo: String,
   private val name: String,
   private val photo: String,
   private val sound: String,
   private val tags: List<Int>,
   private val text: String,
   private val visible: Boolean
){
   fun <T> map(mapper: Mapper<T>): T = mapper.map(
      city_id,
      creation_date,
      id,
      id_point,
      images,
      is_excursion,
      lang,
      last_edit_time,
      lat,
      lng,
      logo,
      name,
      photo,
      sound,
      tags,
      text,
      visible
   )

   interface Mapper<T> {
      fun map(
         city_id: Int,
         creation_date: String,
         id: Int,
         id_point: Int,
         images: List<String>,
         is_excursion: Boolean,
         lang: Int,
         last_edit_time: Int,
         lat: Double,
         lng: Double,
         logo: String,
         name: String,
         photo: String,
         sound: String,
         tags: List<Int>,
         text: String,
         visible: Boolean
      ): T
   }

   class ToPlaceDomain @Inject constructor(): Mapper<PlaceDomain>{
      override fun map(
         city_id: Int,
         creation_date: String,
         id: Int,
         id_point: Int,
         images: List<String>,
         is_excursion: Boolean,
         lang: Int,
         last_edit_time: Int,
         lat: Double,
         lng: Double,
         logo: String,
         name: String,
         photo: String,
         sound: String,
         tags: List<Int>,
         text: String,
         visible: Boolean,
      ): PlaceDomain {
         return PlaceDomain(
            id = id,
            images = images,
            logo = logo,
            name = name,
            sound = sound,
            text = text
         )
      }

   }
}