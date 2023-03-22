package com.id22.movieapp.presentation.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id22.core.data.Resource
import com.id22.core.domain.model.Movie
import com.id22.core.utils.*
import com.id22.core.utils.recyclerview.LoadMoreStateAdapter
import com.id22.core.utils.recyclerview.LoadStateListener
import com.id22.movieapp.R
import com.id22.movieapp.databinding.FragmentSearchBinding
import com.id22.movieapp.presentation.base.BaseFragment
import com.id22.movieapp.presentation.main.MainActivity
import com.id22.movieapp.presentation.movie.adapter.MovieAdapter
import com.id22.movieapp.viewmodel.MovieViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val movieViewModel: MovieViewModel by viewModel()
    private val movieAdapter by lazy { MovieAdapter() }

    private var keyword: String = ""

    override fun createLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            setTitleActionBar()
            setOnClickListener()
            setAdapter()
            setObserverAdapter()
            setSearchValidate()
        }
    }

    private fun setTitleActionBar() {
        (activity as MainActivity?)?.setToolbarVisible(
            title = "",
            isVisible = false,
        )
    }

    private fun setOnClickListener() {
        bind.layoutToolbar.ivBack.setOnClickListener {
            navigateBack()
        }

        bind.layoutToolbar.etSearch.onSearch {
            bind.layoutToolbar.etSearch.hideSoftKeyboard()
            keyword = bind.layoutToolbar.etSearch.text.toString().trim()
            getAllMovie()
        }

        bind.layoutToolbar.inputLayoutSearch.setEndIconOnClickListener {
            setHideKeyboard(it, requireActivity())
            checkIsSearchNotEmpty()
        }
    }

    private fun setAdapter() {
        bind.rvMovie.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = movieAdapter.withLoadStateFooter(LoadMoreStateAdapter())
        }

        movieAdapter.setOnItemClickCallback(object : MovieAdapter.ActionAdapter {
            override fun onItemClick(data: Movie) {
                val args = SearchFragmentDirections
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

    @SuppressLint("CheckResult")
    private fun setSearchValidate() {
        bind.layoutToolbar.etSearch.showSoftKeyboard()

        val searchStream = RxTextView.textChanges(bind.layoutToolbar.etSearch)
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { searchValidate(it) }
        searchStream.subscribe {
            showSearchAlert(it)
        }
    }

    private fun searchValidate(value: CharSequence): Boolean {
        return value.isNotEmpty()
    }

    private fun showSearchAlert(isValid: Boolean) {
        if (!isValid) {
            keyword = ""
            getAllMovie()
        } else {
            keyword = bind.layoutToolbar.etSearch.text.toString().trim()
            getAllMovie()
        }
    }

    private fun checkIsSearchNotEmpty() {
        if (bind.layoutToolbar.etSearch.text.toString().isNotEmpty()) {
            keyword = ""
            bind.layoutToolbar.etSearch.text?.clear()
        }
    }

    private fun getAllMovie() {
        movieViewModel.searchMovie(keyword).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Success -> {
                        bind.rvMovie.visibility = View.VISIBLE
                        bind.tvMessage.visibility = View.GONE

                        lifecycleScope.launch {
                            movieAdapter.submitData(result.data!!)
                        }
                    }
                    is Resource.Error -> {
                        bind.rvMovie.visibility = View.GONE
                        bind.tvMessage.visibility = View.VISIBLE
                        bind.tvMessage.text = result.message
                    }
                }
            }
        }
    }
}
