package com.id22.core.domain.repository

import androidx.paging.PagingData
import com.id22.core.data.Resource
import com.id22.core.domain.model.*
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {

    fun getNowPlayingLimit(): Flow<Resource<List<Movie>>>

    fun getNowPlaying(): Flow<Resource<PagingData<Movie>>>

    fun getPopularLimit(): Flow<Resource<List<Movie>>>

    fun getPopular(): Flow<Resource<PagingData<Movie>>>

    fun getTopRatedLimit(): Flow<Resource<List<Movie>>>

    fun getTopRated(): Flow<Resource<PagingData<Movie>>>

    fun getGenre(): Flow<Resource<List<Genres>>>

    fun getDiscover(genreIds: Int): Flow<Resource<PagingData<Movie>>>

    fun getDetailMovie(id: Int): Flow<Resource<MovieDetail>>

    fun getRecommendations(id: Int): Flow<Resource<List<Movie>>>

    fun getTrailers(id: Int): Flow<Resource<List<Trailers>>>

    fun getReviews(id: Int): Flow<Resource<PagingData<Reviews>>>

    fun getFavoriteMovie(): Flow<List<Movie>>

    fun setFavoriteMovie(data: Movie, state: Boolean)

    fun searchMovie(keyword: String): Flow<Resource<PagingData<Movie>>>
}
