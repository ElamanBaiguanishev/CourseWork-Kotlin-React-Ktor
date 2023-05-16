package components

import config.Config
import data.BibTex
import js.core.get
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.router.useParams
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText


val CBibtex = FC<Props>("Test") {
    val id = useParams()["id"]

    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = arrayOf("currentBibtex").unsafeCast<QueryKey>(),
        queryFn = {
            fetchText(Config.documentsPath + id)
        }
    )

    if (query.isLoading) div { +"Loading .." }
    else if (query.isError) div { +"Error!" }
    else {
        val item =
            Json.decodeFromString<BibTex>(query.data ?: "")
        div {
            +item.map().toString()
        }
        CEditInputFIle{
            fields = item.map()
        }
    }
}