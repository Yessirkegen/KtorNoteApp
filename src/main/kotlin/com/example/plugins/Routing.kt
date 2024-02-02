package com.example.plugins

import com.example.authentication.JwtService
import com.example.authentication.hash
import com.example.data.model.User
import com.example.repository.repo
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val db = repo()
    val jwtService = JwtService()
    val hashFunction = { s:String -> hash(s) }
    routing {
        get("/") {
            call.respondText("Fuck you")

        }

        get("/token") {
            val email = call.request.queryParameters["email"]!!
            val password = call.request.queryParameters["password"]!!
            val username = call.request.queryParameters["username"]!!

            val user = User(email,hashFunction(password),username)
            call.respond(jwtService.generateToken(user))


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
