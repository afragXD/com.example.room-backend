package com.example.database.user

import java.math.BigInteger

data class UserDTO(
    val id:  Int,
    val email: String,
    val password: String,
    val username: String,
    val gender: Boolean,
    val role: String,
    val avatar: String,
)