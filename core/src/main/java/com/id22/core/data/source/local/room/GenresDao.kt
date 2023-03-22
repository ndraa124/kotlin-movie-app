package com.id22.core.data.source.local.room

import androidx.room.*
import com.id22.core.data.source.local.entity.GenresEntity
import com.id22.core.data.source.local.entity.MovieDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresDao {
    @Query("SELECT * FROM genres")
    fun getAllGenre(): Flow<List<GenresEntity>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(data: List<GenresEntity>)

    @Query("DELETE FROM genres")
    suspend fun deleteGenre()
}
