package components.load

import config.Config
import data.BibTex
import data.BibTexId
import js.core.get
import js.core.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.router.useParams
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import tools.fetchText
import kotlin.js.json


val CCurrentBibtex = FC<Props>("Test") {
    val id = useParams()["id"] as BibTexId
    val queryClient = useQueryClient()
    val myQueryKey = arrayOf("update").unsafeCast<QueryKey>()

    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = arrayOf("currentBibtex").unsafeCast<QueryKey>(),
        queryFn = {
            fetchText(Config.uploadPath + id)
        }
    )

    val updateQuery = useMutation<HTTPResult, Any, BibTex, Any>(
        mutationFn = { element: BibTex ->
            fetch(
                url = Config.uploadPath,
                jso {
                    method = "PUT"
                    headers = json("Content-Type" to "application/json")
                    body = Json.encodeToString(element)
                })
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(myQueryKey)
            }
        })

    if (query.isLoading) div { +"Loading .." }
    else if (query.isError) div { +"Error!" }
    else {
        val item =
            Json.decodeFromString<BibTex>(query.data ?: "")
        val bibTexMap = item.map()
        CEditInputFIle {
            fields = bibTexMap
            this.query = {
                updateQuery.mutateAsync(it, null)
            }
        }
    }
}