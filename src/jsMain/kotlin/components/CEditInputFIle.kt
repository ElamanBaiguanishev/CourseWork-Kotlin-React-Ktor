package components

import config.Config
import csstype.Border
import csstype.LineStyle
import csstype.px
import data.BibTex
import emotion.react.css
import js.core.jso
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import kotlin.js.json

external interface EditFileProps : Props {
    var fields: MutableMap<String?, String?>
}

val CEditInputFIle = FC<EditFileProps>("EditFile") { props ->
    val queryClient = useQueryClient()
    val myQueryKey = arrayOf("update").unsafeCast<QueryKey>()
    val addMutation = useMutation<HTTPResult, Any, BibTex, Any>(
        mutationFn = { element: BibTex ->
            fetch(
                url = Config.uploadPath,
                jso {
                    method = "POST"
                    headers = json("Content-Type" to "application/json")
                    body = Json.encodeToString(element)
                })
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(myQueryKey)
            }
        })

    table {
        css {
            border = Border(1.px, LineStyle.solid)
        }
        props.fields.map {
            tr {
                td {
                    css {
                        border = Border(1.px, LineStyle.solid)
                    }
                    +(it.key ?: "")
                }
                td {
                    css {
                        border = Border(1.px, LineStyle.solid)
                    }
                    td {
                        +(it.value ?: "")
                    }
                }
            }
        }

    }
    button {
        +"Загрузить"
        onClick = {
            addMutation.mutate(
                BibTex(
                    _id = props.fields["id"]!!,
                    type = props.fields["type"]!!,
                    tag = props.fields["tag"]!!,
                    author = props.fields["author"],
                    title = props.fields["title"],
                    journal = props.fields["journal"],
                    year = props.fields["year"],
                    volume = props.fields["volume"],
                    number = props.fields["number"],
                    pages = props.fields["pages"],
                    month = props.fields["month"],
                    note = props.fields["note"],
                    key = props.fields["key"],
                    publisher = props.fields["publisher"],
                    series = props.fields["series"],
                    address = props.fields["address"],
                    edition = props.fields["edition"],
                    organization = props.fields["organization"],
                ),
                null
            )
        }
    }
}