package com.example.features.login

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.database.tokens.TokenDTO
import com.example.database.tokens.Tokens
import com.example.database.user.UserDTO
import com.example.database.user.Users
import com.example.utils.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.util.*

class LoginController(val call: ApplicationCall) {
    private val issuer = "roommyServer"
    private val jwtSecret = System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC512(jwtSecret)
    suspend fun performLogin(){

        val receive = call.receive<LoginReceiveRemote>()

        val userDTO = Users.fetchUser(receive.email)

        if (userDTO == null){
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else{
            if (userDTO.password == receive.password){
                //var token = UUID.randomUUID().toString()

                val token = JWT.create()
                    .withClaim("email", userDTO.email)
                    .withClaim("username", userDTO.username)
                    .withClaim("gender", userDTO.gender)
                    .withExpiresAt(Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)))
                    .withIssuer(issuer)
                    .sign(algorithm)

                Tokens.insert(
                    TokenDTO(
                        rowId = UUID.randomUUID().toString(), email = receive.email,
                        token = token
                    )
                )

                call.respond(LoginResponseRemote(token = token))

            }else{
                call.respond(HttpStatusCode.BadRequest, "Пароль инвалид")
            }
        }

    }
}
//9 6 12 10 11 10