package com.example.quizandroid.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score")
data class Score(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userEmail: String,
    val pontos: Int,
    val data: String
)
