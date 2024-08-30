package com.paymob.core.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paymob.core.R
import com.paymob.core.databinding.LoadStateItemBinding

class PagingListStateAdapter(
    private val onLoadStateError: (LoadState.Error) -> Unit = {},
    private val retry: () -> Unit
) : LoadStateAdapter<PagingListStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoadStateViewHolder(parent)

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    inner class LoadStateViewHolder(val parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.load_state_item, parent, false)
    ) {
        private val binding = LoadStateItemBinding.bind(itemView)
        fun bind(loadState: LoadState) {
            binding.retryBtn.setOnClickListener { retry() }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryBtn.isVisible = loadState is LoadState.Error
            binding.retryTitleTv.isVisible = loadState is LoadState.Error
            if (loadState is LoadState.Error) {
                onLoadStateError(loadState)
            }
        }
    }
}