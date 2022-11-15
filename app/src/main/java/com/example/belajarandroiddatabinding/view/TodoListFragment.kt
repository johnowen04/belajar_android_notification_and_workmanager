package com.example.belajarandroiddatabinding.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.belajarandroiddatabinding.R
import com.example.belajarandroiddatabinding.viewmodel.TodoListViewModel
import kotlinx.android.synthetic.main.fragment_todo_list.*

class TodoListFragment : Fragment() {
    private lateinit var viewModel: TodoListViewModel

    private lateinit var adapter: TodoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)
        viewModel.refresh()

        adapter = TodoListAdapter(arrayListOf()) { viewModel.clearTask(it) }
        recTodo.layoutManager = LinearLayoutManager(context)
        recTodo.adapter = adapter

        fabTodo.setOnClickListener {
            val action = TodoListFragmentDirections.actionCreateTodoFragment()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.todoListLiveData.observe(viewLifecycleOwner) {
            adapter.updateTodoList(it)
            txtEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}