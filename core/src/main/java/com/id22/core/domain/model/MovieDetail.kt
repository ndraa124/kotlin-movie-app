package com.id22.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail(
    val adult: Boolean,
    val backdropPath: String?,
    val genres: List<Genres>?,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val runtime: Int,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
) : Parcelable {
    constructor() : this(false, "", null, 0, "", "", "", "", 0, "", false, 0.0, 0)
}
