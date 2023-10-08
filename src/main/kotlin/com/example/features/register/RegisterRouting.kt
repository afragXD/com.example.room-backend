package com.example.features.register

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRegisterRouting() {
    routing {
        post("/register") {
            val registerController = RegisterController(call)
            registerController.registerNewUser()
        }
    }
}