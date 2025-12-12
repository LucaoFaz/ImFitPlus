package br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class User(
    @PrimaryKey
    val id: Int = 1,

    val nome: String,
    val idade: Int,
    val sexo: String,
    val altura: Double,
    val peso: Double,
    val atividade: String
)
