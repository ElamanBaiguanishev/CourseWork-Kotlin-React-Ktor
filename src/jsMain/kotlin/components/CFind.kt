package components

import config.Config
import data.BibTex
import kotlinx.serialization.decodeFromString
import react.FC
import react.Props
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ol
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText
import kotlinx.serialization.json.Json
import query.QueryError

external interface FindProps: Props {
    var criterion: String
}

val CFindCriterion = FC<FindProps>("Find") { props ->
    val selectQueryKey = arrayOf("criterion").unsafeCast<QueryKey>()
    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = selectQueryKey,
        queryFn = {
            fetchText("${Config.readPath}find:${props.criterion}")
        }
    )
    if (query.isLoading) {
        +"Loading..."
    } else if (query.isError) {
        +"Error"
    } else {
        val files =
            Json.decodeFromString<List<BibTex>>(query.data ?: "")
        ol {
            for (i in files) {
                li {
                    +i.toString()
                }
            }
        }
    }
}