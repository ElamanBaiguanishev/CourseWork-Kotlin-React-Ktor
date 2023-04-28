import components.CBibtex
import components.CBibtexList
import components.CInputFileBibtex
import config.Config
import react.FC
import react.Props
import react.create
import react.dom.client.createRoot
import react.router.Route
import react.router.Routes
import react.router.dom.HashRouter
import react.router.dom.Link
import tanstack.query.core.QueryClient
import tanstack.react.query.QueryClientProvider

fun main() {
    val container = web.dom.document.getElementById("root")!!
    createRoot(container).render(app.create())
}

val app = FC<Props>("App") {
    HashRouter {
        QueryClientProvider {
            client = QueryClient()
            Link {
                +"Список"
                to = Config.documentsPath
            }
            Link {
                +"Добавить"
                to = "addBibTex"
            }
            Routes {
                Route {
                    path = "addBibTex"
                    element = CInputFileBibtex.create()
                }
                Route {
                    path = Config.documentsPath
                    element = CBibtexList.create()
                }
                Route {
                    path = Config.documentsPath + ":id"
                    element = CBibtex.create()
                }
            }
        }
    }
}