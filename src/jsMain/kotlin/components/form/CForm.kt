package components.form

import components.find.CFindCriterion
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr
import react.useRef
import react.useState
import tanstack.query.core.QueryKey
import web.html.HTMLInputElement


val CForm = FC<Props>("Find") {
    val professorName = useRef<HTMLInputElement>()
    val name = useRef<HTMLInputElement>()
    val zavName = useRef<HTMLInputElement>()
    val secName = useRef<HTMLInputElement>()
    var stub by useState("stop")

    val finds = listOf(
        "Введите имя интересующего вас профессора: " to professorName,
        "Введите ваше ФИО: " to name,
        "Введите ФИО заведующего кафедрой: " to zavName,
        "Введите ФИО Ученого секретаря ученого совета: " to secName,
    )

    table {
        finds.forEach { (name, reference) ->
            tr {
                td {
                    +name
                }
                td {
                    input {
                        ref = reference
                    }
                }
            }
        }
    }

    when (stub) {
        "stop" -> button {
            +"form"
            onClick = {
                stub = "start"
            }
        }

        "start" -> {
            CFindCriterion {
                this.component = CCreateBasicTable
                this.keys = listOf(
                    "findAuthor" to arrayOf("author").unsafeCast<QueryKey>()
                )
                this.criterions =
                    listOf(
                        professorName.current?.value!!,
                        name.current?.value!!,
                        zavName.current?.value!!,
                        secName.current?.value!!
                    )
            }
            button {
                +"clear"
                onClick = {
                    stub = "stop"
                }
            }
        }
    }
}