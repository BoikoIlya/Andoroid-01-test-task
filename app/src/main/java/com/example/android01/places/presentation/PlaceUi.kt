package com.example.android01.places.presentation

import android.media.MediaPlayer
import android.view.View
import com.example.android01.core.ImageLoader
import com.example.android01.databinding.FragmentDetailsBinding
import com.example.android01.databinding.PlaceCityItemBinding
import com.example.android01.details.presentation.ViewPagerAdapter
import javax.inject.Inject

/**
 * Created by HP on 03.02.2023.
 **/
data class PlaceUi(
   private val id: Int,
   private val images: List<String>,
   private val logo: String,
   private val name: String,
   private val sound: String,
   private val text: String,
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


    class ToRecyclerViewItem  @Inject constructor(
        private val itemBinding: PlaceCityItemBinding,
        private val imageLoader: ImageLoader
    ): Mapper<Unit>{
        override fun map(
            id: Int,
            images: List<String>,
            logo: String,
            name: String,
            sound: String,
            text: String,
        )= with(itemBinding) {
            imageLoader.loadImage(placeImg, logo)
            placeTitle.text = name
        }

    }

    class ToDetailsUi  @Inject constructor(
        private val binding: FragmentDetailsBinding,
        private val adapter: ViewPagerAdapter,
        private val mediaPlayer: MediaPlayer
    ): Mapper<Unit>{
        override fun map(
            id: Int,
            images: List<String>,
            logo: String,
            name: String,
            sound: String,
            text: String,
        ){
            if(sound.isEmpty()){
                binding.seekBar.visibility = View.GONE
                binding.playBtn.visibility = View.GONE
            }else {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(sound)
                mediaPlayer.prepareAsync()
            }
            adapter.map(images)
            binding.detailsNameTitle.text = name
            binding.placeDescription.text = text
        }

    }
}
