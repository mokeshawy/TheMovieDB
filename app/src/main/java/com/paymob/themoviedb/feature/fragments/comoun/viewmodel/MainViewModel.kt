package com.paymob.themoviedb.feature.fragments.comoun.viewmodel

import androidx.lifecycle.ViewModel
import com.paymob.themoviedb.feature.fragments.home_fragment.domain.model.MovieDataEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private var movieDataEntity: MovieDataEntity? = null


    fun setMovieDataEntity(movieDataEntity: MovieDataEntity) {
        this.movieDataEntity = movieDataEntity
    }

    fun getMovieDataEntity() = this.movieDataEntity


    fun clearMovieDataEntity() {
        this.movieDataEntity = null
    }

    override fun onCleared() {
        super.onCleared()
        clearMovieDataEntity()
    }
}