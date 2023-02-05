package com.example.android01

import com.example.android01.places.data.cache.PlaceCache
import com.example.android01.places.data.cloud.PlaceCloud
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by HP on 05.02.2023.
 **/
class CloudToCacheMapperTest {

    lateinit var mapper: PlaceCloud.Mapper<PlaceCache>

    @Before
    fun setup(){
        mapper = PlaceCloud.ToPlaceCache()
    }

    @Test
    fun `test remove tags from text`(){
        val data = PlaceCloud(
            city_id = 0,
            creation_date = "",
            id = 0,
            id_point = 0,
            images = listOf(),
            is_excursion = false,
            lang = 0,
            last_edit_time = 0,
            lat = 0.0,
            lng = 0.0,
            logo = "",
            name = "",
            photo = "",
            sound = "",
            tags = listOf(),
            text = "hello ilya",
            visible = false
        )

        val expected = PlaceCache(id = 0, images = listOf(), logo = "", name = "", sound = "", text = "lo i")

       val actual =  data.map(mapper)
        assertEquals(expected,actual)
    }

}