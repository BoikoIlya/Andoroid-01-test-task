package com.example.android01.places.data.cache

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken

/**
 * Created by HP on 05.02.2023.
 **/
class TypeConvertersForImageList {

    @TypeConverter
    fun restoreList(listOfString: String): List<String> {
        return Gson().fromJson(listOfString, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun saveList(listOfString: List<String>): String {
        return Gson().toJson(listOfString)
    }
}