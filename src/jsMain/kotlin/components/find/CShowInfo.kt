package components.find

import components.load.CEditInputFIle
import config.Config
import data.BibTex
import js.core.jso
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import react.*
import react.dom.html.ReactHTML.details
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.ol
import react.dom.html.ReactHTML.summary
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.tr
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import kotlin.js.json

external interface ShowProps : Props {
    var files: MutableList<List<BibTex>>
    var professorName: String
    var name: String
    var zavName: String
    var secName: String
}

val CShowInfo = FC<ShowProps>("Read") { props ->
    val names = listOf(
        "По автору:",
        "По названию:",
        "По году:",
        "Расширенный поиск:",
    )

    val queryClient = useQueryClient()
    val myQueryKey = arrayOf("updates").unsafeCast<QueryKey>()

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
        }
    )

    h1 {
        +"Результаты поиска"
    }
    props.files.forEachIndexed { index, list ->
        details {
            summary { +names[index] }
//            ol {
            list.forEach { bib ->
                CEditInputFIle {
                    fields = bib.map()
                    this.query = {
                        updateQuery.mutateAsync(it, null)
                    }
                }
//                }
            }
        }
    }
}