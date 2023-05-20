package components.form

import csstype.*
import csstype.BorderCollapse.Companion.collapse
import csstype.FontStyle.Companion.italic
import csstype.LineStyle.Companion.solid
import data.BibTex
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
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
            css {
                borderWidth = 2.px
                borderStyle = solid
            }
            td { //Номер строки
                css {
                    borderWidth = 2.px
                    borderStyle = solid
                }
                +"$count."
            }
            td { //Наименование учебных работ
                css {
                    borderWidth = 2.px
                    borderStyle = solid
                }
                css {
                    textAlign = TextAlign.Companion.left
                    width = 900.px
                    borderCollapse = collapse
                    borderWidth = 2.px
                    borderStyle = solid
                }
                +it.title!!
                br
                div {
                    css {
                        fontStyle = italic
                    }
                    +"(${it.type}"
                }
            }

            td { //Форма в которой представлены труды
                css {
                    borderWidth = 2.px
                    borderStyle = solid
                }
                +"Печатная"
            }
            td {// Выходные данные
                css {
                    borderWidth = 2.px
                    borderStyle = solid
                }
                css {
                    textAlign = TextAlign.Companion.left
                }
                +"${it.publisher ?: " "} ${it.journal ?: " "} – ${it.year ?: " "} № ${it.number ?: " "} C. ${it.pages ?: " "}."
            }

            td {
                css {
                    borderWidth = 2.px
                    borderStyle = solid
                }
                +"${it.volume}СТР."
            }
            td {
                css {
                    borderWidth = 2.px
                    borderStyle = solid
                }
                css {
                    textAlign = TextAlign.Companion.left
                }
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