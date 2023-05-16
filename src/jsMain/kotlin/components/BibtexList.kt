package components

import config.Config
import data.BibTex
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ol
import react.router.dom.Link
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText

val CBibtexList = FC<Props>("Test") {
    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = arrayOf("studentList").unsafeCast<QueryKey>(),
        queryFn = {
            fetchText(Config.documentsPath)
        }
    )

    if (query.isLoading) ReactHTML.div { +"Loading .." }
    else if (query.isError) ReactHTML.div { +"Error!" }
    else {
        val items =
            Json.decodeFromString<List<BibTex>>(query.data ?: "")
        ol {
            for (i in items) {
                li {
                    Link {
                        +i._id
                        to = i._id
                    }
                }
            }
        }
    }
}