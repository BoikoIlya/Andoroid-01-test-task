package com.example.android01.details.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.android01.core.ImageLoader
import com.example.android01.databinding.ViewPagerItemBinding
import com.example.android01.places.presentation.PlaceUi

/**
 * Created by HP on 04.02.2023.
 **/
class ViewPagerAdapter(
    private val imageLoader: ImageLoader,
): RecyclerView.Adapter<ViewPagerViewHolder>(){

    private val list =  mutableListOf<String>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(ViewPagerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),imageLoader)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.map(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun map(newItems: List<String>){
        list.addAll(newItems)
        notifyDataSetChanged()
    }


}

class ViewPagerViewHolder(
    private val binding: ViewPagerItemBinding,
    private val imageLoader: ImageLoader
): ViewHolder(binding.root){


    fun map(url: String) = imageLoader.loadImage(binding.itemImage, url)

}