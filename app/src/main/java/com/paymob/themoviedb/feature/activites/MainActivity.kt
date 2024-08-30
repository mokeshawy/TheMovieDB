package com.paymob.themoviedb.feature.activites

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.paymob.core.base_activity.BaseActivity
import com.paymob.themoviedb.R
import com.paymob.themoviedb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, Int>() {

    override val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override val navHostResourceId = R.id.navHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

    }
}