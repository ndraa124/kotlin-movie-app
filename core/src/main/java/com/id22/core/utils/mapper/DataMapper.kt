package com.id22.core.utils.mapper

import com.id22.core.data.source.local.entity.MovieEntity
import com.id22.core.data.source.remote.response.movie.MovieResponse
import com.id22.core.domain.model.Movie

object DataMapper {
    fun mapResponsesToEntities(input: List<MovieResponse>, category: String): List<MovieEntity> {
        val dataList = ArrayList<MovieEntity>()

        input.map {
            dataList.add(
                MovieEntity(
                    id = it.id,
                    overview = it.overview,
                    posterPath = it.posterPath,
                    title = it.title,
                    rating = it.voteAverage,
                    isFavorite = false,
                    category = category,
                ),
            )
        }

        return dataList
    }

    fun mapResponseToDomain(input: MovieResponse): Movie =
        Movie(
            idMovie = input.id,
            id = input.id,
            overview = input.overview,
            posterPath = input.posterPath,
            title = input.title,
            rating = input.voteAverage,
            isFavorite = false,
            category = ""
        )

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                idMovie = it.idMovie,
                id = it.id,
                overview = it.overview,
                posterPath = it.posterPath,
                title = it.title,
                rating = it.rating,
                isFavorite = it.isFavorite,
                category = it.category
            )
        }

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        idMovie = input.idMovie,
        id = input.id,
        overview = input.overview,
        posterPath = input.posterPath,
        title = input.title,
        rating = input.rating,
        isFavorite = input.isFavorite,
        category = input.category,
    )
}
