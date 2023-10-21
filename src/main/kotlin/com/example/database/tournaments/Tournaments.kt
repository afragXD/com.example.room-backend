package com.example.database.tournaments

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Tournaments: Table() {
    private val id = Tournaments.integer("id")
    private val name = Tournaments.varchar("name", 100)
    private val size = Tournaments.integer("size")
    private val status = Tournaments.varchar("status", 10)
    private val creatorId = Tournaments.integer("creator_id")

    fun insert(tournamentDTO: TournamentDTO){
        transaction {
            Tournaments.insert{
                it[name] = tournamentDTO.name
                it[size] = tournamentDTO.size
                it[status] = tournamentDTO.status
                it[creatorId] = tournamentDTO.creatorId
            }
        }
    }
    fun selectByStatus(status: String):List<TournamentDTO>{
        if (status != ""){
            return transaction {
                Tournaments.select{Tournaments.status.eq(status)}
                    .map { row ->
                        TournamentDTO(
                            id = row[Tournaments.id],
                            name = row[Tournaments.name],
                            size = row[Tournaments.size],
                            status = row[Tournaments.status],
                            creatorId = row[Tournaments.creatorId]
                        )
                    }
            }
        } else{
            return transaction {
                Tournaments.selectAll()
                    .map { row ->
                        TournamentDTO(
                            id = row[Tournaments.id],
                            name = row[Tournaments.name],
                            size = row[Tournaments.size],
                            status = row[Tournaments.status],
                            creatorId = row[Tournaments.creatorId]
                        )
                    }
            }
        }
    }
}