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

class TodoListViewModel(application: Application): AndroidViewModel(application), CoroutineScope {

    val todoListLiveData = MutableLiveData<List<Todo>>()
    val todoLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun refresh() {
        loadingLiveData.value = true
        todoLoadErrorLiveData.value = false

        launch {
            val db = buildDB(getApplication())

            todoListLiveData.value = db.todoDao().selectAllTodo()
        }
    }

    fun clearTask(todo: Todo) {
        launch {
            val db = buildDB(getApplication())

            db.todoDao().deleteTodo(todo)

            todoListLiveData.value = db.todoDao().selectAllTodo()
        }
    }
}