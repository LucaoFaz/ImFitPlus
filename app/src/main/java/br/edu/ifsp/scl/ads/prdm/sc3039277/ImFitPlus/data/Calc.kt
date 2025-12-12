package br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Calc(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: String,
    val imc: Double,
    val categoriaImc: String,
    val tmb: Double,
    val pesoIdeal: Double,
    val fcmax: Int,
    val zonaLeve1: Double,
    val zonaLeve2: Double,
    val zonaQueima1: Double,
    val zonaQueima2: Double,
    val zonaAero1: Double,
    val zonaAero2: Double,
    val zonaAnaero1: Double,
    val zonaAnaero2: Double,
    val dataData: Long = System.currentTimeMillis() //data atual em milisegundos
)
