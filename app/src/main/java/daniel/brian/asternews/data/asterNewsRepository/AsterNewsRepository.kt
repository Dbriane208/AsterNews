package daniel.brian.asternews.data.asterNewsRepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import daniel.brian.asternews.data.asterNewsDtos.AllArticles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface AsterNewsRepository {
    suspend fun getAllArticles(): Flow<PagingData<AllArticles>>
    suspend fun getArticlesOnCategory(keyword: String): Flow<PagingData<AllArticles>>
}

class AsterNewsRepositoryImpl @Inject constructor(
    private val newsApi: AsterNewsApiService,
) : AsterNewsRepository {
    override suspend fun getAllArticles(): Flow<PagingData<AllArticles>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
            ),
            pagingSourceFactory = {
                AsterNewsPagingSource(newsApi, null)
            },
        ).flow
    }

    override suspend fun getArticlesOnCategory(keyword: String): Flow<PagingData<AllArticles>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
            ),
            pagingSourceFactory = {
                AsterNewsPagingSource(newsApi, keyword)
            },
        ).flow
    }
}
