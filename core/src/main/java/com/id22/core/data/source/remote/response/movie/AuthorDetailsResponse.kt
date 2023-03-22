package com.id22.core.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName

data class AuthorDetailsResponse(
    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("avatar_path")
    val avatarPath: String?,

    @field:SerializedName("rating")
    val rating: Double,
)