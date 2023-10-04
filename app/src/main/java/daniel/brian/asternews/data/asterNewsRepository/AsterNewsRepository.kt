package daniel.brian.asternews.data.asterNewsRepository

import daniel.brian.asternews.data.asterNewsDtos.AllArticles
import daniel.brian.asternews.utils.FetchedResponse
import daniel.brian.asternews.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface AsterNewsRepository {
    suspend fun getAllArticles(): Flow<FetchedResponse<AllArticles>>
    suspend fun getArticlesOnCategory(categoryName: String): Flow<FetchedResponse<AllArticles>>
}

class AsterNewsRepositoryImpl @Inject constructor(
    private val newsApi: AsterNewsApiService,
) : AsterNewsRepository {
    override suspend fun getAllArticles(): Flow<FetchedResponse<AllArticles>> = flowOf(
        safeApiCall(Dispatchers.IO) {
            newsApi.getAllArticles()
        },
    )

    override suspend fun getArticlesOnCategory(categoryName: String): Flow<FetchedResponse<AllArticles>> =
        flowOf(
            safeApiCall(Dispatchers.IO) {
                newsApi.getArticlesOnCategory(categoryName = categoryName)
            },
        )
}
