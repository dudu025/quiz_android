package com.example.quizandroid.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.quizandroid.data.local.entities.Score
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {

    @Insert
    suspend fun insertScore(score: Score)

    @Query("SELECT * FROM score ORDER BY id DESC")
    fun getAllScores(): Flow<List<Score>>

    @Query("DELETE FROM score")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteScore(score: Score)
}
