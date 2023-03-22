package com.id22.core.data.boundresource

import com.id22.core.data.Resource
import com.id22.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<Resource<ResultType>> =
        flow {
            emit(Resource.ShowLoading())

            if (shouldFetch(loadFromDB().first())) {
                when (val apiResponse = createCall().first()) {
                    is ApiResponse.Success -> {
                        emit(Resource.HideLoading())

                        saveCallResult(apiResponse.data)
                        emitAll(loadFromDB().map { Resource.Success(it) })
                    }
                    is ApiResponse.Empty -> {
                        emit(Resource.HideLoading())
                        emit(Resource.Empty())
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.HideLoading())
                        onFetchFailed()
                        emit(Resource.Error(apiResponse.errorMessage))
                    }
                }
            } else {
                emit(Resource.HideLoading())
                emitAll(loadFromDB().map { Resource.Success(it) })
            }
        }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract suspend fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}
