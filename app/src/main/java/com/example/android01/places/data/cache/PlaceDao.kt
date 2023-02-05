package com.example.android01.places.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by HP on 05.02.2023.
 **/
@Dao
interface PlaceDao {

    @Insert
    suspend fun insetPlaces(list: List<PlaceCache>)

    @Query("SELECT * FROM places")
    suspend fun getPlaces(): List<PlaceCache>
}