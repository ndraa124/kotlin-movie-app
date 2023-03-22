package com.id22.movieapp.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.id22.movieapp.R

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected lateinit var bind: VB

    protected abstract fun createLayout(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        bind = createLayout(inflater, container)
        return bind.root
    }

    protected fun setNavigate(resId: Int, args: Bundle? = null, isPopStackRemove: Boolean) {
        if (isPopStackRemove) {
            findNavController().navigate(
                resId = resId,
                args = args,
                navOptions = NavOptions.Builder()
                    .setPopUpTo(
                        findNavController().graph.startDestinationId,
                        inclusive = false,
                    )
                    .setEnterAnim(R.anim.fade_in)
                    .setPopEnterAnim(R.anim.fade_in)
                    .setExitAnim(R.anim.fade_out)
                    .setPopExitAnim(R.anim.fade_out)
                    .build(),
            )
        } else {
            findNavController().navigate(
                resId = resId,
                args = args,
                navOptions = NavOptions.Builder()
                    .setEnterAnim(R.anim.fade_in)
                    .setPopEnterAnim(R.anim.fade_in)
                    .setExitAnim(R.anim.fade_out)
                    .setPopExitAnim(R.anim.fade_out)
                    .build(),
            )
        }
    }

    fun navigateBack() {
        findNavController().navigateUp()
    }
}
