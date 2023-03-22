package com.id22.core.utils.mapper

import com.id22.core.data.source.local.entity.MovieDetailEntity
import com.id22.core.data.source.remote.response.movie.MovieDetailResponse
import com.id22.core.domain.model.MovieDetail

object DetailMapper {
    fun mapResponsesToEntities(input: MovieDetailResponse): MovieDetailEntity =
        MovieDetailEntity(
            adult = input.adult,
            backdropPath = input.backdropPath,
            genres = GenreMapper.mapResponsesToEntities(input.genres),
            id = input.id,
            originalTitle = input.originalTitle,
            overview = input.overview,
            posterPath = input.posterPath,
            releaseDate = input.releaseDate,
            runtime = input.runtime,
            title = input.title,
            video = input.video,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount,
        )

    fun mapEntitiesToDomain(input: MovieDetailEntity?): MovieDetail {
        return if (input != null) {
            MovieDetail(
                adult = input.adult,
                backdropPath = input.backdropPath,
                genres = GenreMapper.mapEntitiesToDomain(input.genres),
                id = input.id,
                originalTitle = input.originalTitle,
                overview = input.overview,
                posterPath = input.posterPath,
                releaseDate = input.releaseDate,
                runtime = input.runtime,
                title = input.title,
                video = input.video,
                voteAverage = input.voteAverage,
                voteCount = input.voteCount,
            )
        } else {
            MovieDetail()
        }
    }
}
