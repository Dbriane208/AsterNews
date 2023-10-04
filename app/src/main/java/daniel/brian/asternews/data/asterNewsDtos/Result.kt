package daniel.brian.asternews.data.asterNewsDtos

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val authors: List<Author>,
    val body: String,
    val dataType: String,
    val date: String,
    val dateTime: String,
    val dateTimePub: String,
    val eventUri: String?,
    val image: String?,
    val isDuplicate: Boolean,
    val lang: String = "eng",
    val relevance: Int,
    val sentiment: Double?,
    val sim: Double,
    val source: Source,
    val time: String,
    val title: String,
    val uri: String,
    val url: String,
    val wgt: Int,
)
