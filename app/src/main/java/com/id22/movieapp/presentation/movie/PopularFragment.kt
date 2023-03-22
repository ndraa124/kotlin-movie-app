package com.id22.movieapp.presentation.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id22.core.data.Resource
import com.id22.core.domain.model.Movie
import com.id22.core.utils.recyclerview.LoadMoreStateAdapter
import com.id22.core.utils.recyclerview.LoadStateListener
import com.id22.movieapp.R
import com.id22.movieapp.databinding.FragmentPopularBinding
import com.id22.movieapp.presentation.base.BaseFragment
import com.id22.movieapp.presentation.main.MainActivity
import com.id22.movieapp.presentation.movie.adapter.MovieAdapter
import com.id22.movieapp.viewmodel.MovieViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment : BaseFragment<FragmentPopularBinding>() {

    private val movieViewModel: MovieViewModel by viewModel()
    private val movieAdapter by lazy { MovieAdapter() }

    override fun createLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentPopularBinding = FragmentPopularBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            setTitleActionBar()
            setAdapter()
            setObserverAdapter()
            getAllPopularMovie()
        }
    }

    private fun setTitleActionBar() {
        (activity as MainActivity?)?.setToolbarVisible(
            title = getString(R.string.menu_popular)
        )
    }

    private fun setAdapter() {
        bind.rvMovie.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = movieAdapter.withLoadStateFooter(LoadMoreStateAdapter())
        }

        movieAdapter.setOnItemClickCallback(object : MovieAdapter.ActionAdapter {
            override fun onItemClick(data: Movie) {
                val args = PopularFragmentDirections
                    .actionToDetailMovie()
                    .setMovie(data)
                    .arguments

                setNavigate(
                    resId = R.id.nav_detail,
                    args = args,
                    isPopStackRemove = false,
                )
            }
        })
    }

    private fun setObserverAdapter() {
        movieAdapter.addLoadStateListener { loadState ->
            if (LoadStateListener(loadState).showNotLoadingState()) {
                bind.tvMessage.visibility = View.GONE
            }

            if (LoadStateListener(loadState).showLoadingState()) {
                bind.progressBar.visibility = View.VISIBLE
            } else {
                bind.progressBar.visibility = View.GONE
            }

            if (LoadStateListener(loadState).showEmptyState(movieAdapter.itemCount)) {
                bind.tvMessage.visibility = View.VISIBLE
                bind.tvMessage.text = getString(R.string.empty_now_playing)
            } else {
                bind.tvMessage.visibility = View.GONE
            }

            if (LoadStateListener(loadState).showErrorState()) {
                when (LoadStateListener(loadState).getCodeErrorState()) {
                    404 -> {
                        bind.tvMessage.visibility = View.VISIBLE
                        bind.tvMessage.text = getString(R.string.empty_now_playing)
                    }
                    else -> {
                        LoadStateListener(loadState).showErrorMessageState(
                            activity = requireActivity(),
                            message = getString(R.string.error_try),
                        )
                    }
                }
            }
        }
    }

    private fun getAllPopularMovie() {
        movieViewModel.getPopular().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Success -> {
                        bind.tvMessage.visibility = View.GONE

                        lifecycleScope.launch {
                            movieAdapter.submitData(result.data!!)
                        }
                    }
                    is Resource.Error -> {
                        bind.tvMessage.visibility = View.VISIBLE
                        bind.tvMessage.text = result.message
                    }
                }
            }
        }
    }
}
