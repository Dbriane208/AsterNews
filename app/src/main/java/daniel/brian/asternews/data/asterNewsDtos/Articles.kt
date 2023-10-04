package daniel.brian.asternews.data.asterNewsDtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Articles(
    val page: Int,
    val pages: Int,
    val results: List<Result>,
    val totalResults: Int,
    @SerialName("count") val articleCount: Int
)
