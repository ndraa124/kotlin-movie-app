package com.id22.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.id22.core.domain.model.Movie
import com.id22.core.domain.usecase.MoviesUseCase
import kotlinx.coroutines.Dispatchers

class MovieViewModel(private val useCase: MoviesUseCase) : ViewModel() {
    fun getNowPlayingLimit() = useCase.getNowPlayingLimit()
        .asLiveData(Dispatchers.Main)

    fun getNowPlaying() = useCase.getNowPlaying()
        .asLiveData(Dispatchers.Main)

    fun getPopularLimit() = useCase.getPopularLimit()
        .asLiveData(Dispatchers.Main)

    fun getPopular() = useCase.getPopular()
        .asLiveData(Dispatchers.Main)

    fun getTopRatedLimit() = useCase.getTopRatedLimit()
        .asLiveData(Dispatchers.Main)

    fun getTopRated() = useCase.getTopRated()
        .asLiveData(Dispatchers.Main)

    fun getGenre() = useCase.getGenre()
        .asLiveData(Dispatchers.Main)

    fun getDiscover(genreIds: Int) = useCase.getDiscover(genreIds)
        .asLiveData(Dispatchers.Main)

    fun getDetailMovie(id: Int) = useCase.getDetailMovie(id)
        .asLiveData(Dispatchers.Main)

    fun getRecommendations(id: Int) = useCase.getRecommendations(id)
        .asLiveData(Dispatchers.Main)

    fun getTrailers(id: Int) = useCase.getTrailers(id)
        .asLiveData(Dispatchers.Main)

    fun getReviews(id: Int) = useCase.getReviews(id)
        .asLiveData(Dispatchers.Main)

    fun setFavoriteMovie(data: Movie, state: Boolean) = useCase.setFavoriteMovie(data, state)

    fun searchMovie(keyword: String) = useCase.searchMovie(keyword)
        .asLiveData(Dispatchers.Main)
}
