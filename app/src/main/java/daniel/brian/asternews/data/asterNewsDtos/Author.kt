package daniel.brian.asternews.data.asterNewsDtos

import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val isAgency: Boolean,
    val name: String,
    val type: String,
    val uri: String,
)
