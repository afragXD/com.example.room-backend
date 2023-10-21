package com.example.features.tournaments

import com.example.database.tokens.Tokens
import com.example.database.tournaments.TournamentDTO
import com.example.database.tournaments.Tournaments
import com.example.database.user.Users
import com.example.features.login.LoginReceiveRemote
import com.example.features.profile.ProfileResponseRemote
import com.example.utils.TokenCheck
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class TournamentsController(val call: ApplicationCall) {
    private val statusArr = arrayOf("OPENED", "ACTIVE", "FINISHED")
    suspend fun tournamentCreate(){
        val token = call.request.headers["Bearer-Authorization"]
        val receive = call.receive<TournamentsCreateReceiveRemote>()
        if (receive.size %2 != 0){
            call.respond(HttpStatusCode.BadRequest, "Неправильный формат ввода данных")
        }else if (TokenCheck.isTokenValid(token.orEmpty())){
            val tokenDTO = Tokens.fetchTokens1(token.toString())
            val userDTO = tokenDTO?.let { Users.fetchUser(it.email) }
            if (userDTO != null) {
                if (userDTO.role == "admin"){
                    Tournaments.insert(
                        TournamentDTO(
                            id = 0,
                            name = receive.name,
                            size = receive.size,
                            status = statusArr[0],
                            creatorId = userDTO.id
                        )
                    )
                    call.respond(HttpStatusCode.Created, "Турнир успешно создан")
                }else{
                    call.respond(HttpStatusCode.Forbidden, "Недостаточно прав")
                }
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
    suspend fun getTournamentsByStatus(){
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty())){
            val tokenDTO = Tokens.fetchTokens1(token.toString())
            val userDTO = tokenDTO?.let { Users.fetchUser(it.email) }
            if (userDTO != null) {
                val receive = call.parameters["status"]

                val isOk = when(receive){
                    "OPENED", "ACTIVE", "FINISHED"  -> true
                    else -> false
                }
                if (isOk){
                    val enter = Tournaments.selectByStatus(receive as String)
                    call.respond(HttpStatusCode.Found, enter)
                }else{
                    //call.respond(HttpStatusCode.BadRequest, "Неправильный формат ввода данных")
                    val enter = Tournaments.selectByStatus(receive as String)
                    call.respond(HttpStatusCode.Found, enter)
                }
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