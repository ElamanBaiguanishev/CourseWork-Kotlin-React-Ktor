import components.find.CReadCriterion
import components.form.CForm
import components.load.CBibtex
import components.load.CBibtexList
import components.load.CInputFileBibtex
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
            Link {
                +"Найти"
                to = "findBibTex"
            }
            Link {
                +"Сформировать"
                to = "form"
            }
            Routes {
                Route {
                    path = "addBibTex"
                    element = CInputFileBibtex.create()
                }
                Route {
                    path = "findBibTex"
                    element = CReadCriterion.create()
                }
                Route {
                    path = Config.documentsPath
                    element = CBibtexList.create()
                }
                Route {
                    path = Config.documentsPath + ":id"
                    element = CBibtex.create()
                }
                Route {
                    path = "form"
                    element = CForm.create()
                }
            }
        }
    }
}