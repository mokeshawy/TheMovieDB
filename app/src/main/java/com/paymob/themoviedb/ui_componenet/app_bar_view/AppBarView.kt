package com.paymob.themoviedb.ui_componenet.app_bar_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.paymob.themoviedb.databinding.LayoutAppBarViewBinding

class AppBarView(context: Context, attributes: AttributeSet? = null) :
    FrameLayout(context, attributes) {

    private val binding by lazy {
        LayoutAppBarViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        binding.root
    }


    fun handleBackIvVisibility(isVisible: Boolean) {
        binding.backIv.isVisible = isVisible
    }


    fun setOnBackIvClicked(onBackClicked: () -> Unit) =
        binding.backIv.setOnClickListener { onBackClicked() }


    fun setTitleBarTv(@StringRes title: Int) {
        binding.titleBarTv.setText(title)
    }

    fun setTitleBarTv( title: String) {
        binding.titleBarTv.text = title
    }
}