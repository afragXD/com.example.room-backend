package com.example.features.register

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

class RegisterController(val call: ApplicationCall) {
    private val issuer = "roommyServer"
    private val jwtSecret = System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC512(jwtSecret)
    suspend fun registerNewUser(){

        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()

        val userDTO = Users.fetchUser(registerReceiveRemote.email)

        if (!registerReceiveRemote.email.isValidEmail()){
            call.respond(HttpStatusCode.InternalServerError, "Email не валидный")
        }else if (userDTO != null){
            call.respond(HttpStatusCode.Conflict, "Пользователь уже существует")
        } else{
            //val token = UUID.randomUUID().toString()

            try{
                Users.insert(
                    UserDTO(
                        email = registerReceiveRemote.email,
                        password = registerReceiveRemote.password,
                        username = registerReceiveRemote.username,
                        gender = registerReceiveRemote.gender,
                        role = "user",
                        avatar = "",
                    )
                )
            }catch (e: ExposedSQLException){
                call.respond(HttpStatusCode.Conflict, "Пользователь уже существует")
            }
            val token = JWT.create()
                .withClaim("email", registerReceiveRemote.email)
                .withClaim("username", registerReceiveRemote.username)
                .withClaim("gender", registerReceiveRemote.gender)
                .withExpiresAt(Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)))
                .withIssuer(issuer)
                .sign(algorithm)
            Tokens.insert(
                TokenDTO(
                    rowId = UUID.randomUUID().toString(), email = registerReceiveRemote.email,
                    token = token
                )
            )
            call.respond(RegisterResponseRemote(token = token))
        }

    }
}