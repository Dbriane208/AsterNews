package daniel.brian.asternews.data.asterNewsRepository

import daniel.brian.asternews.data.asterNewsDtos.AllArticles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface AsterNewsRepository {
    suspend fun getAllArticles(): Flow<AllArticles>
    suspend fun getArticlesOnCategory(keyword: String): Flow<AllArticles>
}

class AsterNewsRepositoryImpl @Inject constructor(
    private val newsApi: AsterNewsApiService,
) : AsterNewsRepository {
    override suspend fun getAllArticles(): Flow<AllArticles> = flow {
        val response = newsApi.getAllArticles()
        emit(response)
    }

    override suspend fun getArticlesOnCategory(keyword: String): Flow<AllArticles> = flow {
        val response = newsApi.getArticlesOnCategory(keyword = keyword)
        emit(response)
    }
}
