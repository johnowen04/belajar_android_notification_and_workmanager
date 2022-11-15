package com.example.belajarandroiddatabinding.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.belajarandroiddatabinding.util.buildDB
import com.example.belajarandroiddatabinding.model.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(application: Application): AndroidViewModel(application), CoroutineScope {

    val todoLiveData = MutableLiveData<Todo>()

    fun fetch(uuid: Int) {
        launch {
            val db = buildDB(getApplication())

            todoLiveData.value = db.todoDao().selectTodo(uuid)
        }
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun addTodo(list: List<Todo>) {
        launch {
            val db = buildDB(getApplication())

            db.todoDao().insertAll(*list.toTypedArray())
        }
    }

    fun updateTodo(title: String, notes: String, priority: Int, id: Int) {
        launch {
            val db = buildDB(getApplication())

            db.todoDao().updateTodo(title, notes, priority, id)
        }
    }

    fun updateTodo(todo: Todo) {
        launch {
            val db = buildDB(getApplication())

            db.todoDao().updateTodo(todo)
        }
    }
}