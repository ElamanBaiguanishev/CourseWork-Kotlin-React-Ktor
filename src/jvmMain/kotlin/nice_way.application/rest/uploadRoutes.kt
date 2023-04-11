package nice_way.application.rest

import config.Config
import data.BibTex
import io.ktor.http.*
//import io.ktor.http.ContentDisposition.Companion.File
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import nice_way.application.mongo.collection
import org.litote.kmongo.coroutine.toList
import org.litote.kmongo.eq
import java.io.File

fun Route.uploadRoutes() {
    route(Config.uploadPath) {
        get("/{name}") {
            // get filename from request url
            val filename = call.parameters["name"]!!
            // construct reference to file
            // ideally this would use a different filename
            val file = File("/uploads/$filename")
            if(file.exists()) {
                call.respondFile(file)
            }
            else call.respond(HttpStatusCode.NotFound)
        }
    }
}