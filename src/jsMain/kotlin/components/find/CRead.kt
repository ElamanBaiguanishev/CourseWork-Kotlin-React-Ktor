package components.find

import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.details
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.summary
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr
import tanstack.query.core.QueryKey
import web.html.HTMLInputElement

val CReadCriterion = FC<Props>("Read") {
    var stub by useState("stop")
    val findAuthor = useRef<HTMLInputElement>()
    val findYear = useRef<HTMLInputElement>()
    val findTitle = useRef<HTMLInputElement>()
    val findAny = useRef<HTMLInputElement>()

    val finds = listOf(
        "Поиск по автору" to findAuthor,
        "Поиск по названию" to findTitle,
        "Поиск по году" to findYear,
        )

    table {
        finds.forEach { (name, reference)->
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
    details {
        summary { +"Расшренный поиск" }
        input {
            ref = findAny
        }
    }

    when(stub) {
        "stop" -> button {
            +"find"
            onClick = {
                stub = "start"
            }
        }

        "start" -> {
            CFindCriterion {
                this.component = CShowInfo
                this.keys = listOf(
                    "findAuthor" to arrayOf("author").unsafeCast<QueryKey>(),
                    "findTitle" to arrayOf("title").unsafeCast<QueryKey>(),
                    "findYear" to arrayOf("year").unsafeCast<QueryKey>(),
                    "findAny" to arrayOf("any").unsafeCast<QueryKey>()
                )
                this.criterions =
                    listOf(
                        findAuthor.current?.value!!,
                        findTitle.current?.value!!,
                        findYear.current?.value!!,
                        findAny.current?.value!!,
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