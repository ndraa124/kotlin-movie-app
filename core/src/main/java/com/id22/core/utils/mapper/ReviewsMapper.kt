package com.id22.core.utils.mapper

import com.id22.core.data.source.remote.response.movie.ReviewsResponse
import com.id22.core.domain.model.Reviews

object ReviewsMapper {
    fun mapResponseToDomain(input: ReviewsResponse): Reviews =
        Reviews(
            id = input.id,
            author = input.author,
            content = input.content,
            createdAt = input.createdAt,
            rating = input.authorDetails.rating,
            avatar = input.authorDetails.avatarPath
        )
}
