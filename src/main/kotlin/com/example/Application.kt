package com.example

import com.example.authentication.JwtService
import com.example.authentication.hash
import com.example.plugins.configureDatabases
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.repository.DatabaseFactory
import com.example.repository.Repo
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.locations.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
            .start(wait = true)


}

fun Application.module() {
//    configureSecurity()
    configureSerialization()
    configureDatabases()


    DatabaseFactory.init()
    install(Locations)
    val db = Repo()
    val jwtService = JwtService()
    val hashFunction = { s:String -> hash(s) }
    install(Authentication){jwt("jwt") {

        verifier(jwtService.varifier)
        realm = "Note Server"
        validate {
            val payload = it.payload
            val email = payload.getClaim("email").asString()
            val user = db.finduserByEmail(email)
            user
        }

    }}

    configureRouting()
}
