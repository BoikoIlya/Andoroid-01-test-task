package com.example.android01.places.presentation

import android.media.MediaPlayer
import android.view.View
import com.example.android01.core.DispatchersList
import com.example.android01.core.ImageLoader
import com.example.android01.databinding.FragmentDetailsBinding
import com.example.android01.databinding.PlaceCityItemBinding
import com.example.android01.details.presentation.DetailsState
import com.example.android01.details.presentation.DetailsViewModel
import com.example.android01.details.presentation.PlayerCommunication
import com.example.android01.details.presentation.ViewPagerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
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
) {
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


    class ToRecyclerViewItem @Inject constructor(
        private val itemBinding: PlaceCityItemBinding,
        private val imageLoader: ImageLoader
    ) : Mapper<Unit> {
        override fun map(
            id: Int,
            images: List<String>,
            logo: String,
            name: String,
            sound: String,
            text: String,
        ) = with(itemBinding) {
            imageLoader.loadImage(placeImg, logo)
            placeTitle.text = name
        }

    }

    class ToDetailsUi @Inject constructor(
        private val binding: FragmentDetailsBinding,
        private val adapter: ViewPagerAdapter,
    ) : Mapper<Unit> {
        override fun map(
            id: Int,
            images: List<String>,
            logo: String,
            name: String,
            sound: String,
            text: String,
        ) = with(binding) {
            if (sound.isEmpty()) {
                seekBar.visibility = View.GONE
                playBtn.visibility = View.GONE
                progress.visibility = View.GONE
            }
            adapter.map(images)
            detailsNameTitle.text = name
            placeDescription.text = text
        }

    }


    interface SetMediaPlayer: Mapper<Unit> {
        class Base @Inject constructor(
            private val playerCommunication: PlayerCommunication,
            private val mediaPlayer: MediaPlayer,
        ) : SetMediaPlayer {
            override fun map(
                id: Int,
                images: List<String>,
                logo: String,
                name: String,
                sound: String,
                text: String,
            ) {
                if (sound.isEmpty()) return
                try {
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(sound)
                    mediaPlayer.prepareAsync()
                } catch (e: Exception) {
                    playerCommunication.map(DetailsState.Error(e.message.toString()))
                }

            }

        }
    }
}

