package com.example.quizandroid.data.local

import android.content.Context
import androidx.room.Room

object AppDatabaseInstance {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "quiz_db"
            )
                // 👉 esta linha evita o erro de versão que está fechando seu app
                .fallbackToDestructiveMigration()
                .build()
        }
        return INSTANCE!!
    }
}
