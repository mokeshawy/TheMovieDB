package com.paymob.core.base_fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.jakewharton.processphoenix.ProcessPhoenix
import com.paymob.core.base_activity.BaseActivity
import com.paymob.core.connectivity.connectivity_manager.NetworkAwareComponent
import com.paymob.core.error.AppError
import com.paymob.core.error.AppErrorHandler
import com.paymob.core.error.GeneralException
import com.paymob.core.error.IoException
import com.paymob.core.error.ResponseErrorException
import com.paymob.core.error.ResponseNotFoundErrorException
import com.paymob.core.error.ResponseUnAuthorizedException
import com.paymob.core.extensions.showToast
import com.paymob.core.storage_manager.StorageManager
import com.paymob.core.storage_manager.di.StorageManagerModule.SHARED_PREFERENCE
import javax.inject.Inject
import javax.inject.Named

abstract class BaseFragment<dataBinding : ViewDataBinding> : Fragment(), NetworkAwareComponent {

    private var bindingField: dataBinding? = null

    protected val binding: dataBinding
        get() = bindingField!!


    protected val connectivityManager get() = (activity as? BaseActivity<*, *>)?.connectivityManager


    @Inject
    @Named(SHARED_PREFERENCE)
    lateinit var sharedPreference: StorageManager


    private val inputMethodManager
        get() = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager


    override fun onStart() {
        super.onStart()
        checkOnSelfPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        bindingField = layoutInflater()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingField = null
    }


    abstract fun layoutInflater(): dataBinding


    fun Int.localize() = getString(this)

    fun Int.localize(vararg args: Any) = getString(this, *args)

    fun Int.showShortToast() = requireActivity().showToast(localize(), Toast.LENGTH_SHORT)

    fun showShortToast(message: String) = requireActivity().showToast(message, Toast.LENGTH_SHORT)

    fun Int.showLongToast() = showLongToast(localize())

    fun showLongToast(message: String) = requireActivity().showToast(message)


    override fun onNetworkAvailable(network: Network) {
        //TODO NO NEED SHOW ANY MESSAGE HERE
    }

    override fun onNetworkLost(network: Network) {
        //TODO NO NEED SHOW ANY MESSAGE HERE
    }


    protected fun showKeyboard(view: View) =
        inputMethodManager?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)


    protected fun hideKeyboard(view: View) =
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)


    protected fun handleApplicationRestart() = ProcessPhoenix.triggerRebirth(requireActivity())

    fun AppError.handleError(callback: AppError.() -> Unit) {
        (activity as? AppErrorHandler)?.handleError(this) {
            val message = logMessage ?: ""
            when (exception) {
                is GeneralException -> handleGeneralExceptionError(message)
                is IoException -> handleIoExceptionError(message)
                is ResponseErrorException -> handleGeneralResponseError(message)
                is ResponseUnAuthorizedException -> handleUnauthorizedError(message)
                is ResponseNotFoundErrorException -> handleNotFoundError(message)
                else -> handleOtherErrors(this)
            }
        }
        callback()
    }


    private val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.READ_PHONE_STATE)
    } else {
        arrayOf(Manifest.permission.READ_PHONE_STATE)
    }

    private fun checkOnSelfPermission() {
        if (permissions.all { checkSelfPermission(requireContext(), it) == PERMISSION_GRANTED }) {
            onPermissionsGranted()
        } else {
            requestMultiplePermissionsLauncher.launch(permissions)
        }
    }

    private val requestMultiplePermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.filter { !it.value }.forEach {
                onPermissionDenied(it.key)
                return@registerForActivityResult
            }
            onPermissionsGranted()
        }

    protected open fun onPermissionsGranted() {}

    protected open fun onPermissionDenied(permission: String?) {}


    protected open fun handleOtherErrors(error: AppError): AppError {
        return error
    }

    protected open fun handleGeneralExceptionError(message: String) = showShortToast(message)


    protected open fun handleIoExceptionError(message: String) = showShortToast(message)


    protected open fun handleGeneralResponseError(message: String) = showShortToast(message)


    protected open fun handleUnauthorizedError(message: String) = showShortToast(message)


    protected open fun handleNotFoundError(message: String) = showShortToast(message)

}