package com.example.android01.places.presentation

import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android01.R
import com.example.android01.core.ClickListener
import com.example.android01.core.ImageLoader
import com.example.android01.databinding.FragmentDetailsBinding
import com.example.android01.databinding.FragmentPlacesBinding
import com.example.android01.places.presentation.PlaceUi
import com.example.android01.places.presentation.PlaceViewModel
import com.example.android01.places.presentation.PlacesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by HP on 03.02.2023.
 **/
@AndroidEntryPoint
class PlacesFragment: Fragment() {

    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewModel: PlaceViewModel by viewModels()

    private var _binding: FragmentPlacesBinding? = null
    private val binding: FragmentPlacesBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlacesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcvPlaces.layoutManager = LinearLayoutManager(requireContext())
        val adapter = PlacesAdapter(object: ClickListener<PlaceUi> {
            override fun onClick(item: PlaceUi) {
                viewModel.saveDetails(item)
                findNavController().navigate(R.id.action_placesFragment_to_detailsFragment)
            }
        },imageLoader)
        binding.rcvPlaces.adapter = adapter


        lifecycleScope.launch {
            viewModel.collect(this@PlacesFragment) {
                it.apply(
                    binding.placesError.root,
                    binding.placesProgress,
                    binding.placesError.errorTv,
                    adapter
                )
            }
        }

        binding.placesError.reloadBtn.setOnClickListener {
            viewModel.loadData()
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}