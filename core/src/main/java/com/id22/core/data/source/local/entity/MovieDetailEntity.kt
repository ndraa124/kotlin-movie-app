package com.id22.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie_detail")
data class MovieDetailEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idDetail")
    val idDetail: Int = 0,

    @ColumnInfo(name = "adult")
    val adult: Boolean,

    @ColumnInfo(name = "backdropPath")
    val backdropPath: String?,

    @ColumnInfo(name = "genres")
    val genres: List<GenresEntity>,

    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "originalTitle")
    val originalTitle: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "posterPath")
    val posterPath: String?,

    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,

    @ColumnInfo(name = "runtime")
    val runtime: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "video")
    val video: Boolean,

    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double,

    @ColumnInfo(name = "voteCount")
    val voteCount: Int,
) : Parcelable
