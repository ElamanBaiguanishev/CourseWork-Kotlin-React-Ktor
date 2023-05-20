package components.form

import components.find.ShowProps
import csstype.*
import csstype.BorderCollapse.Companion.collapse
import csstype.FontStyle.Companion.italic
import csstype.LineStyle.Companion.solid
import emotion.react.css
import react.FC
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr
import kotlin.js.Date


val CCreateBasicTable = FC<ShowProps>("Find") { props ->
    val listCheck = props.files.flatten()
    val currentDate = Date().toLocaleDateString()

    h1 { //Заголовок 1
        css {
            textAlign = TextAlign.Companion.center
        }
        +"Список"
    }
    h2 { // Заголовок 2
        css {
            textAlign = TextAlign.Companion.center
        }
        +"Опубликованных учебных изданий и научных трудов профессора ${props.professorName}"
    }
    table {
        css {
            textAlign = TextAlign.Companion.center
            width = 900.px
            borderCollapse = collapse
            borderWidth = 2.px
            borderStyle = solid
        }
        tr { //Создание строки таблицы
            // Шапка таблицы
            td { //Создание ячейки таблицы
                css {
                    borderWidth = 2.px
                    borderStyle = solid
                }
                +"№"
            }
            td {
                css {
                    borderWidth = 2.px
                    borderStyle = solid
                }
                +"Наименование учебных публикаций, научных работ и патентов на изобретения и другие объекты интеллектуальной собственности"
            }
            td {
                css {
                    borderWidth = 2.px
                    borderStyle = solid
                }
                +"Форма учебных изданий и научных трудов"
            }
            td {
                css {
                    borderWidth = 2.px
                    borderStyle = solid
                }
                +"Выходные данные"
            }
            td {
                css {
                    borderWidth = 2.px
                    borderStyle = solid
                }
                +"Объем"
            }
            td {
                css {
                    borderWidth = 2.px
                    borderStyle = solid
                }
                +"Соавторы"
            }
        }
        CTableRows {
            this.files = listCheck
            this.professorName = props.professorName
        }
    }
    table {

        css {
            width = 900.px
            margin = Auto.auto
        }
        css {
            width = 900.px
            margin = Auto.auto
        }
        tr {
            td {
                css {
                    textAlign = TextAlign.Companion.left
                }
                +"Соискатель"
            }
            td {
                css {
                    textAlign = TextAlign.Companion.right
                }
                +"______________${props.name}"
            }
        }
        tr {
            td {
                css {
                    textAlign = TextAlign.Companion.left
                }
                +"Зав. кафедрой ____________"
            }
            td {
                css {
                    textAlign = TextAlign.Companion.right
                }
                +"______________${props.zavName}"
            }
        }
        tr {
            td {
                css {
                    textAlign = TextAlign.Companion.left
                }
                +"Ученый секретарь ученого совета"
            }
            td {
                css {
                    textAlign = TextAlign.Companion.right
                }
                +"______________${props.secName}"
            }
        }
        tr {
            td {
                css {
                    textAlign = TextAlign.Companion.left
                }
                +currentDate
            }
        }
    }
}