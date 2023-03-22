package com.id22.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val idMovie: Int,
    val id: Int,
    val overview: String,
    val posterPath: String?,
    val title: String,
    val rating: Double,
    val isFavorite: Boolean,
    val category: String,
) : Parcelable
