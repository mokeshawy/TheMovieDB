package com.paymob.themoviedb.feature.fragments.home_fragment.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.paymob.core.state.State
import com.paymob.themoviedb.feature.fragments.home_fragment.domain.model.MovieDataEntity
import com.paymob.themoviedb.feature.fragments.home_fragment.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val moviesUseCase: MoviesUseCase) : ViewModel() {

    private val _pagingMovieDataState =
        MutableStateFlow<State<PagingData<MovieDataEntity>>>(State.Initial())
    val pagingMovieDataState = _pagingMovieDataState.asStateFlow()

    init {
        getPagingMovieDataEntity()
    }

    private fun getPagingMovieDataEntity() = viewModelScope.launch {
        moviesUseCase().cachedIn(viewModelScope).collect {
            _pagingMovieDataState.emit(State.Success(it))
        }
    }
}