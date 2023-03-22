package com.id22.core.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName

data class ReviewsResponse(
    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("author_details")
    val authorDetails: AuthorDetailsResponse,

    @field:SerializedName("content")
    val content: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("updated_at")
    val updatedAat: String,

    @field:SerializedName("url")
    val url: String,
)