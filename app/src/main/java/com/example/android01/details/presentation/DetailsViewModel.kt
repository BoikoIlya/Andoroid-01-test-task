package com.example.android01.details.presentation

import android.media.MediaPlayer
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android01.core.DetailsKeeper
import com.example.android01.core.DispatchersList
import com.example.android01.places.presentation.PlaceUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by HP on 04.02.2023.
 **/
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsKeeper: DetailsKeeper<PlaceUi>,
    private val mediaPlayer: MediaPlayer,
    private val playerCommunication: PlayerCommunication,
    private val detailsCommunication: DetailsCommunication,
    private val playerTimeCommunication: PlayerTimeCommunication,
    private val dispatchersList: DispatchersList,
    private val mapper: PlaceUi.SetMediaPlayer
): ViewModel() {

    private var time: Long = 0

    init {
        val data = detailsKeeper.read()
        setupPlayer()
        data.map(mapper)
        detailsCommunication.map(data)
        playerTime()
    }

    fun setupPlayer(){

        mediaPlayer.setOnPreparedListener {
            time = mediaPlayer.duration.toLong() - mediaPlayer.currentPosition
            playerCommunication.map(
                DetailsState.Prepared(
                    "${(time / 1000) / 60}:${(time / 1000) % 60}",
                    mediaPlayer.duration
                )
            )
        }

        mediaPlayer.setOnCompletionListener {
            mediaPlayer.pause()
            playerCommunication.map(DetailsState.CompletePlaying)
        }
    }

    fun play() = mediaPlayer.start()

    fun pause() = mediaPlayer.pause()

    fun seekTo(position: Int){
        mediaPlayer.seekTo(position)
        time = mediaPlayer.duration.toLong() - mediaPlayer.currentPosition
        playerTimeCommunication.map("${(time/1000)/60}:${(time/1000)%60}")
    }

    fun playerTime() = viewModelScope.launch(dispatchersList.io()) {
        while (true) {
            if (mediaPlayer.isPlaying && time >= 0) time -= 1000
            playerCommunication.map(DetailsState.SeekTo(mediaPlayer.currentPosition))
            playerTimeCommunication.map("${(time / 1000) / 60}:${(time / 1000) % 60}")
            delay(1000)
        }
    }

    fun initFragmentAfterConfigurationChanged(){
        time = mediaPlayer.duration.toLong() - mediaPlayer.currentPosition
        playerCommunication.map(
            DetailsState.Prepared(
                "${(time / 1000) / 60}:${(time / 1000) % 60}",
                mediaPlayer.duration
            )
        )
    }

    suspend fun collectDetailsState(
        owner: LifecycleOwner,
        collector: FlowCollector<DetailsState>
    ) = playerCommunication.collect(owner,collector)

    suspend fun collectPlayerTime(
        owner: LifecycleOwner,
        collector: FlowCollector<String>
    ) = playerTimeCommunication.collect(owner,collector)

    suspend fun collectDetails(
        owner: LifecycleOwner,
        collector: FlowCollector<PlaceUi>
    ) = detailsCommunication.collect(owner,collector)

    override fun onCleared() {
        mediaPlayer.pause()
        super.onCleared()
    }
}