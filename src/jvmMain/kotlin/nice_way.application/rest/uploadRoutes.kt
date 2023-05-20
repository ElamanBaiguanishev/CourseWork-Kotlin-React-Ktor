package nice_way.application.rest

import config.Config
import data.BibTex
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import nice_way.application.mongo.collection
import org.litote.kmongo.*
import java.io.File

fun Route.uploadRoutes() {
    route(Config.uploadPath) {
        get {
            val a = collection.find().toList()
            call.respond(a)
        }
        get("{id}") {
            val id =
                call.parameters["id"] ?: return@get call.respondText(
                    "Missing or malformed id",
                    status = HttpStatusCode.BadRequest
                )
            val bibTex = collection.findOne(BibTex::_id eq id) ?: return@get call.respondText(
                "No element with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(bibTex)
        }
        post {
            val bibtex = call.receive<BibTex>()
            bibtex.createId(newId<BibTex>().toString())
            collection.insertOne(bibtex)
            call.respondText(
                "Element stored correctly",
                status = HttpStatusCode.Created
            )
        }
        put() {
//            val id = call.parameters["id"]
//                ?: return@put call.respondText(
//                    "Missing or malformed id",
//                    status = HttpStatusCode.BadRequest
//                )
            val bibtex = call.receive<BibTex>()
            val id = bibtex._id as String
            collection.updateOne(
                BibTex::_id eq id,
                bibtex
            )
            call.respondText(
                "Element update correctly $id",
                status = HttpStatusCode.Created
            )
        }
    }
}