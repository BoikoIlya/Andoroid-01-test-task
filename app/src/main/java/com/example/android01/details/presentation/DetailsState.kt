package com.example.android01.details.presentation

import android.view.View
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import com.example.android01.databinding.FragmentDetailsBinding
import java.text.FieldPosition

/**
 * Created by HP on 05.02.2023.
 **/
sealed interface DetailsState{

    fun apply(
        binding: FragmentDetailsBinding,
        toast: Toast
    )

     object CompletePlaying: DetailsState{
         override fun apply(
             binding: FragmentDetailsBinding,
             toast: Toast
         ) = with(binding){
             playBtn.isChecked = false
         }
     }

    data class Prepared(
        private val time: String,
        private val duration: Int
    ): DetailsState{
        override fun apply(
            binding: FragmentDetailsBinding,
            toast: Toast
        ) = with(binding) {
            progress.visibility = View.GONE
            seekBar.max = duration
            playBtn.visibility = View.VISIBLE
            time.text = this@Prepared.time
        }
    }

    data class SeekTo(
        private val position: Int,
    ): DetailsState{
        override fun apply(
            binding: FragmentDetailsBinding,
            toast: Toast
        ) = with(binding) {
            seekBar.progress = position
        }

    }

    data class Error(
        private val errorMessage: String
    ): DetailsState{
        override fun apply(
            binding: FragmentDetailsBinding,
            toast: Toast
        ) {
            toast.setText(errorMessage)
            toast.show()
        }
    }

    object Loading: DetailsState{
        override fun apply(
            binding: FragmentDetailsBinding,
            toast: Toast
        ) = with(binding) {
            progress.visibility = View.VISIBLE
            playBtn.visibility = View.GONE
        }

    }
}