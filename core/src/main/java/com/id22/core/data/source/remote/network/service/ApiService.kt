package com.id22.core.data.source.remote.network.service

import com.id22.core.data.source.remote.response.ListGenresResponse
import com.id22.core.data.source.remote.response.ListResponse
import com.id22.core.data.source.remote.response.movie.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getAllNowPlayingMovie(
        @Query("page") page: Int? = null
    ): ListResponse<MovieResponse>

    @GET("movie/popular")
    suspend fun getAllPopularMovie(
        @Query("page") page: Int? = null
    ): ListResponse<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getAllTopRatedMovie(
        @Query("page") page: Int? = null
    ): ListResponse<MovieResponse>

    @GET("movie/{id}/recommendations")
    suspend fun getAllRecommendations(
        @Path("id") id: Int,
        @Query("page") page: Int? = null
    ): ListResponse<MovieResponse>

    @GET("movie/{id}")
    suspend fun getDetailMovie(
        @Path("id") id: Int
    ): MovieDetailResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") keyword: String? = null,
        @Query("page") page: Int? = null
    ): ListResponse<MovieResponse>

    @GET("genre/movie/list")
    suspend fun getAllGenres(): ListGenresResponse<GenresResponse>

    @GET("discover/movie")
    suspend fun getMoviesByDiscover(
        @Query("with_genres") genreIds: Int,
        @Query("page") page: Int? = null
    ): ListResponse<MovieResponse>

    @GET("movie/{movieId}/reviews")
    suspend fun getAllReviews(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int? = null
    ): ListResponse<ReviewsResponse>

    @GET("movie/{movieId}/videos")
    suspend fun getAllVideos(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int? = null
    ): ListResponse<TrailersResponse>
}
