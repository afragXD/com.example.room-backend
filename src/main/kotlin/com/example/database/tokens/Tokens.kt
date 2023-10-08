package com.example.database.tokens

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens: Table() {
    private val id = Tokens.varchar("id", 50)
    private val email = Tokens.varchar("email", 25)
    private val token = Tokens.varchar("token", 350)

    fun insert(tokenDTO: TokenDTO){
        transaction {
            Tokens.insert {
                it[id] = tokenDTO.rowId
                it[email] = tokenDTO.email
                it[token] = tokenDTO.token
            }
        }
    }
    fun fetchTokens(): List<TokenDTO> {
        return try {
            transaction {
                Tokens.selectAll().toList()
                    .map {
                        TokenDTO(
                            rowId = it[Tokens.id],
                            token = it[token],
                            email = it[email]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun fetchTokens1(token:String): TokenDTO? {
        return try {
            transaction {
                val tokenModel = Tokens.select{ Tokens.token.eq(token) }.single()
                TokenDTO(
                    rowId = tokenModel[Tokens.id],
                    email = tokenModel[email],
                    token = tokenModel[Tokens.token],
                )
            }
        } catch (e: Exception) {
            null
        }
    }
    fun fetchOut(token: String){
        transaction {
            Tokens.deleteWhere { Tokens.token.eq(token) }
        }
    }
}