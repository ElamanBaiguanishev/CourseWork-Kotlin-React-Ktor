package nice_way.application.rest

import config.Config
import data.BibTex
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import nice_way.application.mongo.collection
import org.litote.kmongo.coroutine.toList
import org.litote.kmongo.eq

fun Route.readRoutes() {
    val properties = listOf(
        BibTex::author,
        BibTex::year,
        BibTex::pages,
        BibTex::journal,
        BibTex::number,
        BibTex::title,
        BibTex::volume
    )
    route(Config.readPath) {
        get {
            val criterionRequest = call.request.queryParameters["criterion"]
            val documents =
                properties
                    .map { collection.find(it eq criterionRequest).toList() }
                    .flatten()

            if (documents.isEmpty()) {
                call.respond (emptyList<BibTex>())
            } else {
                call.respond(documents)
            }
        }
        get("find:{criterion}") {
            val criterion = call.parameters["criterion"]!!
            val documents =
                properties
                    .map { collection.find(it eq criterion).toList() }
                    .flatten()

            if (documents.isEmpty()) {
                call.respond (emptyList<BibTex>())
            } else {
                call.respond(documents)
            }
        }
    }
}