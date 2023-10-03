package daniel.brian.asternews.data.asterNewsDtos

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val dataType: String,
    val title: String,
    val uri: String,
)
