package daniel.brian.asternews.data.asterNewsDtos

import kotlinx.serialization.Serializable

@Serializable
data class AllArticles(
    val articles: Articles,
)
