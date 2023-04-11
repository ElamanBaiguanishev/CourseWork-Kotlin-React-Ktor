package components

import react.FC
import react.Props
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.useRef
import react.useState
import web.html.HTMLInputElement
import web.html.InputType


val testInput = FC<Props>("inputText") {
    var ref by useState<String>("")
    input {
        type = InputType.file
        onChange = {
            ref = it.target.value
        }
    }

    label {
        +ref
    }
}