package com.id22.core.data.source.remote.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.id22.core.data.source.remote.network.service.ApiService
import com.id22.core.data.source.remote.response.movie.ReviewsResponse
import com.id22.core.utils.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class ReviewsPagingSource(
    private val apiService: ApiService,
    private val id: Int
) : PagingSource<Int, ReviewsResponse>() {
    override fun getRefreshKey(state: PagingState<Int, ReviewsResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewsResponse> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val res = apiService.getAllReviews(id, page)

            val totalPages = if (res.totalPages == 0) 1 else res.totalPages
            val prev = if (page == STARTING_PAGE_INDEX) null else page
            val next = if (page != totalPages) page.plus(1) else null
            val data = res.results ?: emptyList()

            LoadResult.Page(data, prev, next)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}