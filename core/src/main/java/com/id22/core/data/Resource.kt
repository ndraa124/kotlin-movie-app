package com.id22.core.data

abstract class Resource<T>(val data: T? = null, val message: String? = null) {
    class ShowLoading<T>(data: T? = null) : Resource<T>(data)

    class HideLoading<T>(data: T? = null) : Resource<T>(data)

    class Success<T>(data: T? = null, message: String? = null) : Resource<T>(data, message)

    class Empty<T> : Resource<T>(null)

    class Error<T>(message: String) : Resource<T>(null, message)
}
