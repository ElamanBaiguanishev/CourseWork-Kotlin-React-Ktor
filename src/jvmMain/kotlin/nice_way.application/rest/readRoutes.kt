package nice_way.application.rest

import config.Config
import data.BibTex
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import nice_way.application.mongo.collection
import org.litote.kmongo.eq

fun Route.readRoutes() {
    val properties = listOf(
        BibTex::_id,
        BibTex::tag,
        BibTex::type,
        BibTex::journal,
        BibTex::volume,
        BibTex::number,
        BibTex::pages,
        BibTex::month,
        BibTex::note,
        BibTex::key,
        BibTex::publisher,
        BibTex::series,
        BibTex::address,
        BibTex::address,
        BibTex::organization,
    )

    val finds = mapOf(
        "findAuthor:{criterion}" to BibTex::author,
        "findTitle:{criterion}" to BibTex::title,
        "findYear:{criterion}" to BibTex::year,
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

        finds.forEach { (attr, prop) ->
            get (attr) {
                val criterion = call.parameters["criterion"]!!
                val documents = collection.find( prop eq criterion).toList()

                if (documents.isEmpty()) {
                    call.respond (emptyList<BibTex>())
                } else {
                    call.respond(documents)
                }
            }
        }

        get("findAny:{criterion}") {
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