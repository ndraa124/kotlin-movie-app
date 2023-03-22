package com.id22.core.domain.usecase

import androidx.paging.PagingData
import com.id22.core.data.Resource
import com.id22.core.domain.model.*
import com.id22.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow

class MoviesInteractor(val repo: IMoviesRepository) : MoviesUseCase {
    override fun getNowPlayingLimit(): Flow<Resource<List<Movie>>> =
        repo.getNowPlayingLimit()

    override fun getNowPlaying(): Flow<Resource<PagingData<Movie>>> =
        repo.getNowPlaying()

    override fun getPopularLimit(): Flow<Resource<List<Movie>>> =
        repo.getPopularLimit()

    override fun getPopular(): Flow<Resource<PagingData<Movie>>> =
        repo.getPopular()

    override fun getTopRatedLimit(): Flow<Resource<List<Movie>>> =
        repo.getTopRatedLimit()

    override fun getTopRated(): Flow<Resource<PagingData<Movie>>> =
        repo.getTopRated()

    override fun getGenre(): Flow<Resource<List<Genres>>> =
        repo.getGenre()

    override fun getDiscover(genreIds: Int): Flow<Resource<PagingData<Movie>>> =
        repo.getDiscover(genreIds)

    override fun getDetailMovie(id: Int): Flow<Resource<MovieDetail>> =
        repo.getDetailMovie(id)

    override fun getRecommendations(id: Int): Flow<Resource<List<Movie>>> =
        repo.getRecommendations(id)

    override fun getTrailers(id: Int): Flow<Resource<List<Trailers>>> =
        repo.getTrailers(id)

    override fun getReviews(id: Int): Flow<Resource<PagingData<Reviews>>> =
        repo.getReviews(id)

    override fun getFavoriteMovie(): Flow<List<Movie>> =
        repo.getFavoriteMovie()

    override fun setFavoriteMovie(data: Movie, state: Boolean) =
        repo.setFavoriteMovie(data, state)

    override fun searchMovie(keyword: String): Flow<Resource<PagingData<Movie>>> =
        repo.searchMovie(keyword)

}
