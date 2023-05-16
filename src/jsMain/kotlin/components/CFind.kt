package components

import config.Config
import data.BibTex
import kotlinx.serialization.decodeFromString
import react.FC
import react.Props
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText
import kotlinx.serialization.json.Json
import query.QueryError
import tanstack.react.query.UseBaseQueryResult

external interface FindProps: Props {
    var criterions: List<String>
}

val CFindAnyCriterion = FC<FindProps>("Find") { props ->
    val keys = listOf(
        "findAuthor" to arrayOf("author").unsafeCast<QueryKey>(),
        "findTitle" to arrayOf("title").unsafeCast<QueryKey>(),
        "findYear" to arrayOf("year").unsafeCast<QueryKey>(),
        "findAny" to arrayOf("any").unsafeCast<QueryKey>()
        )

    val queries = mutableListOf<UseBaseQueryResult<String, QueryError>>()
    keys.forEachIndexed { index, (param, key)->
        queries.add(
            useQuery<String, QueryError, String, QueryKey>(
                queryKey = key,
                queryFn = {
                    fetchText("${Config.readPath}$param:${props.criterions[index]}")
                }
            )
        )
    }
    val files: MutableList<List<BibTex>> = mutableListOf()
    for (query in queries) {
        if (query.isLoading) {
            +" "
        } else if (query.isError) {
            +"Error"
        } else {
            files.add(Json.decodeFromString<List<BibTex>>(query.data ?: ""))
        }
    }
    CShowInfo {
        this.files = files
    }
}