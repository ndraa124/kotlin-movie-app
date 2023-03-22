package com.id22.core.utils.mapper

import com.id22.core.data.source.remote.response.movie.TrailersResponse
import com.id22.core.domain.model.Trailers

object TrailersMapper {
    fun mapResponseToDomain(input: TrailersResponse): Trailers =
        Trailers(
            id = input.id,
            name = input.name,
            key = input.key,
            publishedAt = input.published_at
        )
}
