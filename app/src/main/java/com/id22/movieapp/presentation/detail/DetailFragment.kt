package com.id22.movieapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.id22.core.BuildConfig
import com.id22.core.data.Resource
import com.id22.core.domain.model.Genres
import com.id22.core.domain.model.Movie
import com.id22.core.domain.model.MovieDetail
import com.id22.core.utils.recyclerview.LoadMoreStateAdapter
import com.id22.core.utils.recyclerview.LoadStateListener
import com.id22.movieapp.R
import com.id22.movieapp.databinding.FragmentDetailBinding
import com.id22.movieapp.presentation.base.BaseFragment
import com.id22.movieapp.presentation.detail.adapter.ReviewsAdapter
import com.id22.movieapp.presentation.detail.adapter.TrailerAdapter
import com.id22.movieapp.presentation.home.adapter.HomeAdapter
import com.id22.movieapp.presentation.main.MainActivity
import com.id22.movieapp.viewmodel.MovieViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private lateinit var movieDetail: MovieDetail
    private val movieViewModel: MovieViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()
    private val recommendationAdapter by lazy { HomeAdapter() }
    private val trailerAdapter by lazy { TrailerAdapter() }
    private val reviewsAdapter by lazy { ReviewsAdapter() }

    private var id: Int = 0
    private var posterPath: String? = null
    private var isFavorite: Boolean? = false

    override fun createLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            setArgs()
            setTitleActionBar()
            setOnClickListener()
            setTrailerAdapter()
            setRecommendationsAdapter()
            setReviewsAdapter()
            setReviewsObserverAdapter()
            getDetailMovie()
            getTrailers()
            getAllRecommendationsMovie()
            getReviews()
        }
    }

    private fun setArgs() {
        if (args.movie != null) {
            id = args.movie!!.id
            posterPath = args.movie!!.posterPath
            isFavorite = args.movie!!.isFavorite
            setStatusFavorite(isFavorite!!)
        }

        Glide.with(requireContext())
            .load(BuildConfig.BASE_URL_IMAGE + posterPath)
            .into(bind.ivMovie)
    }

    private fun setTitleActionBar() {
        (activity as MainActivity?)?.setToolbarVisible(
            title = "",
            isVisible = false
        )
    }

    private fun setOnClickListener() {
        bind.ivBack.setOnClickListener {
            navigateBack()
        }

        bind.content.error.btnTry.setOnClickListener {
            getDetailMovie()
        }

        bind.fab.setOnClickListener {
            isFavorite = !isFavorite!!
            movieViewModel.setFavoriteMovie(args.movie!!, isFavorite!!)
            setStatusFavorite(isFavorite!!)
        }
    }

    private fun setTrailerAdapter() {
        bind.content.rvTrailer.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = trailerAdapter
        }
    }

    private fun setRecommendationsAdapter() {
        bind.content.rvRecommendation.apply {
            adapter = recommendationAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        recommendationAdapter.setOnItemClickCallback(object : HomeAdapter.ActionAdapter {
            override fun onItemClick(data: Movie) {
                val args = DetailFragmentDirections
                    .actionToDetail()
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

    private fun setReviewsAdapter() {
        bind.content.rvReviews.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = reviewsAdapter.withLoadStateFooter(LoadMoreStateAdapter())
        }
    }

    private fun setReviewsObserverAdapter() {
        reviewsAdapter.addLoadStateListener { loadState ->
            if (LoadStateListener(loadState).showNotLoadingState()) {
                bind.content.progressBarReviews.visibility = View.GONE
            }

            if (LoadStateListener(loadState).showLoadingState()) {
                bind.content.progressBarReviews.visibility = View.VISIBLE
            } else {
                bind.content.progressBarReviews.visibility = View.GONE
            }

            if (LoadStateListener(loadState).showEmptyState(reviewsAdapter.itemCount)) {
                bind.content.tvMessageReviews.visibility = View.VISIBLE
                bind.content.tvMessageReviews.text = getString(R.string.empty_reviews)
            } else {
                bind.content.tvMessageReviews.visibility = View.GONE
            }

            if (LoadStateListener(loadState).showErrorState()) {
                when (LoadStateListener(loadState).getCodeErrorState()) {
                    404 -> {
                        bind.content.tvMessageReviews.visibility = View.VISIBLE
                        bind.content.tvMessageReviews.text = getString(R.string.empty_reviews)
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

    private fun setRuntime(runtime: Int): String {
        val hours = runtime / 60
        val minutes = runtime % 60

        return if (hours > 0) {
            "${hours}h ${minutes}m"
        } else {
            "${minutes}m"
        }
    }

    private fun setGenres(genres: List<Genres>): String {
        var result = ""

        genres.forEach {
            result += "${it.name}/"
        }

        return result.substring(0, result.length - 2)
    }

    private fun setStatusFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            bind.fab.drawable.setTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorPrimary
                )
            )
        } else {
            bind.fab.drawable.setTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorDisable
                )
            )
        }
    }

    private fun showDetailLayout() {
        val rating = movieDetail.voteAverage / 2
        val ratingDigit = String.format("%.1f", rating).toDouble()

        bind.content.ratingBar.rating = rating.toFloat()
        bind.content.tvRating.text = "$ratingDigit"
        bind.content.tvTitle.text = movieDetail.title
        bind.content.tvGenre.text = setGenres(movieDetail.genres!!)
        bind.content.tvRuntime.text = setRuntime(movieDetail.runtime)
        bind.content.tvOverview.text = movieDetail.overview

        getAllRecommendationsMovie()
    }

    private fun getDetailMovie() {
        movieViewModel.getDetailMovie(id).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.ShowLoading -> {
                        bind.content.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.HideLoading -> {
                        bind.content.progressBar.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        if (result.data != null) {
                            movieDetail = result.data!!
                            showDetailLayout()
                        }
                    }
                    is Resource.Error -> {
                        bind.content.error.clError.visibility = View.VISIBLE
                        bind.content.clDetail.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun getAllRecommendationsMovie() {
        movieViewModel.getRecommendations(id).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.ShowLoading -> {
                        bind.content.progressBarRecommendation.visibility = View.VISIBLE
                    }
                    is Resource.HideLoading -> {
                        bind.content.progressBarRecommendation.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        bind.content.tvMessage.visibility = View.GONE
                        recommendationAdapter.submitList(result.data)
                    }
                    is Resource.Empty -> {
                        bind.content.tvMessage.visibility = View.VISIBLE
                        bind.content.tvMessage.text = getString(R.string.empty_recommendations)
                    }
                    is Resource.Error -> {
                        bind.content.tvMessage.visibility = View.VISIBLE
                        bind.content.tvMessage.text = result.message
                    }
                }
            }
        }
    }

    private fun getTrailers() {
        movieViewModel.getTrailers(id).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.ShowLoading -> {
                        bind.content.progressBarTrailer.visibility = View.VISIBLE
                    }
                    is Resource.HideLoading -> {
                        bind.content.progressBarTrailer.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        bind.content.tvMessageTrailer.visibility = View.GONE
                        trailerAdapter.submitList(result.data)
                    }
                    is Resource.Empty -> {
                        bind.content.tvMessageTrailer.visibility = View.VISIBLE
                        bind.content.tvMessageTrailer.text = getString(R.string.empty_trailer)
                    }
                    is Resource.Error -> {
                        bind.content.tvMessageTrailer.visibility = View.VISIBLE
                        bind.content.tvMessageTrailer.text = result.message
                    }
                }
            }
        }
    }

    private fun getReviews() {
        movieViewModel.getReviews(id).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Success -> {
                        bind.content.tvMessageReviews.visibility = View.GONE

                        lifecycleScope.launch {
                            reviewsAdapter.submitData(result.data!!)
                        }
                    }
                    is Resource.Error -> {
                        bind.content.tvMessageReviews.visibility = View.VISIBLE
                        bind.content.tvMessageReviews.text = result.message
                    }
                }
            }
        }
    }
}
