package nice_way.application.rest

import config.Config
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import data.BibTex
import nice_way.application.mongo.collection
import org.litote.kmongo.coroutine.toList
import org.litote.kmongo.eq

fun Route.documentRoutes() {
    route(Config.documentsPath) {
        get {
            call.respond(collection.find().toList())
        }
        post {
            collection.insertOne(call.receive<BibTex>())
            call.respond(HttpStatusCode.OK)
        }
        delete("/{author}") {
            val author = call.parameters["author"] ?: error("Invalid delete request")
            collection.deleteOne(BibTex::author eq author)
            call.respond(HttpStatusCode.OK)
        }
    }
}