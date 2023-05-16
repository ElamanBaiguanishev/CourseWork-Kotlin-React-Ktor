package nice_way.application.rest

//import io.ktor.http.ContentDisposition.Companion.File
import config.Config
import data.BibTex
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import nice_way.application.mongo.collection
import org.litote.kmongo.newId
import java.io.File

fun Route.uploadRoutes() {
    route(Config.uploadPath) {
        get("/{name}") {
            val filename = call.parameters["name"]!!
            val file = File("resources/$filename")
            if (file.exists()) {
                call.respondFile(file)
            } else call.respond(HttpStatusCode.NotFound)
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
    }
}