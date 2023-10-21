package com.example.features.tournaments

import com.example.features.login.LoginController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureTournamentsRouting() {
    routing {
        route("/tournaments") {
            post("/create") {
                val tournamentsController = TournamentsController(call)
                tournamentsController.tournamentCreate()
            }
            get("/select") {
                val tournamentsController = TournamentsController(call)
                tournamentsController.getTournamentsByStatus()
            }
        }
    }
}