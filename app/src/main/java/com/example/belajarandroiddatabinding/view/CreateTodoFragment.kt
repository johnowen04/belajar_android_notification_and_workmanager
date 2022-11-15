package com.example.belajarandroiddatabinding.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.belajarandroiddatabinding.R
import com.example.belajarandroiddatabinding.databinding.FragmentCreateTodoBinding
import com.example.belajarandroiddatabinding.model.Todo
import com.example.belajarandroiddatabinding.util.NotificationHelper
import com.example.belajarandroiddatabinding.util.TodoWorker
import com.example.belajarandroiddatabinding.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*
import java.util.concurrent.TimeUnit

class CreateTodoFragment : Fragment(), TodoAddClickListener {
    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var dataBinding: FragmentCreateTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = FragmentCreateTodoBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        dataBinding.todo = Todo("", "", 3)
        dataBinding.addListener = this
        dataBinding.listener = object: RadioButtonListener {
            override fun onRadioClick(view: View, priority: Int, todo: Todo) {
                todo.priority = priority
            }
        }

        /*
        btnAddNotes.setOnClickListener {
            val radio = view.findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)

            val todo = Todo(
                editTitle.text.toString(),
                editNotes.text.toString(),
                radio.tag.toString().toInt()
            )

            val list = listOf(todo)

            viewModel.addTodo(list)
            Toast.makeText(view.context, "Data added", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()

            /*
            NotificationHelper(view.context)
                .createNotification(
                    "Todo Created",
                    "A new todo has been created. Stay focused!"
                )
             */

            val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(5, TimeUnit.SECONDS)
                .setInputData(
                    workDataOf(
                        "TITLE" to "Todo Created",
                        "CONTENT" to "A new todo has been created. Stay focused!"
                    )
                )
                .build()

            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
        }
        */
    }

    override fun onAddClickListener(view: View) {
        dataBinding.todo?.let {
            val list = listOf(it)
            viewModel.addTodo(list)

            Toast.makeText(view.context, "Data added", Toast.LENGTH_SHORT).show()
            val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(5, TimeUnit.SECONDS)
                .setInputData(
                    workDataOf(
                        "TITLE" to "Todo Created",
                        "CONTENT" to "A new todo has been created. Stay focused!"
                    )
                )
                .build()

            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
            Navigation.findNavController(view).popBackStack()
        }
    }
}