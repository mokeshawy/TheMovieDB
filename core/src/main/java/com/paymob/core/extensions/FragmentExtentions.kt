package com.paymob.core.extensions

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

fun Fragment.navigate(action: Int, bundle: Bundle? = null) {
    try {
        findNavController().navigate(action, bundle)
    } catch (e: IllegalArgumentException) {
        Timber.e(e)
    }
}

fun Fragment.navigate(directions: NavDirections) {
    findNavController().navigate(directions)
}

fun Fragment.navigateUp() {
    findNavController().navigateUp()
}

fun Fragment.onBackPressed(action: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                action.invoke()
            }
        }
    )
}


fun View.setOnGoToConnectionSettingClicked(activity: Activity) = setOnClickListener {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        activity.startActivity(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))
    } else {
        activity.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }
}


fun Fragment.repeatOnLifecycleScope(
    lifecycle: Lifecycle.State = Lifecycle.State.STARTED,
    block: suspend CoroutineScope.() -> Unit
) : Job{
    return lifecycleScope.launch { repeatOnLifecycle(lifecycle) { block() } }
}