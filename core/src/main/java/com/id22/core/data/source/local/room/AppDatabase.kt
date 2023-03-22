package com.id22.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.id22.core.data.source.local.entity.GenresEntity
import com.id22.core.data.source.local.entity.MovieDetailEntity
import com.id22.core.data.source.local.entity.MovieEntity
import com.id22.core.data.source.local.entity.SearchEntity
import com.id22.core.utils.converter.GenresTypeConverter
import com.id22.core.utils.converter.IntTypeConverter

@Database(
    entities = [
        MovieEntity::class,
        MovieDetailEntity::class,
        GenresEntity::class,
        SearchEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(IntTypeConverter::class, GenresTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "movies"
    }

    abstract fun moviesDao(): MoviesDao

    abstract fun moviesDetailDao(): MoviesDetailDao

    abstract fun searchDao(): SearchDao

    abstract fun genreDao(): GenresDao
}
