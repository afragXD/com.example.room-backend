package com.example.features.update

import com.example.features.register.RegisterController
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureUpdate() {
    routing {
        post("/update/username") {
            val updateController = UpdateUsernameController(call)
            updateController.updateUsername()
        }
    }
}