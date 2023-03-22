package com.id22.favorite.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id22.core.domain.model.Movie
import com.id22.favorite.databinding.FragmentFavoriteBinding
import com.id22.favorite.di.favoriteModule
import com.id22.favorite.presentation.adapter.FavoriteAdapter
import com.id22.favorite.viewmodel.FavoriteViewModel
import com.id22.movieapp.R
import com.id22.movieapp.presentation.base.BaseFragment
import com.id22.movieapp.presentation.main.MainActivity
import com.id22.movieapp.presentation.movie.NowPlayingFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    private val viewModel: FavoriteViewModel by viewModel()
    private val favoriteAdapter by lazy { FavoriteAdapter() }

    override fun createLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(favoriteModule)

        if (activity != null) {
            setTitleActionBar()
            setAdapter()
        }
    }

    override fun onResume() {
        super.onResume()
        getAllFavorite()
    }

    private fun setTitleActionBar() {
        (activity as MainActivity?)?.setToolbarVisible(
            title = getString(R.string.menu_favorite)
        )
    }

    private fun setAdapter() {
        bind.rvFavorite.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        favoriteAdapter.setOnItemClickCallback(object : FavoriteAdapter.ActionAdapter {
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

    private fun getAllFavorite() {
        bind.progressBar.visibility = View.VISIBLE

        viewModel.getFavoriteMovie.observe(viewLifecycleOwner) { result ->
            if (!result.isNullOrEmpty()) {
                bind.progressBar.visibility = View.GONE
                bind.rvFavorite.visibility = View.VISIBLE
                bind.tvMessage.visibility = View.GONE

                favoriteAdapter.submitList(result)
            } else {
                bind.progressBar.visibility = View.GONE
                bind.rvFavorite.visibility = View.GONE
                bind.tvMessage.visibility = View.VISIBLE
                bind.tvMessage.text = getString(R.string.empty_favorite)
            }
        }
    }
}
