package com.example.database.tournaments

import kotlinx.serialization.Serializable

@Serializable
data class TournamentDTO(
    val id: Int,
    val name: String,
    val size: Int,
    val status: String,
    val creatorId: Int,
)
