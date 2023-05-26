package components.load

import csstype.*
import data.BibTex
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.thead
import react.dom.html.ReactHTML.tr
import react.useRef
import react.useState
import web.html.HTMLSelectElement
import web.html.InputType

external interface EditFileProps : Props {
    var fields: MutableMap<String?, String?>
    var query: (BibTex) -> Unit
}

val CEditInputFIle = FC<EditFileProps>("EditFile") { props ->
    var edit: Pair<String, String>? by useState()
    val map by useState(props.fields)
    val refSelect = useRef<HTMLSelectElement>()

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
        select {
            ref = refSelect
            BibTex.tags(map).forEach {
                option {
                    +it
                }
            }
        }
        button {
            +"Добавить"
            onClick = {
                map += refSelect.current?.value!! to ""
                edit = refSelect.current?.value!! to ""
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