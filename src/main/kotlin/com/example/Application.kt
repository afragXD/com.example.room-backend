package com.example

import com.example.features.login.configureLoginRouting
import com.example.features.register.configureRegisterRouting
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import com.example.features.profile.configureProfileRouting
import com.example.features.tournaments.configureTournamentsRouting
import com.example.features.update.configureUpdate
import io.ktor.server.netty.*

fun main() {

    Database.connect("jdbc:postgresql://localhost:5432/Hack", driver = "org.postgresql.Driver",
        user = "habrpguser", password = "pgpwd4habr"
    )

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    //configureHTTP()
    //configureSwagger()
    configureRouting()
    configureRegisterRouting()
    configureLoginRouting()

    configureProfileRouting()
    configureUpdate()

    configureTournamentsRouting()

    configureStaticRouting()

    configureRoutingUpload()

    configureSerialization()
}
