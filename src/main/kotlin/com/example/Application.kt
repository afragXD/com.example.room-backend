package com.example

import com.example.features.login.configureLoginRouting
import com.example.features.register.configureRegisterRouting
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import com.example.features.profile.configureProfileRouting
import com.example.features.update.configureUpdate

fun main() {

    Database.connect("jdbc:postgresql://localhost:5432/roommy", driver = "org.postgresql.Driver",
        user = "postgres", password = "742617"
    )

    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureRegisterRouting()
    configureLoginRouting()

    configureProfileRouting()
    configureUpdate()

    configureStaticRouting()

    configureRoutingUpload()

    configureSerialization()
}
