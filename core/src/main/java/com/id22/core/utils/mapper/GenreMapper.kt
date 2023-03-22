package com.id22.core.utils.mapper

import com.id22.core.data.source.local.entity.GenresEntity
import com.id22.core.data.source.remote.response.movie.GenresResponse
import com.id22.core.domain.model.Genres

object GenreMapper {
    fun mapResponsesToEntities(input: List<GenresResponse>): List<GenresEntity> {
        val dataList = ArrayList<GenresEntity>()

        input.map {
            dataList.add(
                GenresEntity(
                    id = it.id,
                    name = it.name
                ),
            )
        }

        return dataList
    }

    fun mapEntitiesToDomain(input: List<GenresEntity>): List<Genres> =
        input.map {
            Genres(
                id = it.id,
                name = it.name,
                isActive = false
            )
        }
}
