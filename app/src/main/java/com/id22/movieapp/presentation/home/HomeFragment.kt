package com.id22.movieapp.presentation.home

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id22.core.data.Resource
import com.id22.core.domain.model.Genres
import com.id22.core.domain.model.Movie
import com.id22.core.utils.recyclerview.LoadMoreStateAdapter
import com.id22.core.utils.recyclerview.LoadStateListener
import com.id22.movieapp.R
import com.id22.movieapp.databinding.FragmentHomeBinding
import com.id22.movieapp.presentation.base.BaseFragment
import com.id22.movieapp.presentation.home.adapter.DiscoverAdapter
import com.id22.movieapp.presentation.home.adapter.GenresAdapter
import com.id22.movieapp.presentation.home.adapter.HomeAdapter
import com.id22.movieapp.presentation.main.MainActivity
import com.id22.movieapp.presentation.movie.NowPlayingFragmentDirections
import com.id22.movieapp.viewmodel.MovieViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val movieViewModel: MovieViewModel by viewModel()
    private val nowPlayingAdapter by lazy { HomeAdapter() }
    private val popularAdapter by lazy { HomeAdapter() }
    private val topRatedAdapter by lazy { HomeAdapter() }
    private val genresAdapter by lazy { GenresAdapter() }
    private val discoverAdapter by lazy { DiscoverAdapter() }

    override fun createLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            setTitleActionBar()
            setOptionMenuActionBar()
            setOnCLickListener()
            setNowPlayingAdapter()
            setPopularAdapter()
            setTopRatedAdapter()
            setGenresAdapter()
            setDiscoverAdapter()
            setDiscoverObserverAdapter()
            getAllPlayingMovie()
            getAllPopularMovie()
            getAllTopRatedMovie()
            getAllGenres()
        }
    }

    private fun setTitleActionBar() {
        (activity as MainActivity?)?.setToolbarVisible("")
    }

    private fun setOptionMenuActionBar() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.nav_search -> {
                        setNavigate(
                            resId = R.id.nav_search,
                            isPopStackRemove = false,
                        )
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setOnCLickListener() {
        bind.nowPlaying.tvSeeAll.setOnClickListener {
            setNavigate(
                resId = R.id.nav_now_playing,
                isPopStackRemove = false,
            )
        }

        bind.popular.tvSeeAll.setOnClickListener {
            setNavigate(
                resId = R.id.nav_popular,
                isPopStackRemove = false,
            )
        }

        bind.topRated.tvSeeAll.setOnClickListener {
            setNavigate(
                resId = R.id.nav_top_rated,
                isPopStackRemove = false,
            )
        }
    }

    private fun setNowPlayingAdapter() {
        bind.nowPlaying.tvTitle.text = getString(R.string.home_now_playing)

        bind.nowPlaying.rvMovie.apply {
            adapter = nowPlayingAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        nowPlayingAdapter.setOnItemClickCallback(object : HomeAdapter.ActionAdapter {
            override fun onItemClick(data: Movie) {
                navigateToDetail(data)
            }
        })
    }

    private fun setPopularAdapter() {
        bind.popular.tvTitle.text = getString(R.string.home_popular)

        bind.popular.rvMovie.apply {
            adapter = popularAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        popularAdapter.setOnItemClickCallback(object : HomeAdapter.ActionAdapter {
            override fun onItemClick(data: Movie) {
                navigateToDetail(data)
            }
        })
    }

    private fun setTopRatedAdapter() {
        bind.topRated.tvTitle.text = getString(R.string.home_top_rated)

        bind.topRated.rvMovie.apply {
            adapter = topRatedAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        topRatedAdapter.setOnItemClickCallback(object : HomeAdapter.ActionAdapter {
            override fun onItemClick(data: Movie) {
                navigateToDetail(data)
            }
        })
    }

    private fun setGenresAdapter() {
        bind.genre.rvGenre.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = genresAdapter
        }

        genresAdapter.setOnItemClickCallback(object : GenresAdapter.ActionAdapter {
            override fun onItemClick(data: Genres) {
                getDiscover(data.id)
            }
        })
    }

    private fun setDiscoverAdapter() {
        bind.genre.rvMovie.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = discoverAdapter.withLoadStateFooter(LoadMoreStateAdapter())
        }

        discoverAdapter.setOnItemClickCallback(object : DiscoverAdapter.ActionAdapter {
            override fun onItemClick(data: Movie) {
                val args = NowPlayingFragmentDirections
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

    private fun setDiscoverObserverAdapter() {
        discoverAdapter.addLoadStateListener { loadState ->
            if (LoadStateListener(loadState).showNotLoadingState()) {
                bind.genre.tvMessage.visibility = View.GONE
            }

            if (LoadStateListener(loadState).showLoadingState()) {
                bind.genre.progressBar.visibility = View.VISIBLE
            } else {
                bind.genre.progressBar.visibility = View.GONE
            }

            if (LoadStateListener(loadState).showEmptyState(discoverAdapter.itemCount)) {
                bind.genre.tvMessage.visibility = View.VISIBLE
                bind.genre.tvMessage.text = getString(R.string.empty_now_playing)
            } else {
                bind.genre.tvMessage.visibility = View.GONE
            }

            if (LoadStateListener(loadState).showErrorState()) {
                when (LoadStateListener(loadState).getCodeErrorState()) {
                    404 -> {
                        bind.genre.tvMessage.visibility = View.VISIBLE
                        bind.genre.tvMessage.text = getString(R.string.empty_now_playing)
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

    private fun navigateToDetail(data: Movie) {
        val args = HomeFragmentDirections.actionToDetailMovie()
            .setMovie(data)
            .arguments

        setNavigate(
            resId = R.id.nav_detail,
            args = args,
            isPopStackRemove = false,
        )
    }

    private fun getAllPlayingMovie() {
        movieViewModel.getNowPlayingLimit().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.ShowLoading -> {
                        bind.nowPlaying.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.HideLoading -> {
                        bind.nowPlaying.progressBar.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        bind.nowPlaying.tvMessage.visibility = View.GONE
                        nowPlayingAdapter.submitList(result.data)
                    }
                    is Resource.Empty -> {
                        bind.nowPlaying.tvMessage.visibility = View.VISIBLE
                        bind.nowPlaying.tvMessage.text = getString(R.string.empty_now_playing)
                    }
                    is Resource.Error -> {
                        bind.nowPlaying.tvMessage.visibility = View.VISIBLE
                        bind.nowPlaying.tvMessage.text = result.message
                    }
                }
            }
        }
    }

    private fun getAllPopularMovie() {
        movieViewModel.getPopularLimit().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.ShowLoading -> {
                        bind.popular.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.HideLoading -> {
                        bind.popular.progressBar.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        bind.popular.tvMessage.visibility = View.GONE
                        popularAdapter.submitList(result.data)
                    }
                    is Resource.Empty -> {
                        bind.popular.tvMessage.visibility = View.VISIBLE
                        bind.popular.tvMessage.text = getString(R.string.empty_popular)
                    }
                    is Resource.Error -> {
                        bind.popular.tvMessage.visibility = View.VISIBLE
                        bind.popular.tvMessage.text = result.message
                    }
                }
            }
        }
    }

    private fun getAllTopRatedMovie() {
        movieViewModel.getTopRatedLimit().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.ShowLoading -> {
                        bind.topRated.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.HideLoading -> {
                        bind.topRated.progressBar.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        bind.topRated.tvMessage.visibility = View.GONE
                        topRatedAdapter.submitList(result.data)
                    }
                    is Resource.Empty -> {
                        bind.topRated.tvMessage.visibility = View.VISIBLE
                        bind.topRated.tvMessage.text = getString(R.string.empty_top_rated)
                    }
                    is Resource.Error -> {
                        bind.topRated.tvMessage.visibility = View.VISIBLE
                        bind.topRated.tvMessage.text = result.message
                    }
                }
            }
        }
    }

    private fun getAllGenres() {
        movieViewModel.getGenre().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.ShowLoading -> {
                        bind.genre.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.HideLoading -> {
                        bind.genre.progressBar.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        bind.genre.tvMessage.visibility = View.GONE
                        genresAdapter.submitList(result.data)
                    }
                    is Resource.Empty -> {
                        bind.genre.tvMessage.visibility = View.VISIBLE
                        bind.genre.tvMessage.text = getString(R.string.empty_top_rated)
                    }
                    is Resource.Error -> {
                        bind.genre.tvMessage.visibility = View.VISIBLE
                        bind.genre.tvMessage.text = result.message
                    }
                }
            }
        }
    }

    private fun getDiscover(genreIds: Int) {
        movieViewModel.getDiscover(genreIds).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Success -> {
                        bind.genre.tvMessage.visibility = View.GONE

                        lifecycleScope.launch {
                            discoverAdapter.submitData(result.data!!)
                        }
                    }
                    is Resource.Error -> {
                        bind.genre.tvMessage.visibility = View.VISIBLE
                        bind.genre.tvMessage.text = result.message
                    }
                }
            }
        }
    }
}
