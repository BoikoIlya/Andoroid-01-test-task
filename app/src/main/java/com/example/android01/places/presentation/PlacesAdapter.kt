package com.example.android01.places.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.android01.core.ClickListener
import com.example.android01.core.ImageLoader
import com.example.android01.databinding.PlaceCityItemBinding

/**
 * Created by HP on 03.02.2023.
 **/
class PlacesAdapter(
    private val clickListener: ClickListener<PlaceUi>,
    private val imageLoader: ImageLoader
): RecyclerView.Adapter<CitiesViewHolder>() {

    private val list = mutableListOf<PlaceUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        return CitiesViewHolder(PlaceCityItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),clickListener, imageLoader)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.map(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun map(list: List<PlaceUi>){
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}

class CitiesViewHolder(
    private val binding: PlaceCityItemBinding,
    private val clickListener: ClickListener<PlaceUi>,
    private val imageLoader: ImageLoader
):ViewHolder(binding.root){

    private val mapper = PlaceUi.ToRecyclerViewItem(binding,imageLoader)

    fun map(item: PlaceUi) {
        item.map(mapper)

        binding.root.setOnClickListener {
            clickListener.onClick(item)
        }
    }

}
