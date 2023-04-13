package components

import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import web.html.HTMLInputElement

val CReadCriterion = FC<Props>("Read") {
    var stub by useState("stop")
    val inputRef = useRef<HTMLInputElement>()
    input {
        ref = inputRef
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
                this.criterion = inputRef.current?.value!!
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