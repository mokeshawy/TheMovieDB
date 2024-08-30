package com.paymob.core.base_activity

import android.net.Network
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.NavHostFragment
import com.paymob.core.BuildConfig
import com.paymob.core.connectivity.connectivity_manager.ConnectivityManager
import com.paymob.core.connectivity.connectivity_manager.NetworkAwareComponent
import com.paymob.core.error.AppError
import com.paymob.core.error.AppErrorHandler
import javax.inject.Inject

abstract class BaseActivity<dataBinding : ViewDataBinding, _navHostResourceId : Int?> :
    AppCompatActivity(), NetworkAwareComponent, AppErrorHandler {


    abstract val binding: dataBinding
    abstract val navHostResourceId: _navHostResourceId

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(navHostResourceId ?: 0) as NavHostFragment
    }

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        hideSystemBars(binding.root)
        handleScreenShootAndRecordSecure()
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemBars(binding.root)
        }
    }

    private fun hideSystemBars(root: View) {
        val windowInsetsController = WindowInsetsControllerCompat(window, root)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun handleScreenShootAndRecordSecure() {
        if (BuildConfig.DEBUG) return
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    fun getCurrentFragment() = navHostFragment.childFragmentManager.fragments.firstOrNull()

    override fun onNetworkAvailable(network: Network) {
        (getCurrentFragment() as? NetworkAwareComponent)?.onNetworkAvailable(network)
    }

    override fun onNetworkLost(network: Network) {
        (getCurrentFragment() as? NetworkAwareComponent)?.onNetworkLost(network)
    }

    override fun handleError(error: AppError, callback: AppError.() -> Unit) {
        error.logError()
        callback(error)
    }
}