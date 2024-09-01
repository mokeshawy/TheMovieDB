package com.paymob.themoviedb.feature.fragments.comoun.viewmodel

import androidx.lifecycle.ViewModel
import com.paymob.themoviedb.feature.fragments.home_fragment.domain.model.MovieUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private var movieUiModel: MovieUiModel? = null


    fun setMovieDataEntity(movieUiModel: MovieUiModel) {
        this.movieUiModel = movieUiModel
    }

    fun getMovieDataEntity() = this.movieUiModel


    fun clearMovieDataEntity() {
        this.movieUiModel = null
    }

    override fun onCleared() {
        super.onCleared()
        clearMovieDataEntity()
    }
}