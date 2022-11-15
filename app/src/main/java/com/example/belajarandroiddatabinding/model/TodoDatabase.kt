package com.example.belajarandroiddatabinding.model

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.belajarandroiddatabinding.util.buildDB

@Database(entities = arrayOf(Todo::class), version = 2)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile private var instance: TodoDatabase ?= null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) =
            buildDB(context)

        operator fun invoke(context: Context) {
            if (instance != null) {
                synchronized(LOCK) {
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }
}