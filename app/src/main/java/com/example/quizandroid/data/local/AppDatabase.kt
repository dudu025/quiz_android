package com.example.quizandroid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quizandroid.data.local.entities.User
import com.example.quizandroid.data.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
