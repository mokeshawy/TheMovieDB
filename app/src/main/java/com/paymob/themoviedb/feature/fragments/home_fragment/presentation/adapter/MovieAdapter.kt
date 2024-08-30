package com.paymob.themoviedb.feature.fragments.home_fragment.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.paymob.core.utils.CommonUtils.load
import com.paymob.themoviedb.R
import com.paymob.themoviedb.core.utils.Constants.IMAGE_BASE_URL
import com.paymob.themoviedb.databinding.ItemMovieBinding
import com.paymob.themoviedb.feature.fragments.home_fragment.domain.model.MovieDataEntity

class MovieAdapter(
    diffUtil: DiffUtil.ItemCallback<MovieDataEntity>,
    private val onItemClicked: (MovieDataEntity) -> Unit,
    private val onFavoriteClicked: (Int, Boolean) -> Unit,
) :
    PagingDataAdapter<MovieDataEntity, MovieAdapter.ViewHolder>(diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        viewHolder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context: Context = binding.root.context

        fun bind(item: MovieDataEntity) {
            binding.setupView(item)
        }

        @SuppressLint("SetTextI18n")
        private fun ItemMovieBinding.setupView(item: MovieDataEntity) {
            setImageResource(item)
            movieNameTv.text = item.title
            favoriteTglBtn.isChecked = item.isFavorite
            releaseDateValueTv.text = item.releaseDate
            voteAverageTv.text = "${item.voteAverage}"
            setOnMovieIvClicked(item)
            setOnFavoriteClicked(item)
        }

        private fun ItemMovieBinding.setImageResource(item: MovieDataEntity) {
            val posterPath = if (item.posterPath == null) {
                R.drawable.ic_vector_no_image_placeholder
            } else {
                "$IMAGE_BASE_URL${item.posterPath}"
            }
            movieIv.load(context, posterPath)
        }

        private fun ItemMovieBinding.setOnMovieIvClicked(item: MovieDataEntity) =
            movieIv.setOnClickListener { onItemClicked(item) }

        private fun ItemMovieBinding.setOnFavoriteClicked(item: MovieDataEntity) =
            favoriteTglBtn.setOnCheckedChangeListener { _, isChecked ->
                onFavoriteClicked(item.id, isChecked)
            }
    }
}