package com.id22.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.id22.core.domain.usecase.MoviesUseCase
import kotlinx.coroutines.Dispatchers

class FavoriteViewModel(useCase: MoviesUseCase) : ViewModel() {
    val getFavoriteMovie = useCase.getFavoriteMovie()
        .asLiveData(Dispatchers.Main)
}
