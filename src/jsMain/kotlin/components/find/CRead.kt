package components.find

import components.form.CCreateBasicTable
import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.details
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.summary
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr
import tanstack.query.core.QueryKey
import web.html.HTMLInputElement

external interface ReadProps : Props {
    var form: Boolean
}

val CReadCriterion = FC<ReadProps>("Read") { props ->
    var stub by useState("stop")
    val inputOne = useRef<HTMLInputElement>()
    val inputTwo = useRef<HTMLInputElement>()
    val inputThree = useRef<HTMLInputElement>()
    val inputFour = useRef<HTMLInputElement>()

    val finds =
        if (props.form)
            listOf(
                "Введите имя интересующего вас профессора: " to inputOne,
                "Введите ваше ФИО: " to inputTwo,
                "Введите ФИО заведующего кафедрой: " to inputThree,
                "Введите ФИО Ученого секретаря ученого совета: " to inputFour,
            )
        else
            listOf(
                "Поиск по автору" to inputOne,
                "Поиск по названию" to inputThree,
                "Поиск по году" to inputTwo,
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
    if (!props.form){
        details {
            summary { +"Расшренный поиск" }
            input {
                ref = inputFour
            }
        }
    }

    when (stub) {
        "stop" -> button {
            +"find"
            onClick = {
                stub = "start"
            }
        }

        "start" -> {
            CFindCriterion {
                this.component = if (props.form) CCreateBasicTable else CShowInfo
                this.keys = listOf(
                    "findAuthor" to arrayOf("author").unsafeCast<QueryKey>(),
                    "findTitle" to arrayOf("title").unsafeCast<QueryKey>(),
                    "findYear" to arrayOf("year").unsafeCast<QueryKey>(),
                    "findAny" to arrayOf("any").unsafeCast<QueryKey>()
                )
                this.criterions =
                    listOf(
                        inputOne.current?.value!!,
                        inputThree.current?.value!!,
                        inputTwo.current?.value!!,
                        inputFour.current?.value!!,
                    )
            }
            div {
                button {
                    +"clear"
                    onClick = {
                        inputOne.current?.value = ""
                        inputThree.current?.value = ""
                        inputTwo.current?.value = ""
                        inputFour.current?.value = ""
                        stub = "stop"
                    }
                }
            }
        }
    }
}