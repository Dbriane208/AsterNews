package com.samkt.asternews.data.asterNewsRepository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import daniel.brian.asternews.data.asterNewsDtos.AllArticles
import retrofit2.HttpException
import java.io.IOException

const val ASTER_NEWS_STARTING_PAGE = 1

class AsterNewsPagingSource(
    private val service: AsterNewsApiService,
    private val keyword: String?,
) : PagingSource<Int, AllArticles>() {
    override fun getRefreshKey(state: PagingState<Int, AllArticles>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AllArticles> {
        val position = params.key ?: ASTER_NEWS_STARTING_PAGE

        return try {
            val response = service.getArticlesOnCategory(keyword = keyword)
            LoadResult.Page(
                data = listOf(response),
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (response.articles.results.isEmpty()) null else response.articles.page + 1,
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
