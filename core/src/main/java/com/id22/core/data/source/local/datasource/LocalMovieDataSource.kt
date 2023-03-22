package com.id22.core.data.source.local.datasource

import com.id22.core.data.source.local.entity.GenresEntity
import com.id22.core.data.source.local.entity.MovieDetailEntity
import com.id22.core.data.source.local.entity.MovieEntity
import com.id22.core.data.source.local.room.GenresDao
import com.id22.core.data.source.local.room.MoviesDao
import com.id22.core.data.source.local.room.MoviesDetailDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class LocalMovieDataSource(
    private val dao: MoviesDao,
    private val daoDetail: MoviesDetailDao,
    private val daoGenres: GenresDao
) {
    fun getAllMovie(category: String): Flow<List<MovieEntity>> {
        return dao.getAllMovie(category) ?: emptyFlow()
    }

    fun getAllGenres(): Flow<List<GenresEntity>> {
        return daoGenres.getAllGenre() ?: emptyFlow()
    }

    fun getMovie(id: Int): Flow<MovieDetailEntity> {
        return daoDetail.getMovie(id) ?: emptyFlow()
    }

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = dao.getAllFavoriteMovie()

    suspend fun insertMovie(data: List<MovieEntity>, category: String) {
        dao.deleteMovie(category)
        dao.insertMovie(data)
    }

    suspend fun insertMovieDetail(data: MovieDetailEntity, id: Int) {
        daoDetail.deleteMovieDetail(id)
        daoDetail.insertMovieDetail(data)
    }

    suspend fun insertGenre(data: List<GenresEntity>) {
        daoGenres.deleteGenre()
        daoGenres.insertGenre(data)
    }

    fun setFavoriteMovie(data: MovieEntity, newState: Boolean) {
        dao.updateFavoriteMovie(data.id, newState)
    }
}
