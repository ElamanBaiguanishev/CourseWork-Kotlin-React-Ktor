package data

import kotlinx.serialization.Serializable

@Serializable
data class BibTex(
    val type: String,
    val tag: String,
    val author: String? = null,
    val title: String? = null,
    val journal: String? = null,
    val year: String? = null,
    val volume: String? = null,
    val number: String? = null,
    val pages: String? = null,
    val month: String? = null,
    val note: String? = null,
    val key: String? = null,
    val publisher: String? = null,
    val series: String? = null,
    val address: String? = null,
    val edition: String? = null,
    val organization: String? = null
) {
    fun map(): MutableMap<String?, String?> {
        return mutableMapOf(
            "type" to type,
            "tag" to tag,
            "author" to author,
            "title" to title,
            "journal" to journal,
            "year" to year,
            "volume" to volume,
            "number" to number,
            "pages" to pages,
            "month" to month,
            "note" to note,
            "key" to key,
            "publisher" to publisher,
            "series" to series,
            "address" to address,
            "edition" to edition,
            "organization" to organization,
        )
    }
}