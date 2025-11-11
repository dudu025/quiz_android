package com.example.quizandroid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quizandroid.data.local.entities.User
import com.example.quizandroid.data.UserDao
import com.example.quizandroid.data.local.entities.Score

@Database(entities = [User::class, Score::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun scoreDao(): ScoreDao
}
