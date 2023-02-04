package com.example.android01.places.presentation

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Created by HP on 03.02.2023.
 **/
sealed interface PlacesUiState{

    fun apply(
        errorView: ConstraintLayout,
        progressBar: ProgressBar,
        errorMessage: TextView,
        adapter: PlacesAdapter
    )

    object Loading: PlacesUiState{
        override fun apply(
            errorView: ConstraintLayout,
            progressBar: ProgressBar,
            errorMessage: TextView,
            adapter: PlacesAdapter
        ) {
            errorView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }

    }

    data class ShowList(
        private val list: List<PlaceUi>
    ): PlacesUiState{
        override fun apply(
            errorView: ConstraintLayout,
            progressBar: ProgressBar,
            errorMessage: TextView,
            adapter: PlacesAdapter
        ) {
            progressBar.visibility = View.GONE
            adapter.map(list)
        }

    }

    data class ShowError(
        private val errorMessage: String
    ): PlacesUiState{

        override fun apply(
            errorView: ConstraintLayout,
            progressBar: ProgressBar,
            errorMessage: TextView,
            adapter: PlacesAdapter
        ) {
            errorMessage.text = this.errorMessage
            errorView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }

    }

}