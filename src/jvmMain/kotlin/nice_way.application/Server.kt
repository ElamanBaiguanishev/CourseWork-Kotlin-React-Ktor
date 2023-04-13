package nice_way.application

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import nice_way.application.rest.documentRoutes
import nice_way.application.rest.readRoutes
import nice_way.application.rest.uploadRoutes

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(
        Netty,
        port = port,
        host = "127.0.0.1",
        watchPaths = listOf("classes")
    ) {
        main()
    }.start(wait = true)
}

fun Application.main(isTest: Boolean = true) {
    config(isTest)
    static()
    rest()
    if (isTest) logRoute()
}

fun Application.config(isTest: Boolean) {
    install(ContentNegotiation) {
        json()
    }
    install(Compression) {
        gzip()
    }
/*    if (isTest) {
        createTestData()
        install(createApplicationPlugin("DelayEmulator") {
            onCall {
                delay(10L)
            }
        })
    }*/
}

fun Application.rest() {
    routing {
        documentRoutes()
        uploadRoutes()
        readRoutes()
    }
}