package br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CalcDao {
    @Insert
    suspend fun insertCalc(calc: Calc)

    @Query("SELECT * FROM Calc ORDER BY dataData DESC")
    suspend fun getAllCalcs(): List<Calc>
}