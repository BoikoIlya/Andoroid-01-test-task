package com.example.android01.places.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

/**
 * Created by HP on 05.02.2023.
 **/
@TypeConverters(TypeConvertersForImageList::class)
@Database(entities = [PlaceCache::class], version = 1)
abstract class PlacesDB: RoomDatabase() {

    abstract fun getDao(): PlaceDao
}