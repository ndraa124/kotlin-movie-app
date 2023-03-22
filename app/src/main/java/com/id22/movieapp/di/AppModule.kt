package com.id22.movieapp.di

import com.id22.core.domain.usecase.MoviesInteractor
import com.id22.core.domain.usecase.MoviesUseCase
import com.id22.movieapp.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MoviesUseCase> { MoviesInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
}
