package com.example.belajarandroiddatabinding.model

import androidx.room.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg todo: Todo)

    @Query("SELECT * FROM todo ORDER BY priority DESC")
    suspend fun selectAllTodo(): List<Todo>

    @Query("SELECT * FROM todo WHERE uuid= :id")
    suspend fun selectTodo(id: Int): Todo

    @Query("UPDATE todo SET title= :title, notes= :notes, priority= :priority WHERE uuid= :id")
    suspend fun updateTodo(title: String, notes: String, priority: Int, id: Int)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)
}