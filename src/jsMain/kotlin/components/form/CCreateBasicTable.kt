package components.form

import components.find.ShowProps
import csstype.*
import react.FC
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr
import kotlin.js.Date


val CCreateBasicTable = FC<ShowProps>("Find") { props ->
    val listCheck = props.files.flatten()
    val currentDate = Date().toLocaleDateString()

    h1 {
        className = ClassName("header")
        +"Список"
    }
    h2 {
        className = ClassName("header")
        +"Опубликованных учебных изданий и научных трудов профессора ${props.professorName}"
    }
    table {
        className = ClassName("topTable")

        tr { //Создание строки таблицы
            // Шапка таблицы
            td { //Создание ячейки таблицы
                +"№"
            }
            td {
                +"Наименование учебных публикаций, научных работ и патентов на изобретения и другие объекты интеллектуальной собственности"
            }
            td {
                +"Форма учебных изданий и научных трудов"
            }
            td {
                +"Выходные данные"
            }
            td {
                +"Объем"
            }
            td {
                +"Соавторы"
            }
        }
        CTableRows {
            this.files = listCheck
            this.professorName = props.professorName
        }
    }
    table {
        className = ClassName("bottomTable")
        tr {
            td {
                className  = ClassName("leftTd")
                +"Соискатель"
            }
            td {
                className  = ClassName("rightTd")
                +"______________${props.name}"
            }
        }
        tr {
            td {
                className  = ClassName("leftTd")
                +"Зав. кафедрой ____________"
            }
            td {
                className  = ClassName("rightTd")
                +"______________${props.zavName}"
            }
        }
        tr {
            td {
                className  = ClassName("leftTd")
                +"Ученый секретарь ученого совета"
            }
            td {
                className  = ClassName("rightTd")
                +"______________${props.secName}"
            }
        }
        tr {
            td {
                className  = ClassName("leftTd")
                +currentDate
            }
        }
    }
}