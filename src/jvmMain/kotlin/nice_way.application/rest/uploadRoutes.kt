package nice_way.application.rest

import config.Config
import data.BibTex
import io.ktor.http.*
import io.ktor.http.content.*
//import io.ktor.http.ContentDisposition.Companion.File
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.Identity.decode
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import nice_way.application.mongo.collection
import org.jbibtex.BibTeXDatabase
import org.jbibtex.BibTeXParser
import org.jbibtex.CharacterFilterReader
import org.litote.kmongo.coroutine.toList
import org.litote.kmongo.eq
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader

fun Route.uploadRoutes() {
    route(Config.uploadPath) {
        get("/{name}") {
            // get filename from request url
            val filename = call.parameters["name"]!!
            // construct reference to file
            // ideally this would use a different filename
            val file = File("resources/$filename")
            if (file.exists()) {
                call.respondFile(file)
            } else call.respond(HttpStatusCode.NotFound)
        }
        post {
            val multipart = call.receiveMultipart()
            multipart.forEachPart { part ->
                // if part is a file (could be form item)
                if(part is PartData.FileItem) {
                    // retrieve file name of upload
                    val name = part.streamProvider()
                    val a = InputStreamReader(name)
                    val filterReader = CharacterFilterReader(a)
                    val bibTeX = BibTeXParser().parse(filterReader)
//                    bibTeX.entries.values.map { println(it.fields.values.map { it.toUserString() }) }
                    return@forEachPart call.respond(bibTeX.entries.firstNotNullOf { it -> it.value.fields.values.map { it.toUserString() } })
//                    val file = File("/uploads/$name")
//
//                    // use InputStream from part to save file
//                    part.streamProvider().use { its ->
//                        // copy the stream to the file with buffering
//                        file.outputStream().buffered().use {
//                            // note that this is blocking
//                            its.copyTo(it)
//                        }
//                    }
                }
                // make sure to dispose of the part after use to prevent leaks
                part.dispose()
            }
        }
    }
}