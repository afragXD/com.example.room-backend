package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureStaticRouting() {
    routing {
        staticResources("/static/avatars", "avatars"){
            default("default_img.png")
        }
        staticResources("/static/videos", "videos"){
            default("def_video.mp4")
        }
    }
}
