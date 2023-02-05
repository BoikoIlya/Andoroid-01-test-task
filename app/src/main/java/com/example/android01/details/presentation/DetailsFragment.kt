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
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.android01.R
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
class DetailsFragment: Fragment(R.layout.fragment_details) {

    private val viewModel: DetailsViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    private val binding by viewBinding(FragmentDetailsBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState!=null) viewModel.initFragmentAfterConfigurationChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewPagerAdapter(imageLoader)
        binding.detailsViewPager.adapter = adapter

        val mapper = PlaceUi.ToDetailsUi(binding, adapter)

        lifecycleScope.launch{
            viewModel.collectDetails(this@DetailsFragment){
                it.map(mapper)
            }
        }

        lifecycleScope.launch {
            viewModel.collectDetailsState(this@DetailsFragment){
                it.apply(
                    binding,
                    Toast.makeText(requireContext(), "", Toast.LENGTH_LONG)
                )
            }
        }

        lifecycleScope.launch{
            viewModel.collectPlayerTime(this@DetailsFragment){
                binding.time.text = it
            }
        }


        binding.playBtn.setOnClickListener {
            if ((it as ToggleButton).isChecked) viewModel.play()
            else viewModel.pause()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, position: Int, user: Boolean) {
                if (user) viewModel.seekTo(position)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) = Unit

            override fun onStopTrackingTouch(p0: SeekBar?)= Unit
        })

    }

}

