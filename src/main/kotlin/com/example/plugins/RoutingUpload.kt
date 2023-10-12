package com.example.plugins

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import java.io.File

fun Application.configureRoutingUpload() {
    routing {
        var fileDescription = ""
        var fileName = ""
        var fileType = ""

        post("/upload") {
            val multipartData = call.receiveMultipart()

            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        fileDescription = part.value
                    }

                    is PartData.FileItem -> {
                        part.originalFileName?.let { originalFileName ->
                            fileType = originalFileName.substringAfterLast(".")
                            fileName = part.name as String
                            val fileBytes = part.streamProvider().readBytes()
                            File("src/main/resources/upload_test/$fileName.$fileType").writeBytes(fileBytes)
                        }
                    }

                    else -> {}
                }
                part.dispose()
            }

            call.respondText("$fileDescription is uploaded to 'uploads/$fileName'")
        }
    }
}
