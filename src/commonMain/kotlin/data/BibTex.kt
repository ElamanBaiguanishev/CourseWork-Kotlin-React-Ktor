package data

import kotlinx.serialization.Serializable

typealias BibTexId = String

@Serializable
data class BibTex(
    val _id: BibTexId? = null,
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
    fun createId(newId: BibTexId) =
        BibTex(
            newId,
            type,
            tag,
            author,
            title,
            journal,
            year,
            volume,
            number,
            pages,
            month,
            note,
            key,
            publisher,
            series,
            address,
            edition,
            organization
        )

    fun map(): MutableMap<String?, String?> {
        return mutableMapOf(
            "id" to _id,
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

    companion object {
        fun tags(map: MutableMap<String?, String?>): MutableList<String> {
            val allKeys = mutableListOf(
                "type", "tag", "author", "title", "journal", "year",
                "volume", "number", "pages", "month", "note", "key",
                "publisher","series", "address", "edition", "organization"
            )
            val currentKeys = map.keys
            allKeys.removeAll(currentKeys)
            if (allKeys.isEmpty()) {
                val a = map.mapNotNull {
                    if (it.value == null){
                        it.key
                    }
                    else {
                        null
                    }
                }
                return a.toMutableList()
            }
            return allKeys
        }
    }
}