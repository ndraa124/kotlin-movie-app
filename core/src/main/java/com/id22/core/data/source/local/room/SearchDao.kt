package com.id22.core.data.source.local.room

import androidx.room.*
import com.id22.core.data.source.local.entity.SearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query("SELECT * FROM search WHERE title LIKE '%' || :keyword || '%'")
    fun getAllSearch(keyword: String): Flow<List<SearchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchMovie(data: List<SearchEntity>)

    @Query("UPDATE search SET isFavorite = :newState WHERE id = :id")
    fun updateFavoriteMovie(id: Int, newState: Boolean)

    @Query("DELETE FROM search")
    suspend fun deleteSearchMovie()
}
