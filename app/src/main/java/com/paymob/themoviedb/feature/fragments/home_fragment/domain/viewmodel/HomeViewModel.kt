package com.paymob.themoviedb.feature.fragments.home_fragment.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.paymob.themoviedb.feature.fragments.home_fragment.domain.model.MovieDataEntity
import com.paymob.themoviedb.feature.fragments.home_fragment.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val moviesUseCase: MoviesUseCase) : ViewModel() {


    private var movieDataEntity: MovieDataEntity? = null

    fun getPagingMovieDataEntity(submitPagingMovieEntity: suspend (PagingData<MovieDataEntity>) -> Unit) =
        viewModelScope.launch {
            moviesUseCase().collect { submitPagingMovieEntity(it) }
        }


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