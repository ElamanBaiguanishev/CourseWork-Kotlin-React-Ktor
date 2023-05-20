package components.load

import data.BibTex
import react.*
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr

external interface BibtexInListProps : Props {
    var item: BibTex
}

val  CBibTexInList = FC<BibtexInListProps>("Read") { props ->
    li {
        props.item.map().forEach {(name, prop) ->
            table {
                tr {
                    td {
                        +"$name: "
                    }
                    td {
                        +prop.toString()
                    }
                }
            }
        }
    }
}