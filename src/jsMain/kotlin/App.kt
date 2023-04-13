import components.CReadCriterion
import components.testInput
import react.FC
import react.Props
import react.create
import react.dom.client.createRoot
import tanstack.query.core.QueryClient
import tanstack.react.query.QueryClientProvider

fun main() {
    val container = web.dom.document.getElementById("root")!!
    createRoot(container).render(app.create())
}

val app = FC<Props>("App") {
    QueryClientProvider {
        client = QueryClient()
        testInput {}
        CReadCriterion {}
    }
}