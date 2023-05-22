package components.load

import config.Config
import csstype.*
import data.BibTex
import emotion.react.css
import js.core.jso
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import react.FC
import react.Props
import react.dom.aria.ariaColSpan
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.thead
import react.dom.html.ReactHTML.tr
import react.useRef
import react.useState
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import web.html.HTMLInputElement
import web.html.InputType
import kotlin.js.json

external interface EditFileProps : Props {
    var fields: MutableMap<String?, String?>
    var query: (BibTex) -> Unit
}

val CEditInputFIle = FC<EditFileProps>("EditFile") { props ->
    var edit: Pair<String, String>? by useState()
    val map by useState(props.fields)

    div {
        className = ClassName("div-1")
        table {
            className = ClassName("table")
            thead {
                tr {
                    th {
                        +"field"
                    }
                    th {
                        +"value"
                    }
                    th {
                        +"edited"
                    }
                }
            }
            tbody {
                map.map { (key, value) ->
                    tr {
                        if (key != null && value != null) {
                            td {
                                +key
                            }
                            if (edit?.first == key) {
                                td {
                                    input {
                                        type = InputType.text
                                        this.value = edit!!.second
                                        onChange = {
                                            edit = Pair(edit!!.first, it.target.value)
                                        }
                                    }
                                }
                                td {
                                    +"Сохранить"
                                    onClick = {
                                        map += edit!!
                                        edit = null
                                    }
                                }
                            }
                            else {
                                td {
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
            }
        }
        button {
            +"Отправить"
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
}