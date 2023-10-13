package com.example.features.update

import com.example.database.tokens.Tokens
import com.example.database.user.Users
import com.example.utils.TokenCheck
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class UpdateUsernameController(val call: ApplicationCall) {

    suspend fun updateUsername(){
        val token = call.request.headers["Bearer-Authorization"]
        val receive = call.receive<UpdateReceiveRemote>()

        if (TokenCheck.isTokenValid(token.orEmpty())){
            val tokenDTO = Tokens.fetchTokens1(token.toString())
            val userDTO = tokenDTO?.let { Users.fetchUser(it.email) }
            if (userDTO != null) {
                Users.fetchUpdateUsername(userDTO.email, receive.updateValue)
                call.respondText("Данные обновлены")
                //call.respond(HttpStatusCode.BadGateway, "Ошибка авторизации")
            }
        }else{
            if (token == null){
                call.respond(HttpStatusCode.Forbidden, "Еблан входа")
            }else {
                Tokens.fetchOut(token)
                call.respond(HttpStatusCode.Unauthorized, "Срок действия токена истек")
            }
        }

    }

}