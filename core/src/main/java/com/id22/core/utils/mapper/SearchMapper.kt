package com.id22.core.utils.mapper

import com.id22.core.data.source.local.entity.SearchEntity
import com.id22.core.data.source.remote.response.movie.MovieResponse
import com.id22.core.domain.model.Movie

object SearchMapper {
    fun mapResponsesToEntities(input: List<MovieResponse>): List<SearchEntity> {
        val dataList = ArrayList<SearchEntity>()

        input.map {
            dataList.add(
                SearchEntity(
                    id = it.id,
                    overview = it.overview,
                    posterPath = it.posterPath,
                    title = it.title,
                    isFavorite = false,
                ),
            )
        }

        return dataList
    }

    fun mapDomainToEntity(input: Movie) = SearchEntity(
        id = input.id,
        overview = input.overview,
        posterPath = input.posterPath,
        title = input.title,
        isFavorite = input.isFavorite,
    )
}
