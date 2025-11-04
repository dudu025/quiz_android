package com.example.quizandroid.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoreDao {

    @Insert
    suspend fun insertScore(score: Score)

    @Query("SELECT * FROM score ORDER BY id DESC")
    suspend fun getAllScores(): List<Score>

    @Query("DELETE FROM score")
    suspend fun deleteAll()
}
