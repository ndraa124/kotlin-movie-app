package com.id22.core.data.source.local.room

import androidx.room.*
import com.id22.core.data.source.local.entity.MovieDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDetailDao {
    @Query("SELECT * FROM movie_detail WHERE id = :id")
    fun getMovie(id: Int): Flow<MovieDetailEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(data: MovieDetailEntity)

    @Query("DELETE FROM movie_detail WHERE id = :id")
    suspend fun deleteMovieDetail(id: Int)
}
