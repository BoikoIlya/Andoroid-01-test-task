package com.example.android01.details.presentation

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android01.core.ImageLoader
import com.example.android01.databinding.FragmentDetailsBinding
import com.example.android01.places.presentation.PlaceUi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by HP on 03.02.2023.
 **/
@AndroidEntryPoint
class DetailsFragment: Fragment() {

    private val viewModel: DetailsViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var mediaPlayer: MediaPlayer

    private var _binding: FragmentDetailsBinding? = null
    private  val binding: FragmentDetailsBinding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = viewModel.loadData()

        val adapter = ViewPagerAdapter(imageLoader)
        binding.detailsViewPager.adapter = adapter
        val mapper = PlaceUi.ToDetailsUi(binding, adapter, mediaPlayer)

        try {
            data.map(mapper)
        } catch (e: java.lang.Exception) {
            Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_LONG).show()
        }

        binding.playBtn.setOnClickListener {
            if ((it as ToggleButton).isChecked) mediaPlayer.start()
            else mediaPlayer.pause()
        }

        mediaPlayer.setOnCompletionListener {
            mediaPlayer.pause()
            binding.playBtn.isChecked = false
        }
        var time = 0L
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, position: Int, user: Boolean) {
                if (user) mediaPlayer.seekTo(position)
                time = mediaPlayer.duration.toLong() - mediaPlayer.currentPosition
                binding.time.text = "${(time/1000)/60}:${(time/1000)%60}"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) = Unit

            override fun onStopTrackingTouch(p0: SeekBar?)= Unit
        })


        mediaPlayer.setOnPreparedListener {
            binding.seekBar.max = mediaPlayer.duration
            binding.progress.visibility = View.GONE
            binding.playBtn.visibility = View.VISIBLE
            time = mediaPlayer.duration.toLong() - mediaPlayer.currentPosition
            binding.time.text = "${(time/1000)/60}:${(time/1000)%60}"
        }


       lifecycleScope.launch{
            while (true) {
                if(mediaPlayer.isPlaying && time>=0) time -= 1000
                binding.seekBar.progress = mediaPlayer.currentPosition
                binding.time.text = "${(time/1000)/60}:${(time/1000)%60}"
                delay(1000)
            }
        }

    }

    override fun onDestroyView() {
        _binding = null
        mediaPlayer.stop()
        super.onDestroyView()
    }
}

