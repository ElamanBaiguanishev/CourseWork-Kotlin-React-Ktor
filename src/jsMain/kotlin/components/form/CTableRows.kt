package components.form

import data.BibTex
import react.FC
import react.Props
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr

external interface RowsProps : Props {
    var files: List<BibTex>
    var professorName: String
}

val CTableRows = FC<RowsProps>("Find") { props ->
    val listCheck = props.files
    var count = 0
    var splitAuthors: List<String>
    val splitRegex = "( and )|, ".toRegex()
    listCheck.forEach {
        count++
        tr {
            td {
                +"$count."
            }
            td {
                +it.title!!
            }

            td {
                +"Печатная"
            }
            td {
                +"${it.publisher ?: " "} ${it.journal ?: " "} – ${it.year ?: " "} № ${it.number ?: " "} C. ${it.pages ?: " "}."
            }

            td {
                +"${it.volume}СТР."
            }
            td {
                splitAuthors = it.author!!.split(splitRegex)
                splitAuthors.forEachIndexed { index, author ->
                    if (index != splitAuthors.lastIndex)
                        +author
                    else +"$author."
                }
            }
        }
    }
}