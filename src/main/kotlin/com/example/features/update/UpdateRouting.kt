package com.example.features.update

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureUpdate() {
    routing {
        post("/update/username") {
            val updateController = UpdateUsernameController(call)
            updateController.updateUsername()
        }
        post("/update/avatar") {
            val updateController = UpdateAvatarController(call)
            updateController.updateAvatar()
        }
    }
}