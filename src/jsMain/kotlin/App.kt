import react.FC
import react.Props
import react.create
import react.dom.client.createRoot
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ol

fun main() {
    val container = web.dom.document.getElementById("root")!!
    createRoot(container).render(app.create())
}

val app = FC<Props>("App") {
    ol {
        for (i in "hello, world!".indices) {
            li {
                +"hello, world!"[i].toString()
            }
        }
    }
}