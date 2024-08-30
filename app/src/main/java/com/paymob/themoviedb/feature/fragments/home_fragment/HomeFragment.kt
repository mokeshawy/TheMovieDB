package com.paymob.themoviedb.feature.fragments.home_fragment

import android.os.Bundle
import android.view.View
import com.paymob.themoviedb.core.MoveDbBaseFragment
import com.paymob.themoviedb.databinding.FragmentHomeBinding

class HomeFragment : MoveDbBaseFragment<FragmentHomeBinding>() {

    override fun layoutInflater() = FragmentHomeBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}