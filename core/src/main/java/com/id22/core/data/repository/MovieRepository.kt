package com.id22.core.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.id22.core.data.Resource
import com.id22.core.data.boundresource.NetworkBoundResource
import com.id22.core.data.boundresource.PagingBoundResource
import com.id22.core.data.source.local.datasource.LocalMovieDataSource
import com.id22.core.data.source.remote.datasource.RemoteMovieDataSource
import com.id22.core.data.source.remote.network.ApiResponse
import com.id22.core.data.source.remote.response.movie.*
import com.id22.core.domain.model.*
import com.id22.core.domain.repository.IMoviesRepository
import com.id22.core.utils.AppExecutors
import com.id22.core.utils.mapper.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MovieRepository(
    val remoteDataSource: RemoteMovieDataSource,
    val localDataSource: LocalMovieDataSource,
    private val appExecutors: AppExecutors,
) : IMoviesRepository {

    companion object {
        const val playing = "playing"
        const val popular = "popular"
        const val top = "top"
    }

    override fun getNowPlayingLimit(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getAllMovie(playing)
                    .map { DataMapper.mapEntitiesToDomain(it) }

            override suspend fun shouldFetch(data: List<Movie>?): Boolean {
                return when (val fetch = remoteDataSource.getNowPlayingLimit().first()) {
                    is ApiResponse.Success -> {
                        if (data == null || data.isEmpty()) {
                            true
                        } else {
                            fetch.data[0].id != data[0].id
                        }
                    }
                    else -> false
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getNowPlayingLimit()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val dataList = DataMapper.mapResponsesToEntities(data, playing)
                localDataSource.insertMovie(dataList, playing)
            }
        }.asFlow()

    override fun getNowPlaying(): Flow<Resource<PagingData<Movie>>> =
        object : PagingBoundResource<PagingData<Movie>, PagingData<MovieResponse>>() {
            override suspend fun dataCallback(data: PagingData<MovieResponse>): PagingData<Movie> =
                data.map { DataMapper.mapResponseToDomain(it) }

            override fun dataCreate(): Flow<ApiResponse<PagingData<MovieResponse>>> {
                return remoteDataSource.getNowPlaying()
            }
        }.asFlow()

    override fun getPopularLimit(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getAllMovie(popular)
                    .map { DataMapper.mapEntitiesToDomain(it) }

            override suspend fun shouldFetch(data: List<Movie>?): Boolean {
                return when (val fetch = remoteDataSource.getPopularLimit().first()) {
                    is ApiResponse.Success -> {
                        if (data == null || data.isEmpty()) {
                            true
                        } else {
                            fetch.data[0].id != data[0].id
                        }
                    }
                    else -> false
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getPopularLimit()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val dataList = DataMapper.mapResponsesToEntities(data, popular)
                localDataSource.insertMovie(dataList, popular)
            }
        }.asFlow()

    override fun getPopular(): Flow<Resource<PagingData<Movie>>> =
        object : PagingBoundResource<PagingData<Movie>, PagingData<MovieResponse>>() {
            override suspend fun dataCallback(data: PagingData<MovieResponse>): PagingData<Movie> =
                data.map { DataMapper.mapResponseToDomain(it) }

            override fun dataCreate(): Flow<ApiResponse<PagingData<MovieResponse>>> {
                return remoteDataSource.getPopular()
            }
        }.asFlow()

    override fun getTopRatedLimit(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getAllMovie(top)
                    .map { DataMapper.mapEntitiesToDomain(it) }

            override suspend fun shouldFetch(data: List<Movie>?): Boolean {
                return when (val fetch = remoteDataSource.getTopRatedLimit().first()) {
                    is ApiResponse.Success -> {
                        if (data == null || data.isEmpty()) {
                            true
                        } else {
                            fetch.data[0].id != data[0].id
                        }
                    }
                    else -> false
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getTopRatedLimit()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val dataList = DataMapper.mapResponsesToEntities(data, top)
                localDataSource.insertMovie(dataList, top)
            }
        }.asFlow()

    override fun getTopRated(): Flow<Resource<PagingData<Movie>>> =
        object : PagingBoundResource<PagingData<Movie>, PagingData<MovieResponse>>() {
            override suspend fun dataCallback(data: PagingData<MovieResponse>): PagingData<Movie> =
                data.map { DataMapper.mapResponseToDomain(it) }

            override fun dataCreate(): Flow<ApiResponse<PagingData<MovieResponse>>> {
                return remoteDataSource.getTopRated()
            }
        }.asFlow()

    override fun getGenre(): Flow<Resource<List<Genres>>> =
        object : NetworkBoundResource<List<Genres>, List<GenresResponse>>() {
            override fun loadFromDB(): Flow<List<Genres>> =
                localDataSource.getAllGenres()
                    .map { GenreMapper.mapEntitiesToDomain(it) }

            override suspend fun shouldFetch(data: List<Genres>?): Boolean {
                return when (val fetch = remoteDataSource.getGenres().first()) {
                    is ApiResponse.Success -> {
                        if (data == null || data.isEmpty()) {
                            true
                        } else {
                            fetch.data[0].id != data[0].id
                        }
                    }
                    else -> false
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<GenresResponse>>> =
                remoteDataSource.getGenres()

            override suspend fun saveCallResult(data: List<GenresResponse>) {
                val dataList = GenreMapper.mapResponsesToEntities(data)
                localDataSource.insertGenre(dataList)
            }
        }.asFlow()

    override fun getDiscover(genreIds: Int): Flow<Resource<PagingData<Movie>>> =
        object : PagingBoundResource<PagingData<Movie>, PagingData<MovieResponse>>() {
            override suspend fun dataCallback(data: PagingData<MovieResponse>): PagingData<Movie> =
                data.map { DataMapper.mapResponseToDomain(it) }

            override fun dataCreate(): Flow<ApiResponse<PagingData<MovieResponse>>> {
                return remoteDataSource.getDiscover(genreIds)
            }
        }.asFlow()

    override fun getDetailMovie(id: Int): Flow<Resource<MovieDetail>> =
        object : NetworkBoundResource<MovieDetail, MovieDetailResponse>() {
            override fun loadFromDB(): Flow<MovieDetail> =
                localDataSource.getMovie(id).map { DetailMapper.mapEntitiesToDomain(it) }

            override suspend fun shouldFetch(data: MovieDetail?): Boolean = data?.id == 0

            override suspend fun createCall(): Flow<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getDetailMovie(id)

            override suspend fun saveCallResult(data: MovieDetailResponse) {
                val dataList = DetailMapper.mapResponsesToEntities(data)
                localDataSource.insertMovieDetail(dataList, id)
            }
        }.asFlow()

    override fun getRecommendations(id: Int): Flow<Resource<List<Movie>>> =
        object : PagingBoundResource<List<Movie>, List<MovieResponse>>() {
            override suspend fun dataCallback(data: List<MovieResponse>): List<Movie> =
                data.map { DataMapper.mapResponseToDomain(it) }

            override fun dataCreate(): Flow<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getRecommendations(id)
            }
        }.asFlow()

    override fun getTrailers(id: Int): Flow<Resource<List<Trailers>>> =
        object : PagingBoundResource<List<Trailers>, List<TrailersResponse>>() {
            override suspend fun dataCallback(data: List<TrailersResponse>): List<Trailers> =
                data.map { TrailersMapper.mapResponseToDomain(it) }

            override fun dataCreate(): Flow<ApiResponse<List<TrailersResponse>>> {
                return remoteDataSource.getTrailers(id)
            }
        }.asFlow()

    override fun getReviews(id: Int): Flow<Resource<PagingData<Reviews>>> =
        object : PagingBoundResource<PagingData<Reviews>, PagingData<ReviewsResponse>>() {
            override suspend fun dataCallback(data: PagingData<ReviewsResponse>): PagingData<Reviews> =
                data.map { ReviewsMapper.mapResponseToDomain(it) }

            override fun dataCreate(): Flow<ApiResponse<PagingData<ReviewsResponse>>> {
                return remoteDataSource.getReviews(id)
            }
        }.asFlow()

    override fun getFavoriteMovie(): Flow<List<Movie>> =
        localDataSource.getFavoriteMovie().map { DataMapper.mapEntitiesToDomain(it) }

    override fun setFavoriteMovie(data: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(data)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }

    override fun searchMovie(keyword: String): Flow<Resource<PagingData<Movie>>> =
        object : PagingBoundResource<PagingData<Movie>, PagingData<MovieResponse>>() {
            override suspend fun dataCallback(data: PagingData<MovieResponse>): PagingData<Movie> =
                data.map { DataMapper.mapResponseToDomain(it) }

            override fun dataCreate(): Flow<ApiResponse<PagingData<MovieResponse>>> {
                return remoteDataSource.searchMovie(keyword)
            }
        }.asFlow()
}
