package com.id22.core.data.source.local.room

import androidx.room.*
import com.id22.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movie WHERE category = :category")
    fun getAllMovie(category: String): Flow<List<MovieEntity>>?

    @Query("SELECT * FROM movie where isFavorite = 1 GROUP BY id")
    fun getAllFavoriteMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(data: List<MovieEntity>)

    @Query("DELETE FROM movie WHERE category = :category")
    suspend fun deleteMovie(category: String)

    @Update
    fun updateFavoriteMovie(data: MovieEntity)

    @Query("UPDATE movie SET isFavorite = :newState WHERE id = :id")
    fun updateFavoriteMovie(id: Int, newState: Boolean)
}
