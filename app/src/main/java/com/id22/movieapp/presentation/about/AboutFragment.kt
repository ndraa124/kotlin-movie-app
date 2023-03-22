package com.id22.movieapp.presentation.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.id22.movieapp.databinding.FragmentAboutBinding
import com.id22.movieapp.presentation.base.BaseFragment

class AboutFragment : BaseFragment<FragmentAboutBinding>() {
    override fun createLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentAboutBinding = FragmentAboutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
