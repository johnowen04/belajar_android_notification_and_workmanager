package com.example.belajarandroiddatabinding.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.belajarandroiddatabinding.databinding.TodoItemLayoutBinding
import com.example.belajarandroiddatabinding.model.Todo

class TodoListAdapter(
    val todoList: ArrayList<Todo>,
    val adapterOnClick: (Todo) -> Unit
):  RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(),
    TodoCheckedChangeListener,
    TodoEditClickListener {

    class TodoViewHolder(var view: TodoItemLayoutBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = TodoItemLayoutBinding.inflate(inflater, parent, false)

        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.view.todo = todoList[position]
        holder.view.listener = this
        holder.view.editListener = this
    }

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = todoList.size

    override fun onCheckedChange(cb: CompoundButton, isChecked: Boolean, todo: Todo) {
        if (isChecked) adapterOnClick(todo)
    }

    override fun onClick(view: View) {
        val action = TodoListFragmentDirections.actionEditTodoFragment(view.tag.toString().toInt())
        Navigation.findNavController(view).navigate(action)
    }
}