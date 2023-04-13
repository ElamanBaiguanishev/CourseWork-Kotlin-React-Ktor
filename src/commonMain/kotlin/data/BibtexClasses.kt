package data

import kotlinx.serialization.Serializable

@Serializable
data class BibTex(
    val title: String? = null,
    val author: String? = null,
    val year: String? = null,
    val journal: String? = null,
    val volume: String? = null,
    val number: String? = null,
    val pages: String? = null,
)