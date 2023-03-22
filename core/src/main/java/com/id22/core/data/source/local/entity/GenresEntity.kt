package com.id22.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "genres")
data class GenresEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,
) : Parcelable
