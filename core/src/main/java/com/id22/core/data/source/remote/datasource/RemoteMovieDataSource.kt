package com.id22.core.data.source.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.id22.core.data.source.remote.network.ApiResponse
import com.id22.core.data.source.remote.network.service.ApiService
import com.id22.core.data.source.remote.pagingsource.DiscoverPagingSource
import com.id22.core.data.source.remote.pagingsource.MoviesPagingSource
import com.id22.core.data.source.remote.pagingsource.ReviewsPagingSource
import com.id22.core.data.source.remote.pagingsource.SearchPagingSource
import com.id22.core.data.source.remote.response.movie.*
import com.id22.core.utils.NETWORK_PAGE_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteMovieDataSource(private val apiService: ApiService) {

    fun getNowPlayingLimit(): Flow<ApiResponse<List<MovieResponse>>> =
        flow {
            try {
                val response = apiService.getAllNowPlayingMovie()

                if (!response.results.isNullOrEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getNowPlaying(): Flow<ApiResponse<PagingData<MovieResponse>>> =
        flow {
            try {
                val pager = Pager(
                    config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false,
                    ),
                    pagingSourceFactory = { MoviesPagingSource(apiService, "playing") },
                ).flow

                emit(ApiResponse.Success(pager.first()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getPopularLimit(): Flow<ApiResponse<List<MovieResponse>>> =
        flow {
            try {
                val response = apiService.getAllPopularMovie()

                if (!response.results.isNullOrEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getPopular(): Flow<ApiResponse<PagingData<MovieResponse>>> =
        flow {
            try {
                val pager = Pager(
                    config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false,
                    ),
                    pagingSourceFactory = { MoviesPagingSource(apiService, "popular") },
                ).flow

                emit(ApiResponse.Success(pager.first()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getTopRatedLimit(): Flow<ApiResponse<List<MovieResponse>>> =
        flow {
            try {
                val response = apiService.getAllTopRatedMovie()

                if (!response.results.isNullOrEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getTopRated(): Flow<ApiResponse<PagingData<MovieResponse>>> =
        flow {
            try {
                val pager = Pager(
                    config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false,
                    ),
                    pagingSourceFactory = { MoviesPagingSource(apiService, "top") },
                ).flow

                emit(ApiResponse.Success(pager.first()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getGenres(): Flow<ApiResponse<List<GenresResponse>>> =
        flow {
            try {
                val response = apiService.getAllGenres()

                if (response.genres.isNotEmpty()) {
                    emit(ApiResponse.Success(response.genres))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getDiscover(genreIds: Int): Flow<ApiResponse<PagingData<MovieResponse>>> =
        flow {
            try {
                val pager = Pager(
                    config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false,
                    ),
                    pagingSourceFactory = { DiscoverPagingSource(apiService, genreIds) },
                ).flow

                emit(ApiResponse.Success(pager.first()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getDetailMovie(id: Int): Flow<ApiResponse<MovieDetailResponse>> =
        flow {
            try {
                val response = apiService.getDetailMovie(id)

                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getRecommendations(id: Int): Flow<ApiResponse<List<MovieResponse>>> =
        flow {
            try {
                val response = apiService.getAllRecommendations(id)

                if (!response.results.isNullOrEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getTrailers(id: Int): Flow<ApiResponse<List<TrailersResponse>>> =
        flow {
            try {
                val response = apiService.getAllVideos(id)

                if (!response.results.isNullOrEmpty()) {
                    emit(ApiResponse.Success(response.results!!))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getReviews(id: Int): Flow<ApiResponse<PagingData<ReviewsResponse>>> =
        flow {
            try {
                val pager = Pager(
                    config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false,
                    ),
                    pagingSourceFactory = { ReviewsPagingSource(apiService, id) },
                ).flow

                emit(ApiResponse.Success(pager.first()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun searchMovie(keyword: String): Flow<ApiResponse<PagingData<MovieResponse>>> =
        flow {
            try {
                val pager = Pager(
                    config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false,
                    ),
                    pagingSourceFactory = {
                        SearchPagingSource(
                            apiService = apiService,
                            keyword = keyword
                        )
                    },
                ).flow

                emit(ApiResponse.Success(pager.first()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
}
