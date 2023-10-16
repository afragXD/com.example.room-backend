package com.example.features.update

import com.example.database.tokens.Tokens
import com.example.database.user.Users
import com.example.utils.TokenCheck
import com.example.utils.toHex
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.io.File

class UpdateAvatarController(val call: ApplicationCall) {

    suspend fun updateAvatar(){

        var fileDescription = ""
        var fileType = ""

        val multipartData = call.receiveMultipart()

        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    fileDescription = part.value
                }
                is PartData.FileItem -> {
                    part.originalFileName?.let { originalFileName ->
                        fileType = originalFileName.substringAfterLast(".")
                        val allowedExtensions = arrayOf("png", "jpg", "webp")
                        val token = part.name as String
                        if (fileType !in allowedExtensions){
                            call.respondText("Недопустимое расширение файла")
                        }else if (TokenCheck.isTokenValid(token.orEmpty())){
                            val tokenDTO = Tokens.fetchTokens1(token.toString())
                            val userDTO = tokenDTO?.let { Users.fetchUser(it.email) }
                            if (userDTO != null) {
                                val fileBytes = part.streamProvider().readBytes()
                                val fileName = "${userDTO.email.toByteArray().toHex()}.png"
                                File("src/main/resources/avatars/$fileName").writeBytes(fileBytes)
                                Users.fetchUpdateAvatar(userDTO.email, "http://arisen.ddns.net:8080/static/avatars/$fileName")
                                call.respondText("Аватарка обновлена")
                            }
                        }else{
                            if (token.isEmpty()){
                                call.respond(HttpStatusCode.Forbidden, "Еблан входа")
                            }else {
                                Tokens.fetchOut(token)
                                call.respond(HttpStatusCode.Unauthorized, "Срок действия токена истек")
                            }
                        }
                    }
                }
                else -> {}
            }
            part.dispose()
        }
    }
}