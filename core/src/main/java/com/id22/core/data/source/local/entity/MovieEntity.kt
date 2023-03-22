package com.id22.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idMovie")
    val idMovie: Int = 0,

    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "posterPath")
    val posterPath: String?,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "rating")
    var rating: Double,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "category")
    var category: String,
) : Parcelable
