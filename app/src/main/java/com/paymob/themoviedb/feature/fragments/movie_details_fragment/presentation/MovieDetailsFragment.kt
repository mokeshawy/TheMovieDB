package com.paymob.themoviedb.feature.fragments.movie_details_fragment.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.paymob.core.utils.CommonUtils.load
import com.paymob.themoviedb.R
import com.paymob.themoviedb.core.moviedb_base_fragment.MovieDbBaseFragment
import com.paymob.themoviedb.core.utils.Constants.IMAGE_BASE_URL
import com.paymob.themoviedb.databinding.FragmentMovieDetailsBinding
import com.paymob.themoviedb.feature.fragments.comoun.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : MovieDbBaseFragment<FragmentMovieDetailsBinding>() {

    override fun layoutInflater() = FragmentMovieDetailsBinding.inflate(layoutInflater)

    private val mainViewModel: MainViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    private val movieDataEntity get() = mainViewModel.getMovieDataEntity()

    private val posterPath get() = movieDataEntity?.posterPath
    private val movieTitle get() = movieDataEntity?.title ?: ""
    private val releaseDate get() = movieDataEntity?.releaseDate ?: ""
    private val voteRavage get() = movieDataEntity?.voteAverage ?: ""
    private val originalLanguage get() = movieDataEntity?.originalLanguage ?: ""
    private val overview get() = movieDataEntity?.overview
    private val isOverViewNotEmpty get() = overview.isNullOrBlank()
    private val isFavorite get() = movieDataEntity?.isFavorite ?: false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupView()
    }

    override fun onResume() {
        super.onResume()
        binding.appBarView.handleBackIvVisibility(true)
    }

    private fun FragmentMovieDetailsBinding.setupView() {
        setOnBackIvClicked()
        setTitleBarTv()
        setMoviePosterPath()
        setOnFavoriteChecked()
        setMovieDetails()
        handleOverViewVisibility()
    }

    private fun FragmentMovieDetailsBinding.setOnBackIvClicked() =
        appBarView.setOnBackIvClicked {
            mainViewModel.clearMovieDataEntity()
            findNavController().popBackStack()
        }


    private fun FragmentMovieDetailsBinding.setTitleBarTv() {
        appBarView.setTitleBarTv(movieTitle)
    }


    private fun FragmentMovieDetailsBinding.setMoviePosterPath() {
        val posterPath = if (posterPath == null) {
            R.drawable.ic_vector_no_image_placeholder
        } else {
            "$IMAGE_BASE_URL${posterPath}"
        }
        movieIv.load(requireContext(), posterPath)
    }


    private fun FragmentMovieDetailsBinding.setOnFavoriteChecked() =
        favoriteTglBtn.setOnCheckedChangeListener { _, isChecked ->
            //TODO HANDLE FAVORITE AND UN FAVORITE HERE
        }


    @SuppressLint("SetTextI18n")
    private fun FragmentMovieDetailsBinding.setMovieDetails() {
        favoriteTglBtn.isChecked = isFavorite
        movieNameTv.text = movieTitle
        releaseDateValueTv.text = releaseDate
        voteAverageTv.text = "$voteRavage"
        languageValueTv.text = originalLanguage
        overviewValueTv.text = overview
    }

    private fun FragmentMovieDetailsBinding.handleOverViewVisibility(){
        overviewTv.isVisible = !isOverViewNotEmpty
        overviewValueTv.isVisible = !isOverViewNotEmpty
    }
}