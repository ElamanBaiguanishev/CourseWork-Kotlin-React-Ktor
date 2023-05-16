package components

import js.core.get
import react.FC
import react.Props
import react.dom.html.ReactHTML.input
import react.useState
import web.html.InputType


val CInputFileBibtex = FC<Props>("inputText") {
    var result by useState<String>(" ")
    val fields = Regex("([a-zA-Z]+) *= *[\"{]*([a-zA-Z0-9 ,-:]+)[\"}]*")
        .findAll(result)
        .associate {
            it.groups[1]?.value to it.groups[2]?.value
        }
    val params = Regex("@([a-zA-Z]+) *\\{ *(.+),")
        .findAll(result)
        .map { matchResult ->
            matchResult.groups.map {
                it?.value to it?.value
            }
        }.flatten().toMap()
    val all = fields + params
    input {
        type = InputType.file
        onChange = { changeEvent ->
            changeEvent.target.files!![0].text().then { result = it }
            changeEvent.target.value = ""
        }
    }

    if (all.isNotEmpty()) {
        CEditInputFIle {
            this.fields = all.toMutableMap()
        }
    }
}