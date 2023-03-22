package com.id22.core.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,
)