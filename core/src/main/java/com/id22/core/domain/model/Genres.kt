package com.id22.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genres(
    val id: Int,
    val name: String,
    var isActive: Boolean,
) : Parcelable