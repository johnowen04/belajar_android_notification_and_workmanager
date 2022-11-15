package com.example.belajarandroiddatabinding.util

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.belajarandroiddatabinding.model.TodoDatabase

val DB_NAME = "newtododb"

val MIGRATION_1_2 = object: Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 NOT NULL")
    }
}

fun buildDB(context: Context): TodoDatabase =
    Room.databaseBuilder(context.applicationContext,
        TodoDatabase::class.java,
        DB_NAME)
        .addMigrations(MIGRATION_1_2)
        .build()