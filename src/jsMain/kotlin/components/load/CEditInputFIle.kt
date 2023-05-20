package components.load

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
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr
import react.useState
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import web.html.InputType
import kotlin.js.json

external interface EditFileProps : Props {
    var fields: MutableMap<String?, String?>
    var query: (BibTex) -> Unit
}

val CEditInputFIle = FC<EditFileProps>("EditFile") { props ->
    var edit: Pair<String, String>? by useState()
    val map by useState(props.fields)
    if (edit != null) {
        div {
            span {
                +edit!!.first
                input {
                    type = InputType.text
                    value = edit!!.second
                    onChange = {
                        edit = Pair(edit!!.first,it.target.value)
                        map += edit!!
                    }
                }
            }
        }
    }
    table {
        css {
            border = Border(1.px, LineStyle.solid)
        }
        map.map { (key, value) ->
            tr {
                if (key != null && value != null) {
                    td {
                        css {
                            border = Border(1.px, LineStyle.solid)
                        }
                        +key
                    }
                    td {
                        css {
                            border = Border(1.px, LineStyle.solid)
                        }
                        +value
                    }
                    td {
                        +"Изменить"
                        onClick = {
                            edit = Pair(key, value)
                        }
                    }
                }
            }
        }
    }
    button {
        +"Сохранить"
        onClick = {
            props.query(
                BibTex(
                    _id = map["id"],
                    type = map["type"] ?: "",
                    tag = map["tag"] ?: "",
                    author = map["author"],
                    title = map["title"],
                    journal = map["journal"],
                    year = map["year"],
                    volume = map["volume"],
                    number = map["number"],
                    pages = map["pages"],
                    month = map["month"],
                    note = map["note"],
                    key = map["key"],
                    publisher = map["publisher"],
                    series = map["series"],
                    address = map["address"],
                    edition = map["edition"],
                    organization = map["organization"],
                )
            )
        }
    }
}