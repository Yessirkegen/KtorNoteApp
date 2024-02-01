package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Fuck you")

        }
        get("/note/{id}") {
            val id = call.parameters["id"]
            call.respond("${id}")
        }
        get("/note") {
            val id = call.request.queryParameters["id"]
            call.respond("${id}")
        }


            route("/notes"){
                route("/create"){
                 post {
                      val body= call.receive<String>()
                      call.respond(body)

                    }
                }
            delete {
                val body= call.receive<String>()
                call.respond(body)
            }
        }

    }

}
