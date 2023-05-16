package components

import data.BibTex
import react.*
import react.dom.html.ReactHTML.details
import react.dom.html.ReactHTML.ol
import react.dom.html.ReactHTML.summary
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.tr

external interface ShowProps : Props {
    var files: MutableList<List<BibTex>>
}

val CShowInfo = FC<ShowProps>("Read") { props ->
    val names = listOf(
        "По автору:",
        "По названию:",
        "По году:",
        "Расширенный поиск:",
    )
    table {
        tr {
            th {
                +"Результаты поиска"
            }
        }
        props.files.forEachIndexed { index, list ->
            details {
                summary { +names[index] }
                tr {
                    td {
                        ol {
                            list.forEach { bib ->
                                CBibTexInList {
                                    this.item = bib
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}