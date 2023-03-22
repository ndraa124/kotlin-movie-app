package com.id22.core.di

import androidx.room.Room
import com.id22.core.data.repository.MovieRepository
import com.id22.core.data.source.local.datasource.LocalMovieDataSource
import com.id22.core.data.source.local.room.AppDatabase
import com.id22.core.data.source.remote.datasource.RemoteMovieDataSource
import com.id22.core.data.source.remote.network.config.ApiConfig
import com.id22.core.domain.repository.IMoviesRepository
import com.id22.core.utils.AppExecutors
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory { get<AppDatabase>().moviesDao() }
    factory { get<AppDatabase>().moviesDetailDao() }
    factory { get<AppDatabase>().genreDao() }
    factory { get<AppDatabase>().searchDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            AppDatabase.DB_NAME,
        ).fallbackToDestructiveMigration()
            .build()
    }
}

val networkModule = module {
    single { ApiConfig.chuckerInterceptor(androidContext()) }
    single { ApiConfig.provideOkHttpClient(get()) }
    single { ApiConfig.getApiClient(get()) }
}

val datasourceLocalModule = module {
    single { LocalMovieDataSource(get(), get(), get()) }
}

val datasourceRemoteModule = module {
    single { RemoteMovieDataSource(get()) }
}

val repositoryModule = module {
    factory { AppExecutors() }
    single<IMoviesRepository> { MovieRepository(get(), get(), get()) }
}
