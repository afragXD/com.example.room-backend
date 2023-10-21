package com.example.features.tournaments

import com.example.database.tournaments.TournamentDTO
import kotlinx.serialization.Serializable

@Serializable
data class TournamentsCreateReceiveRemote(
    var name: String,
    var size: Int,
)

@Serializable
data class TournamentsSelectResponseRemote(
    var tournamentList: List<TournamentDTO>,
)
