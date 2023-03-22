package com.id22.core.data.boundresource

import com.id22.core.data.Resource
import com.id22.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class PagingBoundResource<ResultType, RequestType> {
    private var result: Flow<Resource<ResultType>> =
        flow {
            emit(Resource.ShowLoading())

            when (val apiResponse = dataCreate().first()) {
                is ApiResponse.Success -> {
                    emit(Resource.HideLoading())
                    emit(Resource.Success(dataCallback(apiResponse.data)))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.HideLoading())
                    emit(Resource.Empty())
                }
                is ApiResponse.Error -> {
                    emit(Resource.HideLoading())
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }
        }

    protected abstract suspend fun dataCallback(data: RequestType): ResultType

    protected abstract fun dataCreate(): Flow<ApiResponse<RequestType>>

    fun asFlow(): Flow<Resource<ResultType>> = result
}
